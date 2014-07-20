package com.gdx.testgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
		
		Line[] lineArray = this.generateRandomPath(20);
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
	
	private class Line{
		private float X1, X2;
		private float Y1, Y2;
		
		private Line previousLine;
		
		public Line(float X1, float Y1, float X2, float Y2){
			this.X1 = X1;
			this.Y1 = Y1;
			
			this.X2 = X2;
			this.Y2 = Y2;
			
			this.previousLine = null;
		}
		
		public Line(Line prevLine, float X, float Y){
			this.X1 = prevLine.getX2();
			this.Y1 = prevLine.getY2();
			this.X2 = X;
			this.Y2 = Y;
			
			this.previousLine = prevLine;
		}
		
		public float getX1(){ return X1; }
		public float getY1(){ return Y1; }
		public float getX2(){ return X2; }
		public float getY2(){ return Y2; }
		
	}
	
	private Line[] generateRandomPath(int size){
		Line[] lineArray = new Line[size];
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		lineArray[0] = new Line(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
		for( int i = 1; i < lineArray.length - 1; i++){
			lineArray[i] = new Line(lineArray[i-1], rnd.nextFloat(), rnd.nextFloat());
		}
		return lineArray;
	}
	
	private SequenceAction generateSequence(Line[] lineArray, float speed){
		
		SequenceAction sa = new SequenceAction();
		double commonPath = 0.0;
		double duration[] = new double[lineArray.length];
		double path[] = new double[lineArray.length];
		
		// считаю общий путь
		for ( int i = 1; i < lineArray.length - 1; i++){
			path[i-1] = 100 * Math.sqrt(Math.pow((lineArray[i].getX2() - lineArray[i-1].getX1()), 2) + Math.pow((lineArray[i].getY2() - lineArray[i].getY1()), 2));
			commonPath = commonPath + path[i-1];
		}
		
		// длительность
		double commonDuration = commonPath/speed;
				
		MoveToAction[] mta = new MoveToAction[lineArray.length];
		for ( int i = 1; i < lineArray.length - 1; i++){
			duration[i-1] = (path[i-1]/commonPath) * commonDuration;
			mta[i-1] = new MoveToAction();
			mta[i-1].setX(lineArray[i].getX2()*XPos);
			mta[i-1].setY(lineArray[i].getY2()*YPos);
			mta[i-1].setDuration((float)duration[i-1]);
			
			sa.addAction(mta[i-1]);
		}
							
		return sa;
	}
	
	private void normalizeGraphics(){
		
		float CW = 1f; 
		float CH = 1f;
		
		camera = new OrthographicCamera(CW, -CH);
		camera.position.set(CW / 2, CH / 2, 0);
				
		CH =  CW* Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
		
		float ppuX = (float)Gdx.graphics.getWidth() / CW;
		float ppuY = (float)Gdx.graphics.getHeight() / CH;
		
		XPos = CW*ppuX;
		YPos = CH*ppuY;
	}
}
