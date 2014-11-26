package com.mygdx.gameTD.StateMachine;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.gameTD.Entity.Building;
import com.mygdx.gameTD.Entity.GameWorld;
import com.mygdx.gameTD.Entity.Minions;
import com.mygdx.gameTD.Screen.GameScreen;


public class BasicStateMachine {
	
	public Vector2 pos, destination;
	Rectangle rally_point;
	protected boolean side;
	protected int attack_rng;
	public String current_state;
	public Minions closest_target, closest_friend;
	protected Building closest_building;
	public GameWorld world;
	
	public BasicStateMachine(GameWorld world, boolean side, int attack_rng, Vector2 pos){
		this.attack_rng = attack_rng;
		this.pos = pos;
		this.world = world;
		this.side = side;
		this.current_state = "idle";
		this.rally_point = new Rectangle(0, 0, 5, 5);
		if (this.side ){
			this.destination = new Vector2(0, 0);
		} else {
			this.destination = new Vector2(400, 200);
		}
		
		this.closest_target = this.world.get_closest_target(this.side, this.pos, true);
		this.closest_friend = this.world.get_closest_target(this.side, this.pos, false);
		this.closest_building = null;
		// System.out.println(this.closest_friend == null);
		// this.town_center = new Rectangle(10, 10, 5, 5);
	}
	
	
	public Vector2 get_destination(){
		return this.destination;
	}
	
	public Minions get_closest_target(){
		return this.closest_target;
	}
	
	public Minions get_closest_friend(){
		return this.closest_friend;
	}
	
	public Building get_closest_building(){
		return this.closest_building;
	}

	public String think(Vector2 pos){
		float dst;
		
		this.pos = pos.cpy();
		this.closest_target = this.world.get_closest_target(this.side, this.pos, true);
		this.closest_friend = this.world.get_closest_target(this.side, this.pos, false);
		this.closest_building = this.world.get_closest_building(this.pos);
		
		boolean targetFound = (closest_target != null);
		boolean buildingFound = (closest_building != null);
		
		if (this.side){
			// My minions
			if (targetFound){
				dst = this.pos.dst(closest_target.pos);
				if (dst <= this.attack_rng){
					this.current_state = "attack";
				} else {
					this.current_state = "move";
					this.destination = closest_target.pos;
				}
			} else {
				// no target 
				if (this.rally_point.contains(this.pos)){
					this.current_state = "idle";
				} else {
					// set destination to within rally_point
					this.current_state = "move";
				}
			}
		} else {
			// Enemy
			
			if (targetFound &&  buildingFound){
				dst = this.pos.dst(closest_target.pos);
				float dstb = this.pos.dst(closest_building.pos);
				if (dst <= dstb){
					if (dst <= this.attack_rng){
						this.current_state = "attack";
					} else {
						this.current_state = "move";
						this.destination = closest_target.pos;
					}
				} else {
					if (dstb <= this.attack_rng){
						this.current_state = "attack_building";
					} else {
						this.current_state = "move";
						this.destination = closest_building.pos;
					}
				}
			} else if (targetFound){
				dst = this.pos.dst(closest_target.pos);
				if (dst <= this.attack_rng){
					this.current_state = "attack";
				} else {
					this.current_state = "move";
					this.destination = closest_target.pos;
				}
			} else if (buildingFound){
				dst = this.pos.dst(closest_building.pos);
				if (dst <= this.attack_rng){
					this.current_state = "attack_building";
				} else {
					this.current_state = "move";
					this.destination = closest_building.pos;
				}
			} else {
				// move to towncenter
				this.current_state = "move";
				this.destination = GameScreen.TownCenterPos;
			}
			
		}
		return this.current_state;
	}
	
}
