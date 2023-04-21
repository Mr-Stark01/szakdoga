package com.szakdoga.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.screens.MainMenu;



public class TowerDefence extends Game {
	public static BitmapFont font;
	public SpriteBatch batch;
	public BitmapFont fontHover; //Hover as in when mouse is over it
	public int screenHeight;
	public int screenWidth;
	public static Logger LOGGER;


	@Override
	public void create () {
		batch = new SpriteBatch();
		font = FontCreator.createFont();
		fontHover = FontCreator.createFont(150, Color.ORANGE,"fonts/Kanit-Black.ttf");
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		LOGGER.writeLog("LOG","Succesfull startup lwjgl started",this.getClass().getSimpleName());
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		fontHover.dispose();
	}
}
