package com.mygdx.gameTD.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.gameTD.BuildingManager;
import com.mygdx.gameTD.ImageManager;
import com.mygdx.gameTD.MainGame;
import com.mygdx.gameTD.Entity.Barrack;
import com.mygdx.gameTD.Entity.Building;
import com.mygdx.gameTD.Entity.BuildingIcon;
import com.mygdx.gameTD.Entity.EnemyManager;
import com.mygdx.gameTD.Entity.GameWorld;

// import com.badlogic.gdx.scenes.scene2d.ui.Skin;
// import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
// import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class GameScreen extends InputAdapter implements Screen {
	//private OrthoCamera camera;
	private GameWorld world;
	private EnemyManager eManager;
	private Music backgroundMusic;
	private TiledMap tiledMap;
	// TextureAtlas atlas;
	private TiledMapRenderer tiledMapRenderer;
	// private Stage gameStage;
	// private Stage controlStage;
	public static final Vector2 TownCenterPos = new Vector2();
	public static boolean GameOver = false;
	
	// private OrthographicCamera camera;
	// private OrthographicCamera camera;

	@Override
	public void create() {
		
		
		// gameStage = new Stage();
		// controlStage = new Stage();
		// 
		tiledMap = new TmxMapLoader().load("Background2.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		// (Texture self_texture, Vector2 pos, int attack_range, 
		// int attack_speed, int attack_dmg, Texture bullet_image, 
		// int max_health, int speed, boolean side, GameWorld world)
		this.world = new GameWorld(null);
		this.eManager = new EnemyManager(world);
		this.backgroundMusic = ImageManager.bgMusic;
		
		// cScreen = new ControlScreen(this.world);
		backgroundMusic.setLooping(true);

		
		Gdx.input.setInputProcessor(this);

	}


	@Override
	public void render(SpriteBatch sb, OrthographicCamera camera) {
		// if (this.camera == null) this.camera = camera;
		
		// sb.begin();
		// Gdx.gl.glViewport( 0,Gdx.graphics.getHeight()/4,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
	    tiledMapRenderer.setView(camera);
	    tiledMapRenderer.render();
		world.render(sb);

		// sb.end();
		// render the control part of screen
		if (GameOver){
			System.out.println("game over");
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		// gameStage.dispose();
		// controlStage.dispose();
		this.world = null;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

		eManager.spawn(3);
		world.Update();	
	}

	// Experimental Stuff
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		float x, y;
		Vector3 touchPoint = new Vector3();
		
		MainGame.camera.unproject(touchPoint.set(screenX, screenY, 0));
//		, 0, Gdx.graphics.getHeight()/4, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		x = screenX;
		y = Gdx.graphics.getHeight() - screenY;
		
		//System.out.println(x);
		//System.out.println(y);
		// System.out.println(this.world.my_buildings.get(0).pos);
		// System.out.println(this.world.my_buildings.get(1).pos);
		// System.out.println(this.world.my_building_icon.get(0).pos);
		
		if (this.world.selected_bool){
			// some icon is selected
			this.world.selected_bool = false;
			if (this.world.isValidBuildingLocation(new Vector2(x, y), 
					this.world.selected_icon_name)){
				Building b = BuildingManager.contructBasicBuilding(this.world.selected_icon_name, 
						new Vector2(x, y), this.world);
				this.world.add_building(b);
			}
			
		} else {
			this.world.selectAction(x, y);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return true;
	}
	
	
	@Override
	public void render(SpriteBatch sb) {}

}
