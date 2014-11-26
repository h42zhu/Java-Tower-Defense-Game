package com.mygdx.gameTD.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Building {
	protected Texture texture;
	public Vector2 pos;
	public int build_cost, max_health, cur_health;
	protected Integer id;
	public boolean alive;
	public Rectangle rect;


	
	public Building(Texture texture, Vector2 pos, int build_cost, int max_health){
		this.id = 0;
		this.texture = texture;
		this.pos = pos;
		this.build_cost = build_cost;
		this.max_health = max_health;
		this.cur_health = max_health;
		this.alive = true;
		this.rect =  new Rectangle(this.pos.x,this.pos.y, texture.getWidth(),texture.getHeight());

	}
	
	abstract void Update();
	
	// abstract void Destroy();
	
	public void render(SpriteBatch sb){
		
		sb.begin();
		sb.draw(texture, pos.x, pos.y);
		sb.end();
	}

	public void Hit(int attack_dmg) {
		// TODO Auto-generated method stub
		this.cur_health = this.cur_health - attack_dmg;
		if (this.cur_health <= 0){
			this.alive = false;
		}
	}
	
}
