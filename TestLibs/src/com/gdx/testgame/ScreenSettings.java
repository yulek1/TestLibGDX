package com.gdx.testgame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ScreenSettings {

	public static float CAMERA_WIDTH = 8f;
	public static float CAMERA_HEIGHT = 5f;

	private int width, height;
	public float ppuX; 
	public float ppuY;

	private OrthographicCamera cam;
	ShapeRenderer renderer = new ShapeRenderer();

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float) width / CAMERA_WIDTH;
		ppuY = (float) height / CAMERA_HEIGHT;
	}

	public void setCamera(float x, float y) {
		cam = new OrthographicCamera();
		this.cam.position.set(x, y, 0);
		this.cam.update();
	}

	public ScreenSettings(int width, int height) {
		this.setSize(width, height);
		this.setCamera(width / 2, height / 2);
	}

}
