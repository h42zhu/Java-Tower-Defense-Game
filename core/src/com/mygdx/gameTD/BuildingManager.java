package com.mygdx.gameTD;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.gameTD.Entity.Barrack;
import com.mygdx.gameTD.Entity.Building;
import com.mygdx.gameTD.Entity.GameWorld;
import com.mygdx.gameTD.Entity.Projectile;
import com.mygdx.gameTD.Entity.Tower;

public class BuildingManager {
	
	private static final int BaseTowerWidth = 50;
	private static final int BaseTowerHeight = 100;
	
	private static final int BarrackWidth = 50;
	private static final int BarrackHeight = 100;
	
	
	public static Rectangle getBuildingRec(Vector2 pos, String bname){
		Rectangle rec = new Rectangle();
	
		if (bname.equals("BaseTower")){
			rec = new Rectangle(pos.x, pos.y, BaseTowerWidth, BaseTowerHeight);
		}else if (bname.equals("Barrack")){
			rec = new Rectangle(pos.x, pos.y, BarrackWidth, BarrackHeight);
		}
		
		return rec;
	}
	
	
	public static Building contructBasicBuilding(String bname, Vector2 pos, GameWorld world){
		if (bname.equals("BaseTower")){
			Projectile p = new Projectile(ImageManager.Rock, pos.cpy(),
					world, null, null, 0, 300);
			Tower t1 = new Tower(ImageManager.Obelick, pos.cpy(),
					100, 1000, world, 500, 15, 2, p);
			
			return t1;
		}else if (bname.equals("Barrack")){
			// (Texture texture, Vector2 pos, int build_cost, int max_health, GameWorld world)
			Barrack b1 = new Barrack(ImageManager.Barrack, pos.cpy(),
					100, 1000, world);
			return b1;
		}
		
		return null;
	}
	
	
}
