package com.szakdoga.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.szakdoga.game.TowerDefence;

public class MainMenu extends ScreenAdapter {
    final TowerDefence game;
    protected OrthographicCamera camera;
    Texture bg;
    public MainMenu(final TowerDefence game){
        this.game=game;
        camera = new OrthographicCamera();

        camera.setToOrtho(false, 1920, 1080);
        game.batch.setProjectionMatrix(camera.combined);
    }
    @Override
    public void show(){
        bg = new Texture("menu/start_menu.png");
    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(1, 0, 0, 1);
        camera.update();
        game.batch.begin();
        game.batch.draw(bg, 0 , 0, 1920, 1080);
        game.batch.end();
    }
}
