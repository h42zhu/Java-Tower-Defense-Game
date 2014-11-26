package com.mygdx.gameTD.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.gameTD.Screen.GameScreen;

public class TownCenter extends Building{
	public GameWorld world;


	
	public TownCenter(Texture texture, Vector2 pos, int build_cost,
			int max_health, GameWorld world) {
		super(texture, pos, build_cost, max_health);
		GameScreen.GameOver = false;
		this.world = world;
		// TODO Auto-generated constructor stub
	}

	@Override
	void Update() {
		if (! this.alive){
			GameScreen.GameOver = true;
			this.world.remove_building(this);
			return;
		}
	}

}
