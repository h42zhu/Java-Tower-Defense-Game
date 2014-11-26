package com.mygdx.gameTD.Entity;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gameTD.BuildingManager;
import com.mygdx.gameTD.ImageManager;
import com.mygdx.gameTD.MainGame;



public class GameWorld {
	protected Texture background_image;
	
	public GamePanel panel;
	
	public HashMap<Integer, Minions> enemy_minions;
	public HashMap<Integer, Minions> my_minions;
	public HashMap<Integer, Building> my_buildings;
	public HashMap<Integer, Projectile> projectile;
	
	public Array<Integer> remove_enemy_minions;
	public Array<Integer> remove_my_minions;
	public Array<Integer> remove_my_buildings;
	public Array<Integer> remove_projectile;
	
	public Integer enemy_minions_id, my_minions_id, my_buildings_id, projectile_id;
	
	
	public Array<Rectangle> obstacles;
	public String selected_icon_name;
	public boolean selected_bool;
	
	public static int upperBound = MainGame.HEIGHT;
	public static int rightBound = MainGame.WIDTH * (7 / 8);
	
	public GameWorld(Texture background_image){
		this.panel = new GamePanel();
		
		this.background_image = background_image;
		this.enemy_minions = new HashMap<Integer, Minions>();
		this.my_minions = new HashMap<Integer, Minions>();
		this.my_buildings = new HashMap<Integer, Building>();
		this.projectile = new HashMap<Integer, Projectile>();
		
		this.remove_enemy_minions = new Array<Integer>(false, 20);
		this.remove_my_minions = new Array<Integer>(false, 20);
		this.remove_my_buildings = new Array<Integer>(false, 20);
		this.remove_projectile = new Array<Integer>(false, 50);
		
		this.selected_icon_name = "";
		this.selected_bool = false;
		
		this.enemy_minions_id = 0;
		this.my_minions_id = 0;
		this.my_buildings_id = 0;
		this.projectile_id = 0;
		
		//(Texture texture, Vector2 pos, int build_cost, int max_health) 
		TownCenter my_towncenter = new TownCenter(ImageManager.TownCenter, new Vector2(600, 300), 0, 2000, this);
		add_building(my_towncenter);
	}
	
	// 
	public void add_minion(Minions m){
		boolean s = m.side;
		if (s){
			m.id = this.my_minions_id;
			my_minions.put(my_minions_id, m);
			this.my_minions_id++;
		}
		else {
			m.id = this.enemy_minions_id;
			enemy_minions.put(enemy_minions_id, m);
			this.enemy_minions_id++;
		}
	}
	
	public void remove_minion(Minions m){
		boolean s = m.side;
		if (s){
			remove_my_minions.add(m.id);
		}
		else {
			remove_enemy_minions.add(m.id);
		}
	}
	
	public void add_building(Building b){
		b.id = this.my_buildings_id;
		my_buildings.put(my_buildings_id, b);
		this.my_buildings_id++;
	}
	
	public void remove_building(Building b){
		remove_my_buildings.add(b.id);
	}
	
	public void add_projectile(Projectile p){
		p.id = this.projectile_id;
		projectile.put(projectile_id, p);
		this.projectile_id++;
	}
	
	public void remove_projectile(Projectile p) {
		remove_projectile.add(p.id);
	}
	
	public void render(SpriteBatch sb){
		this.panel.renderIcons(sb);
		// System.out.println("someting");
		if (! my_minions.isEmpty()){
			for (Minions m : my_minions.values()) {
				m.render(sb);
			}
		}
		if (! enemy_minions.isEmpty()){
			for (Minions m : enemy_minions.values()) {
				m.render(sb);
			}
		}
		if (! my_buildings.isEmpty()){
			for (Building b : my_buildings.values()) {
				b.render(sb);
			}
		}
		if (! projectile.isEmpty()){
			for (Projectile p : projectile.values())  {
				p.render(sb);
			}
		}
		// System.out.println(projectile.isEmpty());
		
	}
	

	
	public void Update(){
		// System.out.println(Projectile.inst_num);
		if (! my_minions.isEmpty()){	
			for (Minions m : my_minions.values()) {
				m.Update();
			}
		}
		if (! enemy_minions.isEmpty()){
			for (Minions m : enemy_minions.values()) {
				m.Update();
			}
		}
		if (! my_buildings.isEmpty()){
			for (Building b : my_buildings.values())  {
				b.Update();
			}
		}
		if (! projectile.isEmpty()){
			for (Projectile p : projectile.values())  {
				p.Update();
			}
		}
		// Remove entities
		remove_entity();
	}
	
	public void remove_entity(){
		for (int i = 0; i < this.remove_enemy_minions.size; i++){
			this.enemy_minions.remove(this.remove_enemy_minions.get(i));
		}
		this.remove_enemy_minions.clear();
		
		for (int i = 0; i < this.remove_my_minions.size; i++){
			this.my_minions.remove(this.remove_my_minions.get(i));
		}
		this.remove_my_minions.clear();
		
		for (int i = 0; i < this.remove_my_buildings.size; i++){
			this.my_buildings.remove(this.remove_my_buildings.get(i));
		}
		this.remove_my_buildings.clear();
		
		for (int i = 0; i < this.remove_projectile.size; i++){
			this.projectile.remove(this.remove_projectile.get(i));
		}
		this.remove_projectile.clear();
		
	}
	
	public Building get_closest_building(Vector2 pos){
		Building target = null;
		// Minions friend = null;
		float dst2;
		float min_dst = 1000000;
		for (Building b : my_buildings.values()) {
		    dst2 = pos.dst2(b.pos);
		    if (dst2 < min_dst && dst2 > 0){
		    	min_dst = dst2;
		    	target = b;
		    }
		}
		
		return target;
	}
	
	
	public Minions get_closest_target(boolean side, Vector2 pos, boolean getTarget){
		Minions target = null;
		// Minions friend = null;
		float dst2;
		float min_dst = 1000000;
		if ((side && getTarget) || (!side && !getTarget)){
			for (Minions m : enemy_minions.values()) {
			    dst2 = pos.dst2(m.pos);
			    if (dst2 < min_dst && dst2 > 0){
			    	min_dst = dst2;
			    	target = m;
			    }
			}
		}
		else{
			for (Minions m : my_minions.values()) {
			    dst2 = pos.dst2(m.pos);
			    if (dst2 < min_dst){
			    	min_dst = dst2;
			    	target = m;
			    }
			}
		}
		return target;
	}
	
	
	public boolean isValidBuildingLocation(Vector2 vec, String bname){
		// boolean isValid = true;
		Rectangle rec = BuildingManager.getBuildingRec(vec, bname);
		if (Intersector.overlaps(rec, this.panel.panelRect)){
			return false;
		}
		
		for (Building b : my_buildings.values()){
			if (Intersector.overlaps(rec, b.rect)){
				return false;
			}
		}
		
		for (Minions m : enemy_minions.values()){
			if (Intersector.overlaps(rec, m.rect)){
				return false;
			}
		}
		
		for (Minions m : my_minions.values()){
			if (Intersector.overlaps(rec, m.rect)){
				return false;
			}
		}
		
		// Add Loop for Obstacles
		// Add distance to Towncenter
		return true;
	}
	
	public void selectAction(float x, float y){
		if (this.panel.panelRect.contains(x, y)){
			for (int i = 0; i < this.panel.my_building_icon.size; i++){
				if (this.panel.my_building_icon.get(i).rect.contains(x, y)){
					this.selected_bool = true;
					this.selected_icon_name = this.panel.my_building_icon.get(i).getname();
				}
			}	
		}
	}

	
}
