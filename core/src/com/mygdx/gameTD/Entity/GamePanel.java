package com.mygdx.gameTD.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gameTD.ImageManager;
import com.mygdx.gameTD.MainGame;

public class GamePanel {
	public Array<BuildingIcon> my_building_icon;
	public Rectangle panelRect;
	private ShapeRenderer shapeRenderer;
	public int panelWidth; 
	
	
	public GamePanel(){
		this.my_building_icon = new Array<BuildingIcon>(false, 10);
		this.panelWidth = MainGame.WIDTH / 6;
		this.panelRect = new Rectangle(MainGame.WIDTH - panelWidth, 0, panelWidth, MainGame.HEIGHT);
		this.initIcons();
		this.shapeRenderer = new ShapeRenderer();
	}
	
	public void add_icon(BuildingIcon icon){
		my_building_icon.add(icon);
	}
	
	public void renderIcons(SpriteBatch sb){
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(1, 1, 1, 1);
		shapeRenderer.rect(this.panelRect.x, this.panelRect.y, this.panelRect.width, this.panelRect.height);
		shapeRenderer.end();
		
		for (int i = 0; i < this.my_building_icon.size; i++){
			my_building_icon.get(i).render(sb);
		}
		
		
	}
	
	private void initIcons(){
		// this.my_building_icon
		BuildingIcon icon1 = new BuildingIcon(ImageManager.Obelick, new Vector2(710, 350), 
				0, 0, "BaseTower");
		my_building_icon.add(icon1);
		
		BuildingIcon icon2 = new BuildingIcon(ImageManager.Barrack, new Vector2(710, 250), 
				0, 0, "Barrack");
		my_building_icon.add(icon2);
	}
	
}
