package com.szakdoga.game.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.TowerDefence;
import com.szakdoga.game.network.DTO.Client;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameScreen extends ScreenAdapter {
    final TowerDefence game;
    private Client client;
    private SpriteBatch batch;
    private ExecutorService executor = Executors.newFixedThreadPool(20);
    Texture bg;
    public GameScreen(TowerDefence game){
        this.game = game;
        this.batch = game.batch;
        client = new Client("123.123.123.123",123,executor);
    }
    @Override
    public void show(){
        bg = new Texture("menu/start_menu.png");
    }
    @Override
    public void render(float delta){
        batch.begin();
        batch.draw(bg,0,0,game.screenWidth,game.screenHeight);
        batch.end();
    }
}
