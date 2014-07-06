package com.gdx.testgame;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class World extends Stage {
	
	private Transact transact;
	
	// dimentions
	
	private int width;
	private int height;
	
	public Transact getTransact(){
		return this.transact;
	}
	
	public World(){
		width  = 10;
		height = 10;
		createWorld();
	}
	
	private void createWorld(){
		transact = new Transact();
	}
	

}
