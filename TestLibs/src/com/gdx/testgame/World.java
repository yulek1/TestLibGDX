package com.gdx.testgame;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class World extends Stage {
	
	private Transact transact;
	
	public Transact getTransact(){
		return this.transact;
	}
	
	public World(){
		createWorld();
	}
	
	private void createWorld(){
		transact = new Transact();
	}
}
