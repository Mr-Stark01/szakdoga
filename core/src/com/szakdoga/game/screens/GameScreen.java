package com.szakdoga.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.szakdoga.game.TowerDefence;

public class GameScreen extends ScreenAdapter {
    final TowerDefence game;
    Texture bg;
    public GameScreen(TowerDefence game){
        this.game = game;
    }
    @Override
    public void show(){
        bg = new Texture("menu/start_menu.png");
    }
    @Override
    public void render(float delta){
        game.batch.begin();
        game.batch.draw(bg,0,0,game.screenWidth,game.screenHeight);
        game.batch.end();
    }
}
