package com.szakdoga.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.szakdoga.game.InputHandler;
import com.szakdoga.game.towers.ArcherTower;
import com.szakdoga.game.towers.Tower;


import static com.szakdoga.game.TowerDefence.*;
import static com.szakdoga.game.screens.GameScreen.UIscale;
import static com.szakdoga.game.screens.GameScreen.player;

public class Hud implements Disposable {
    private Stage stage;
    private Table table;
    private TextButton.TextButtonStyle style;
    private Label.LabelStyle labelStyle;
    private Label moneyLabel;
    private Viewport viewport;

    public Hud(final InputMultiplexer multiplexer, SpriteBatch batch, final InputHandler inputHandler) {
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);
        multiplexer.addProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        style = new TextButton.TextButtonStyle();
        labelStyle = new Label.LabelStyle();
        labelStyle.font=font;
        labelStyle.font.getData().setScale(UIscale);
        style.font = font;
        style.font.getData().setScale(0.7f,0.7f);
        style.font.setColor(Color.BLUE);
        ImageButton towerHudElement = new ImageButton(new TextureRegionDrawable(new Texture("textures/tower.png")));
        /**
         * add different eventListeners here for all the clickable elements of the hud
         */
        //TODO how to create a tower buyyer thingi maggie
        towerHudElement.addListener(new ClickListener(){ //TODO esetleg refractor
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inputHandler.draggingArcher();
                player.create(new ArcherTower(0,0));
            }
        });
        table.top().add(towerHudElement).height(Gdx.graphics.getHeight()/6f);
        table.row();
        table.add(new Actor()).height(Gdx.graphics.getHeight()*(4/6f));
        table.row();
        HorizontalGroup horizontalGroup = new HorizontalGroup();
        moneyLabel=new Label(Integer.toString(0),labelStyle);
        horizontalGroup.addActor(moneyLabel);
        horizontalGroup.addActor(new Label("asd",labelStyle));
        horizontalGroup.addActor(new Label("asd",labelStyle));
        horizontalGroup.addActor(new Label("asd",labelStyle));
        table.add(horizontalGroup).height(Gdx.graphics.getHeight()/6f);
        stage.addActor(table);
        style.font.getData().setScale(0.4f,0.4f);
    }

    public void render(){
        stage.draw();
    }
    public void setMoney(int money){
        moneyLabel.setText(Integer.toString(money));
    }
    public void dispose() {

    }
}
