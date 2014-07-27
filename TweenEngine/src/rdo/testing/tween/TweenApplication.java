package rdo.testing.tween;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import aurelienribon.tweenengine.TweenManager;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;


public class TweenApplication implements ApplicationListener {
	
	 	private OrthographicCamera camera;
	    private SpriteBatch batch;
	    private Texture texture;
	    private Sprite sprite1;
	    private TweenManager tweenManager;


	    public void setTweenManager(TweenManager tm){
	    	this.tweenManager = tm;
	    }
	    
	       float screenW = 480;
	        float screenH = 480;
	        float w = 1;
	        float h = w * screenH / screenW;
	    
	@Override
	public void create() {
		
		setTweenManager(new TweenManager());
		
		camera = new OrthographicCamera(w, h);		
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("data/test.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        sprite1 = new Sprite(texture);
        sprite1.setPosition(-w/2, -h/2);
        
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        
        Timeline timeline = Timeline.createParallel()
                .beginSequence()
                .push(Tween.to(sprite1, SpriteAccessor.POS_XY, 3).target(100, 200))
                .push(Tween.to(sprite1, SpriteAccessor.POS_XY, 2).target(200, 200))
                .push(Tween.to(sprite1, SpriteAccessor.POS_XY, 3).target(400, 200))
                .push(Tween.to(sprite1, SpriteAccessor.POS_XY, 3).target(400, 400))
                .end()
                .start(this.tweenManager);
                
        
       // Tween.call(windCallback).start(tweenManager);
	}

	@Override
	public void dispose() {
		batch.dispose();
        texture.dispose();
        //font.dispose();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		   Gdx.gl.glClearColor(1, 1, 1, 1);
	       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	 
	        batch.begin();
	        sprite1.draw(batch);
	        batch.end();
	        
	        
	        tweenManager.update(Gdx.graphics.getDeltaTime());      

	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	
    public static void main(String[] args){
    	
    	new LwjglApplication(new TweenApplication());
    }
    
    private final TweenCallback windCallback = new TweenCallback() {
        @Override
        public void onEvent(int type, BaseTween<?> source) {
            float d = MathUtils.random() * 0.5f + 0.5f;   // duration
            
            Tween.to(sprite1, SpriteAccessor.POS_XY, d)
                .target(100, 200)
                .repeatYoyo(1, 0)
                .setCallback(windCallback)
                .start(tweenManager);
            
        }
    };
    
    private void setCamera(){
    	
    }
}
