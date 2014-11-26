package com.mygdx.gameTD.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Projectile extends GameEntity {
	protected GameWorld world;
	protected int dmg, speed;
	public Integer id;
	public Minions targetm;
	public Building targetb;
	public Vector2 dest, last_pos;
	public static int inst_num = 0;
	
	public Projectile(Texture texture, Vector2 pos, GameWorld world, 
			Minions targetm, Building targetb, int dmg, int speed) {
		super(texture, pos, 0);
		this.world = world;
		this.dmg = dmg;
		this.speed = speed;
		// inst_num ++;
		if (targetm != null){
			this.targetm = targetm;
			this.targetb = null;
			this.dest = this.targetm.pos.cpy();
		} else if  (targetb != null) {
			this.targetm = null;
			this.targetb = targetb;
			this.dest = this.targetb.pos.cpy();
		}
		
		

	}

	@Override
	void Update() {;
		// System.out.println(this.pos);
		boolean isCollide, isStationary;
		isStationary = this.pos.epsilonEquals(this.dest, (float) 0.1)
				|| this.pos.epsilonEquals(this.last_pos, (float) 0.01);
		
		if (this.targetm != null){
			this.dest = this.targetm.pos.cpy();
			// System.out.println(this.targetm.pos);
			// System.out.println(this.dest);
			isCollide = Minions.ImagesCollide(this.texture, this.pos.x, this.pos.y, 
					this.targetm.texture, this.targetm.pos.x, this.targetm.pos.y);

			if (isCollide || isStationary){
				this.targetm.Hit(this.dmg);
				this.world.remove_projectile(this);
				// inst_num --;
				return;
			} else {
				this.move(this.dest);
			}
		} else if (this.targetb != null) {
			this.dest = this.targetb.pos.cpy();
			isCollide = Minions.ImagesCollide(this.texture, this.pos.x, this.pos.y, 
					this.targetb.texture, this.targetb.pos.x, this.targetb.pos.y);
			if (isCollide || isStationary){
				this.targetb.Hit(this.dmg);
				this.world.remove_projectile(this);
				return;
			} else {
				this.move(this.dest);
			}
			
		}
	}
	
	void move(Vector2 get_destination) {
		this.last_pos = this.pos.cpy();
		if ((this.speed > 0) && (! this.pos.epsilonEquals(get_destination, (float) 0.1))){
			this.heading.set(get_destination).sub(this.pos).nor();
			this.velocity.set(this.heading).scl(this.speed);
			this.pos.add(velocity.scl(Gdx.graphics.getDeltaTime()));
		}
	}


}
