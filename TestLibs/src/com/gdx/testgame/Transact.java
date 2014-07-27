package com.gdx.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Transact extends Actor {
		
	Texture texture = new Texture(Gdx.files.internal("data/test.png"));	

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(texture, getX(), getY(), 0.1f, 0.1f);
	}

}
