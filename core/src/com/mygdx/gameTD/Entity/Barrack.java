package com.mygdx.gameTD.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.gameTD.ImageManager;

public class Barrack extends Building{
	
	private long lastSpawnTime;
	private long spawnRate;
	
	private Minions current_Minion;
	public GameWorld world;

	public Barrack(Texture texture, Vector2 pos, int build_cost, int max_health, GameWorld world) {
		super(texture, pos, build_cost, max_health);
		this.world = world;
		this.current_Minion = new Minions(ImageManager.Ant, pos.cpy(),
				50, 2, 1, null, 200, 20, true, world);
		// 
		this.spawnRate = 10;
		this.lastSpawnTime = TimeUtils.nanoTime();
	}

	@Override
	void Update() {
		if (TimeUtils.nanoTime() - this.lastSpawnTime > this.spawnRate*500000000){
			Spawn();
			this.lastSpawnTime = TimeUtils.nanoTime();
		}
		
	}
	
	void Spawn(){
		// (Texture self_texture, Vector2 pos, int attack_range, 
		// int attack_speed, int attack_dmg, Texture bullet_image, 
		// int max_health, int speed, boolean side, GameWorld world) 
		Minions m = new Minions(this.current_Minion.texture, this.pos.cpy(),
				this.current_Minion.attack_range, this.current_Minion.attack_speed,
				this.current_Minion.attack_dmg, this.current_Minion.bullet_image, 
				this.current_Minion.max_health, this.current_Minion.speed,
				this.current_Minion.side, this.current_Minion.world);
		
		this.world.add_minion(m);
	}

}
