package com.szakdoga.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.screens.MainMenu;

public class TowerDefence extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenu(this));
		img =  new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		img.dispose();
	}
}
