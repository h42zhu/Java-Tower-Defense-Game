package com.mygdx.gameTD.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public abstract class GameEntity {
	protected Texture texture;
	public Vector2 pos;
	public Vector2 heading, velocity;
	public int health, max_health;
	private ShapeRenderer sp;

	
	public GameEntity(Texture texture, Vector2 pos, int max_health){
		this.texture = texture;
		this.pos = pos;
		this.heading = new Vector2(0, 0);
		this.velocity = new Vector2(0, 0);
		this.max_health = max_health;
		this.health = max_health;
		this.sp = new ShapeRenderer();
	}
	
	abstract void Update();
	
	public void render(SpriteBatch sb){
		
		sb.begin();
		sb.draw(texture, pos.x, pos.y);
		sb.end();
		
		if (this.health > 0){
			 sp.begin(ShapeType.Filled);
			 sp.setColor(1, 1, 0, 1);
			 sp.rect(pos.x + texture.getWidth()/4, pos.y + texture.getHeight(), 25, 4);
			 sp.setColor(0, 1, 0, 1);
			 sp.rect(pos.x + texture.getWidth()/4, pos.y + texture.getHeight(), 
					 (float) health/ max_health * 25, 4);
			 sp.end();
		}
	}
	
}
