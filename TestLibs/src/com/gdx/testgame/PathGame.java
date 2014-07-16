package com.gdx.testgame;

import javax.sound.midi.Sequence;

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

	@Override
	public void create() {

		camera = new OrthographicCamera(480, 320);
		camera.position.set(480 / 2, 320 / 2, 0);

		world = new World();
		Gdx.input.setInputProcessor(world);

		Transact transact = world.getTransact();

		transact.addAction(Actions.sequence(Actions.moveTo(100, 0, 3),
				Actions.moveTo(100, 100, 3), Actions.moveTo(400, 100, 3),
				Actions.moveTo(400, 200, 3), Actions.moveTo(480, 200, 3),
				Actions.moveTo(480, 320, 3)));

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
}
