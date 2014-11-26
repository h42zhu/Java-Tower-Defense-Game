package com.mygdx.gameTD.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.gameTD.StateMachine.BasicStateMachine;

public class Minions extends GameEntity{
	protected int attack_range, attack_speed, attack_dmg, speed;
	
	
	protected Integer id;
	protected boolean alive, side; // side = true -> myside; false -> enemyside
	protected Texture bullet_image;
	public GameWorld world;
	public BasicStateMachine brain;
	public Minions closest_friend;
	public Rectangle rect;

	public Minions(Texture self_texture, Vector2 pos, int attack_range, 
			int attack_speed, int attack_dmg, Texture bullet_image, 
			int max_health, int speed, boolean side, GameWorld world) {
		super(self_texture, pos, max_health);
		this.attack_range = attack_range;
		this.attack_dmg = attack_dmg;
		this.attack_speed = attack_speed;
		// this.max_health = max_health;
		this.bullet_image = bullet_image;
		this.speed = speed;
		// this.health = max_health;
		this.alive = true;
		this.side = side;
		this.id = 0;
		this.world = world;
		this.rect =  new Rectangle(this.pos.x,this.pos.y,
				this.texture.getWidth(),this.texture.getHeight());
		// (GameWorld world, boolean side, int attack_rng, Vector2 pos)
		this.brain = new BasicStateMachine(this.world, this.side, this.attack_range, this.pos);
	}

	// @Override
	
	
	@Override
	void Update() {
		// 
		// System.out.println(this.brain.current_state);
		if (! this.alive){
			this.world.remove_minion(this);
			return;
		}
		
		String current_state = this.brain.think(this.pos);
		
		if (current_state == "attack"){
			this.attack(this.brain.get_closest_target());
		} else if (current_state == "move"){
			this.move(this.brain.get_destination());
		} else if (current_state == "attack_building"){
			this.attack(this.brain.get_closest_building());
			// System.out.println(this.brain.get_closest_building());
			// idle, maybe move randomly in a circle
		}

	}
	
	void move(Vector2 get_destination) {
		// checking if out of boundary
		if (this.pos.y < 0){
			this.heading.y = 1;
			this.velocity.set(this.heading).scl(this.speed);
			this.pos.add(velocity.scl(Gdx.graphics.getDeltaTime()));
		} else if (this.pos.y > GameWorld.upperBound){
			this.heading.y = -1;
			this.velocity.set(this.heading).scl(this.speed);
			this.pos.add(velocity.scl(Gdx.graphics.getDeltaTime()));
		}
		// Gdx.graphics.getDeltaTime() 
		this.closest_friend = this.brain.get_closest_friend();
		if (this.closest_friend != null){
			if (ImagesCollide(this.texture, this.pos.x, this.pos.y, this.closest_friend.texture,
					this.closest_friend.pos.x, this.closest_friend.pos.y)){
				// todo: need resolution here
				// System.out.println(this.pos);
				// System.out.println(this.closest_friend.pos);
				if (this.pos.y >= this.closest_friend.pos.y){
					// System.out.println(this.heading);
					this.heading.rotate(90);
				}
				// this.heading.rotate(25); 
				// this.closest_friend.heading.rotate(-5);
				this.velocity.set(this.heading).scl(this.speed);
				this.pos.add(velocity.scl(Gdx.graphics.getDeltaTime()));
				
			}		
		}

		if ((this.speed > 0) && (! this.pos.epsilonEquals(get_destination, (float) 0.1))){
			this.heading.set(get_destination).sub(this.pos).nor();
			this.velocity.set(this.heading).scl(this.speed);
			this.pos.add(velocity.scl(Gdx.graphics.getDeltaTime()));
			
		}
		
	}

	void attack(Minions closest_target) {
		closest_target.Hit(this.attack_dmg);
		
	}
	
	void attack(Building b){
		b.Hit(this.attack_dmg);
	}

	void Hit(int dmg_taken){
		this.health = this.health - dmg_taken;
		if (this.health <= 0){
			this.speed = 0;
			this.alive = false;
		}
	}
	
	public static boolean ImagesCollide(Texture Image1,float x, float y, Texture Image2, float x2, float y2){
//		Rectangle Rec1 = new Rectangle(x-Image1.getWidth()/2,y-Image1.getHeight()/2,Image1.getWidth(),Image1.getHeight());
//		Rectangle Rec2 = new Rectangle(x2-Image2.getWidth()/2,y2-Image2.getHeight()/2,Image2.getWidth(),Image2.getHeight());
		// 
		Rectangle Rec1 = new Rectangle(x,y,Image1.getWidth(),Image1.getHeight());
		Rectangle Rec2 = new Rectangle(x2,y2,Image2.getWidth(),Image2.getHeight());
		return Intersector.overlaps(Rec1, Rec2);
	}

}
