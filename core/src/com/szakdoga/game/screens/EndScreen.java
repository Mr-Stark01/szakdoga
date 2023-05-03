package com.szakdoga.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.szakdoga.game.config.DisplayConfig;
import com.szakdoga.game.FontCreator;

public class EndScreen extends ScreenAdapter {
    BitmapFont font = FontCreator.createFont(200, Color.BLACK);
    private SpriteBatch batch;
    private String status;
    private Color color;
    public EndScreen(String status){
        if(status.equals(DisplayConfig.WIN_TEXT)){
            color=DisplayConfig.BLUE_COLOR;
        }
        else{
            color=DisplayConfig.CRIMSON_COLOR;
        }
        this.status=status;
    }
    @Override
    public void show(){
        batch=new SpriteBatch();

    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(color);
        batch.begin();
        font.draw(batch,status, Gdx.graphics.getHeight()/2,Gdx.graphics.getWidth()/2);
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
            System.exit(0);
        }
        batch.end();
    }
}
