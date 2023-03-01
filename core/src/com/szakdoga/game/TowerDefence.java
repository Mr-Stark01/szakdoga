package com.szakdoga.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.screens.MainMenu;

public class TowerDefence extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public BitmapFont fontHover;
	public int screenHeight;
	public int screenWidth;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("fonts/Kanit-Black.fnt"));
		fontHover = new BitmapFont(Gdx.files.internal("fonts/Kanit-Black-Hover.fnt"));
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
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
