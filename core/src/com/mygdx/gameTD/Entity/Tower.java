package com.mygdx.gameTD.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.gameTD.StateMachine.BasicStateMachine;

public class Tower extends Building{
	
	
	public GameWorld world;
	protected int attack_range, attack_speed, attack_dmg, health;
	protected BasicStateMachine brain;
	protected Projectile proj;
	private long lastAttackTime;
	protected Minions attack_target;

	public Tower(Texture texture, Vector2 pos, int build_cost, int max_health,
			GameWorld world, int attack_range, int attack_dmg, int attack_speed, Projectile p) {
		super(texture, pos, build_cost, max_health);
		
		this.world = world;
		this.attack_range = attack_range;
		this.attack_dmg = attack_dmg;
		this.attack_speed = attack_speed;
		this.brain = new BasicStateMachine(this.world, true, this.attack_range, this.pos);
		this.proj = p;
		this.proj.pos = this.pos.cpy().add(this.texture.getWidth()/2, this.texture.getHeight() - 5);
		this.proj.dmg = this.attack_dmg;
		this.lastAttackTime = TimeUtils.nanoTime();
		
		// (GameWorld world, boolean side, int attack_rng, Vector2 pos)
		
	}

	@Override
	void Update() {
		// TODO Auto-generated method stub
		if (! this.alive){
			this.world.remove_building(this);
			return;
		}
		String current_state = this.brain.think(this.pos);
		if (current_state == "attack"){
			
			if (this.attack_target == null || (! this.attack_target.alive)){		
				this.attack_target = this.brain.get_closest_target();
			}
			
			if (TimeUtils.nanoTime() - this.lastAttackTime > this.attack_speed*500000000){
				this.attack(this.attack_target);
				this.lastAttackTime = TimeUtils.nanoTime();
			}
		}
	}
	
	void attack(Minions closest_target) {
		// (Texture texture, Vector2 pos, GameWorld world, 
		// Object targetm, targetb, int dmg, int speed)
		if (closest_target != null){
			Projectile p = new Projectile(this.proj.texture, this.proj.pos.cpy(), this.world,
					closest_target, this.proj.targetb, this.proj.dmg, this.proj.speed);
			this.world.add_projectile(p);			
		}

	}

	void attack(Building closest_target) {
		// (Texture texture, Vector2 pos, GameWorld world, 
		// Object targetm, targetb, int dmg, int speed)
		if (closest_target != null){
			Projectile p = new Projectile(this.proj.texture, this.proj.pos.cpy(), this.world,
					this.proj.targetm, closest_target, this.proj.dmg, this.proj.speed);
			this.world.add_projectile(p);
		}
	}


}
