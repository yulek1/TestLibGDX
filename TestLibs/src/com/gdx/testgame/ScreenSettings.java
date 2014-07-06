package com.gdx.testgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ScreenSettings {
	
	public static float CAMERA_WIDTH = 8f;
	public static  float CAMERA_HEIGHT = 5f;
	
	// Размеры
	private int width, height;
	public float ppuX;    // пикселей на точку мира по X
	public float ppuY;
	
	//камера и renderer
	private OrthographicCamera cam;
	ShapeRenderer renderer = new ShapeRenderer();
	
	
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h; 
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
	}
	
	//установка камеры
	public void setCamera(float x, float y){
		cam = new OrthographicCamera();
		this.cam.position.set(x, y, 0); 
		this.cam.update();
	}
	
	public ScreenSettings(int width, int height){
		this.setSize(width, height);
		this.setCamera(width/2, height/2);
	}
		
}
