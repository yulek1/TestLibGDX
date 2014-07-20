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

public class PathGame implements ApplicationListener {

	private World world;
	private Camera camera;

	private float XPos; // coeff screen width;
	private float YPos; // coeff screen height;

	@Override
	public void create() {

		normalizeGraphics();

		world = new World();

		Gdx.input.setInputProcessor(world);

		Transact transact = world.getTransact();

		PolylineSegment[] lineArray = this.generateRandomPath(20);
		int speed = 20;

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

		private PolylineSegment previousLine;

		public PolylineSegment(float X1, float Y1, float X2, float Y2) {
			this.X1 = X1;
			this.Y1 = Y1;

			this.X2 = X2;
			this.Y2 = Y2;

			this.previousLine = null;
		}

		public PolylineSegment(PolylineSegment prevLine, float X, float Y) {
			this.X1 = prevLine.getX2();
			this.Y1 = prevLine.getY2();
			this.X2 = X;
			this.Y2 = Y;

			this.previousLine = prevLine;
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
		double commonPath = 0.0;
		double durations[] = new double[polyline.length];
		double segmentLenght[] = new double[polyline.length];

		// считаю общий путь
		for (int i = 1; i < polyline.length - 1; i++) {
			segmentLenght[i - 1] = 100 * Math
					.sqrt(Math.pow(
							(polyline[i].getX2() - polyline[i - 1].getX1()),
							2)
							+ Math.pow((polyline[i].getY2() - polyline[i]
									.getY1()), 2));
			commonPath = commonPath + segmentLenght[i - 1];
		}

		// длительность
		double commonDuration = commonPath / speed;

		MoveToAction[] moveToActions = new MoveToAction[polyline.length];
		for (int i = 1; i < polyline.length - 1; i++) {
			durations[i - 1] = (segmentLenght[i - 1] / commonPath) * commonDuration;
			moveToActions[i - 1] = new MoveToAction();
			moveToActions[i - 1].setX(polyline[i].getX2() * XPos);
			moveToActions[i - 1].setY(polyline[i].getY2() * YPos);
			moveToActions[i - 1].setDuration((float) durations[i - 1]);

			sequenceAction.addAction(moveToActions[i - 1]);
		}

		return sequenceAction;
	}

	private void normalizeGraphics() {

		float CW = 1f;
		float CH = 1f;

		camera = new OrthographicCamera(CW, -CH);
		camera.position.set(CW / 2, CH / 2, 0);

		CH = CW * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

		float ppuX = (float) Gdx.graphics.getWidth() / CW;
		float ppuY = (float) Gdx.graphics.getHeight() / CH;

		XPos = CW * ppuX;
		YPos = CH * ppuY;
	}
}
