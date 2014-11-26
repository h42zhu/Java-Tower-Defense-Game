package com.mygdx.gameTD.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Screen {
	public void create();
	
	public void render(SpriteBatch sb);
	
	public void resize(int width, int height);
	
	public void dispose();
	
	public void pause();
	
	public void resume();
	
	public void update();

	public void render(SpriteBatch sb, OrthographicCamera camera);
}
