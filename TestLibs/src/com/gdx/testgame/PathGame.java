package com.gdx.testgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class PathGame implements ApplicationListener {

	private World world;
	private Camera camera;
	
	private float XPos; // coeff screen width;
	private float YPos; // coeff screen height;

	@Override
	public void create() {
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		
		int duration = 3;
		
		normalizeGraphics();
		
		world = new World();
		Gdx.input.setInputProcessor(world);		

		Transact transact = world.getTransact();

		transact.addAction(Actions.sequence(Actions.moveTo(0.5f*XPos, 0, duration),
				Actions.moveTo(0.5f*XPos, 1f*YPos, duration), 
				Actions.moveTo(0.2f*XPos, 0.2f*YPos, duration),
				Actions.moveTo(0.3f*XPos, 0.75f*YPos, duration),
				Actions.moveTo(0.48f*XPos, 0.200f*YPos, duration),
				Actions.moveTo(0.480f*XPos, 0.320f*YPos, duration)));

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
	
	private void normalizeGraphics(){
		
		float CW = 1f; 
		float CH = 1f;
		
		CH =  CW* Gdx.graphics.getHeight()/Gdx.graphics.getWidth();
		
		float ppuX = (float)Gdx.graphics.getWidth() / CW;
		float ppuY = (float)Gdx.graphics.getHeight() / CH;
		
		XPos = CW*ppuX;
		YPos = CH*ppuY;
	}
}
