package com.szakdoga.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.szakdoga.game.FontCreator;
import com.szakdoga.game.Logger;
import com.szakdoga.game.screens.inputHandlers.InputHandler;
import com.szakdoga.game.towers.ArcherTower;
import com.szakdoga.game.units.Unit;

import java.util.Date;

import static com.szakdoga.game.screens.GameScreen.player;
import static com.szakdoga.game.towers.Tower.*;

/**
 * Displays HUD and handles HUD interactions
 */
public class Hud implements Disposable {
    private Stage stage;
    private Table tableTop;
    private Table tableBottom;
    private TextButton.TextButtonStyle style;
    private Label.LabelStyle labelStyle;
    private Label moneyLabel;
    private Label healthLabel;
    private Label unitNumberLabel;
    private Viewport viewport;
    private long lastPressedButton = new Date().getTime();

    public Hud(final InputMultiplexer multiplexer, SpriteBatch batch, final InputHandler inputHandler) {

        BitmapFont font = FontCreator.createFont(75);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);
        multiplexer.addProcessor(stage);
        tableTop = new Table();
        tableTop.setFillParent(true);
        tableTop.setDebug(true);
        tableBottom = new Table();
        tableBottom.setFillParent(true);
        tableBottom.setDebug(true);


        style = new TextButton.TextButtonStyle();
        labelStyle = new Label.LabelStyle();
        labelStyle.font=font;
        style.font = font;
        style.font.setColor(Color.BLUE);
        ImageButton archerTowerHudElement = new ImageButton(new TextureRegionDrawable(new Texture("textures/archertower.png")));
        ImageButton crossBowTowerHudElement = new ImageButton(new TextureRegionDrawable(new Texture("textures/crossbowtower.png")));
        ImageButton wizzardTowerHudElement = new ImageButton(new TextureRegionDrawable(new Texture("textures/wizardtower.png")));
        /**
         * add different eventListeners here for all the clickable elements of the hud
         */
        tableTop.top();
        archerTowerHudElement.addListener(new ClickListener(){ //TODO esetleg refractor
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(new Date().getTime()-lastPressedButton>1000) {
                    inputHandler.draggingArcher("textures/archertower.png", 4);
                    player.create(createArcherTower(0, 0));
                    lastPressedButton=new Date().getTime();
                    Logger.writeLogDisplayLog("log","player bought archer tower",this.getClass().getSimpleName());
                }
            }
        });
        tableTop.add(archerTowerHudElement).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        crossBowTowerHudElement.addListener(new ClickListener(){ //TODO esetleg refractor
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(new Date().getTime()-lastPressedButton>1000) {
                    inputHandler.draggingArcher("textures/crossbowtower.png",8);
                    player.create(createCrossBowTower(0, 0));
                    lastPressedButton=new Date().getTime();
                    Logger.writeLogDisplayLog("log","player bought crossbow tower",this.getClass().getSimpleName());
                }
            }
        });
        tableTop.add(crossBowTowerHudElement).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        wizzardTowerHudElement.addListener(new ClickListener(){ //TODO esetleg refractor
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(new Date().getTime()-lastPressedButton>1000) {
                    inputHandler.draggingArcher("textures/wizardtower.png",3);
                    player.create(createWizardTower(0,0));
                    lastPressedButton=new Date().getTime();
                    Logger.writeLogDisplayLog("log","player bought wizard tower",this.getClass().getSimpleName());
                }
            }
        });
        tableTop.add(wizzardTowerHudElement).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);

        ImageButton knightUnitHudElement = new ImageButton(new TextureRegionDrawable(new Texture("textures/knightunit.png")));
        ImageButton wizardUnitHudElement = new ImageButton(new TextureRegionDrawable(new Texture("textures/wizardunit.png")));
        ImageButton pikeUnitHudElement = new ImageButton(new TextureRegionDrawable(new Texture("textures/pikeunit.png")));
        knightUnitHudElement.addListener(new ClickListener(){ //TODO esetleg refractor
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(new Date().getTime()-lastPressedButton>1000) {
                    player.buyUnit(Unit.createKnightUnit(player.getPositionX(), player.getPositionY(), "Knight"));
                    lastPressedButton=new Date().getTime();
                    Logger.writeLogDisplayLog("log","player bought knight unit",this.getClass().getSimpleName());
                }
            }
        });
        tableTop.add(knightUnitHudElement).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        wizardUnitHudElement.addListener(new ClickListener(){ //TODO esetleg refractor
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(new Date().getTime()-lastPressedButton>1000) {
                    player.buyUnit(Unit.createWizardUnit(player.getPositionX(), player.getPositionY(), "Wizard"));
                    lastPressedButton=new Date().getTime();
                    Logger.writeLogDisplayLog("log","player bought wizard unit",this.getClass().getSimpleName());
                }
            }
        });
        tableTop.add(wizardUnitHudElement).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        pikeUnitHudElement.addListener(new ClickListener(){ //TODO esetleg refractor
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(new Date().getTime()-lastPressedButton>1000) {
                    player.buyUnit(Unit.createPikeUnit(player.getPositionX(), player.getPositionY(), "Pike"));
                    lastPressedButton=new Date().getTime();
                    Logger.writeLogDisplayLog("log","player bought pike unit",this.getClass().getSimpleName());
                }
            }
        });
        tableTop.add(pikeUnitHudElement).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);



        tableBottom.bottom();

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        moneyLabel=new Label(Integer.toString(0),labelStyle);
        healthLabel=new Label(Integer.toString(0),labelStyle);
        unitNumberLabel=new Label(Integer.toString(0),labelStyle);
        Image coin = new Image(new TextureRegionDrawable(new Texture("textures/coin.png")));
        Image health = new Image(new TextureRegionDrawable(new Texture("textures/health.png")));
        Image unit = new Image(new TextureRegionDrawable(new Texture("textures/tower.png")));//todo not unit
        tableBottom.add(coin).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        tableBottom.add(moneyLabel);
        tableBottom.add(health).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        tableBottom.add(healthLabel);
        tableBottom.add(unit).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        tableBottom.add(unitNumberLabel);

        tableBottom.add(horizontalGroup).height(Gdx.graphics.getHeight()/6f);
        stage.addActor(tableTop);
        stage.addActor(tableBottom);
        style.font.getData().setScale(0.4f,0.4f);
    }

    public void render(){
        updateMoney();
        updateHealth();
        updateUnitNumber();
        stage.draw();
    }
    public void updateMoney(){
        moneyLabel.setText(Integer.toString(player.getMoney()));
    }
    public void updateHealth(){
        healthLabel.setText(Float.toString(player.getHealth()));
    }
    public void updateUnitNumber(){
        unitNumberLabel.setText(player.getTowers().size());
    }
    public void dispose() {

    }
}
