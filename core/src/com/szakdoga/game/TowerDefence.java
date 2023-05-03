package com.szakdoga.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.config.DisplayConfig;
import com.szakdoga.game.screens.MainMenu;

public class TowerDefence extends Game {
	public static BitmapFont font;
	public static float UIscale=1;
	public SpriteBatch batch;
	public BitmapFont fontHover; //Hover as in when mouse is over it
	public int screenHeight;
	public int screenWidth;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = FontCreator.createFont();
		fontHover = FontCreator.createFont(150, Color.ORANGE, DisplayConfig.STANDARD_FONT);
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		Logger.writeLog("LOG","Succesfull startup lwjgl started",this.getClass().getSimpleName());
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
