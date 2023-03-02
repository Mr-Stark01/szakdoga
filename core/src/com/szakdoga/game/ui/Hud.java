package com.szakdoga.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import static com.szakdoga.game.TowerDefence.font; //TODO is this better then wranling it trough everywhere?

public class Hud {
    private Stage stage;
    private Table table;
    private TextButton.TextButtonStyle style;
    public Hud() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.setDebug(true);
        style = new TextButton.TextButtonStyle();
        style.font = font;
        style.font.getData().setScale(0.7f,0.7f);
        style.font.setColor(Color.BLUE);
        table.bottom().add(new TextButton("alja", style)).minHeight(0);
        table.row().expand();
        table.add(new Actor()).minHeight(Gdx.graphics.getHeight()/1.5f);
        table.row();
        table.add(new TextButton("teteje",style)).minHeight(0);
        style.font.getData().setScale(0.4f,0.4f);
    }
    public void render(){
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
