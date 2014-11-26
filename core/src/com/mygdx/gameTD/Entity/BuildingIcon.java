package com.mygdx.gameTD.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.gameTD.MainGame;

public class BuildingIcon extends Building{
	
	private String name;
	protected boolean clicked;
	private ShapeRenderer shapeRenderer;
	private int iconWidth, iconHeight;
	public Rectangle rect;

	public BuildingIcon(Texture texture, Vector2 pos, int cost,
			int max_health, String name) {
		super(texture, pos, cost, max_health);
		this.name = name;
		this.shapeRenderer = new ShapeRenderer();
		this.clicked = false;
		
		this.iconWidth = MainGame.WIDTH / 8;
		this.iconHeight = MainGame.HEIGHT / 5;
		this.rect = new Rectangle(pos.x, pos.y, this.iconWidth, this.iconHeight);

	}

	@Override
	void Update() {

		
	}
	
	@Override
	public void render(SpriteBatch sb){
		
		sb.begin();
		sb.draw(texture, pos.x, pos.y, this.iconWidth, this.iconHeight);

		sb.end();
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 0, 1);
		shapeRenderer.rect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
		shapeRenderer.end();
		
	}

	public String getname() {
		return this.name;
	}
	
}
