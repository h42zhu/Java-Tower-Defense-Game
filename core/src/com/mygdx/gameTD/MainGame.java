package com.mygdx.gameTD;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.gameTD.Screen.GameScreen;
import com.mygdx.gameTD.Screen.ScreenManager;

public class MainGame extends ApplicationAdapter {
	public static int WIDTH = 800, HEIGHT = 480;
	public static OrthographicCamera camera;
	
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		camera.translate(WIDTH/2, HEIGHT/2);
		camera.update();
		
		// Gdx.input.setInputProcessor(this);
		// img = new Texture("badlogic.jpg");
		ScreenManager.setScreen(new GameScreen());

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		 // tell the camera to update its matrices.
		 camera.update();

	     // tell the SpriteBatch to render in the
	     // coordinate system specified by the camera.
	     batch.setProjectionMatrix(camera.combined);
		
		 if (ScreenManager.getCurrentScreen() != null){
			 ScreenManager.getCurrentScreen().render(batch);
		 }
		// ScreenManager.getCurrentScreen().render(batch);
		
		// batch.begin();
		// batch.draw(img, 0, 0);
		// batch.end();
		ScreenManager.getCurrentScreen().update();
		ScreenManager.getCurrentScreen().render(batch, camera);	
		
	}
	
	@Override
	public void dispose(){
		if (ScreenManager.getCurrentScreen() != null){
			ScreenManager.getCurrentScreen().dispose();
		}
	}
	
	@Override
	public void resize(int width, int height){
		if (ScreenManager.getCurrentScreen() != null){
			ScreenManager.getCurrentScreen().resize(width, height);
		}
	}
	
	
}
