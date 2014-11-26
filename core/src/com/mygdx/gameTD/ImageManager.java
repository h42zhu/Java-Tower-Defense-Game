package com.mygdx.gameTD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class ImageManager {
	public static Texture Ant = new Texture(Gdx.files.internal("Ant.png"));
	public static Texture Spider = new Texture(Gdx.files.internal("Spider.png"));
	public static Texture Rock = new Texture(Gdx.files.internal("Rock.png"));
	public static Texture Obelick = new Texture(Gdx.files.internal("Obelisk2.png"));
	public static Texture Fireball = new Texture(Gdx.files.internal("rfireball.png"));
	public static Texture Arrow = new Texture(Gdx.files.internal("arrow1.png"));
	public static Texture Barrack = new Texture(Gdx.files.internal("Barrack1.png"));
	
	public static Texture TownCenter = new Texture(Gdx.files.internal("TownCenter.gif"));
	public static Texture House = new Texture(Gdx.files.internal("House1.gif"));
	
	
	public static Music bgMusic = Gdx.audio.newMusic(Gdx.files.internal("newgame.ogg"));
}
