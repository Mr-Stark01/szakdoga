package com.szakdoga.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.szakdoga.game.FontCreator;

public class EndScreen extends ScreenAdapter {
    BitmapFont font = FontCreator.createFont(200, Color.BLACK);
    private SpriteBatch batch;
    private String status;
    public EndScreen(String status){
        this.status=status;
    }
    @Override
    public void show(){
        batch=new SpriteBatch();

    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        font.draw(batch,status, Gdx.graphics.getHeight()/2,Gdx.graphics.getWidth()/2);
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
        batch.end();
    }
}
