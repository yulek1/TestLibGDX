package com.gdx.testgame;

import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PathGame implements ApplicationListener {

	private World world;
	private Camera camera;
	private Viewport viewport;

	@Override
	public void create() {

		setCamera();

		Gdx.input.setInputProcessor(world);

		Transact transact = world.getTransact();
		
		PolylineSegment[] lineArray = this.generateRandomPath(20);
		float speed = 0.5f;
		
		transact.addAction(this.generateSequence(lineArray, speed));

		world.addActor(transact);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.act(Gdx.graphics.getDeltaTime());
		world.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void resume() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LwjglApplication(new PathGame());

	}

	private class PolylineSegment {
		private float X1, X2;
		private float Y1, Y2;

		public PolylineSegment(float X1, float Y1, float X2, float Y2) {
			this.X1 = X1;
			this.Y1 = Y1;

			this.X2 = X2;
			this.Y2 = Y2;
		}

		public PolylineSegment(PolylineSegment prevLine, float X, float Y) {
			this.X1 = prevLine.getX2();
			this.Y1 = prevLine.getY2();
			this.X2 = X;
			this.Y2 = Y;
		}

		public float getX1() {
			return X1;
		}

		public float getY1() {
			return Y1;
		}

		public float getX2() {
			return X2;
		}

		public float getY2() {
			return Y2;
		}

	}

	private PolylineSegment[] generateRandomPath(int segmentCount) {
		PolylineSegment[] lineArray = new PolylineSegment[segmentCount];
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		lineArray[0] = new PolylineSegment(random.nextFloat(), random.nextFloat(),
				random.nextFloat(), random.nextFloat());
		for (int i = 1; i < lineArray.length - 1; i++) {
			lineArray[i] = new PolylineSegment(lineArray[i - 1], random.nextFloat(),
					random.nextFloat());
		}
		return lineArray;
	}

	private SequenceAction generateSequence(PolylineSegment[] polyline, float speed) {

		SequenceAction sequenceAction = new SequenceAction();

		MoveToAction[] moveToActions = new MoveToAction[polyline.length];
		for (int i = 0; i < polyline.length - 1; i++) {
			
			double segmentLenght = Math
					.sqrt(Math.pow(
							(polyline[i].getX2() - polyline[i].getX1()),
							2)
							+ Math.pow((polyline[i].getY2() - polyline[i]
									.getY1()), 2));

			float duration = (float)segmentLenght/speed;
			moveToActions[i] = new MoveToAction();
			moveToActions[i].setPosition(polyline[i].getX2(), polyline[i].getY2());
			moveToActions[i].setDuration(duration);

			sequenceAction.addAction(moveToActions[i]);
		}

		return sequenceAction;
	}
	
	private void setCamera(){
		
		float CW = 1f;
		float CH = 1f;
		
		world = new World();
		viewport = world.getViewport();
		viewport.setWorldHeight(CH);
		viewport.setWorldWidth(CW);
		camera = new OrthographicCamera(CW, CH);
		camera.position.set(CW / 2, CH / 2, 0);
		viewport.setCamera(camera);
	
	}

}
