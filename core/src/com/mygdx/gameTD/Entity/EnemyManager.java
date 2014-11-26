package com.mygdx.gameTD.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.gameTD.ImageManager;
import com.mygdx.gameTD.Entity.GameWorld;
import com.mygdx.gameTD.Entity.Minions;

public class EnemyManager {
	public GameWorld world;
	public int level;
	public static final int spawn_height_min = 100;
	public static final int spawn_height_max = 200;
	
	private long lastSpawnTime;
	private long spawnRate;
	
	public EnemyManager(GameWorld world){
		this.world = world;
		this.level = 1;
		this.spawnRate = 10;
		this.lastSpawnTime = TimeUtils.nanoTime();
	}
	
	public void spawn(int n){
		if (TimeUtils.nanoTime() - this.lastSpawnTime > this.spawnRate*500000000){
		
			for (int i = 0; i < n; i++){
				float x = 0;
				float y = MathUtils.random(spawn_height_min, spawn_height_max);
				Minions m = new Minions(ImageManager.Spider, new Vector2(x, y),
						50, 1, 1, null, 200, 20, false, this.world);
				this.world.add_minion(m);
			}
			this.lastSpawnTime = TimeUtils.nanoTime();
		}
	}
	

	
}
