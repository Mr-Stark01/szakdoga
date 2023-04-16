package com.szakdoga.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.szakdoga.game.screens.inputHandlers.InputHandler;
import com.szakdoga.game.towers.ArcherTower;

import static com.szakdoga.game.screens.GameScreen.player;
import static com.szakdoga.game.screens.MainMenu.UIscale;

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

    public Hud(final InputMultiplexer multiplexer, SpriteBatch batch, final InputHandler inputHandler) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Kanit-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (75*UIscale);
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

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
        ImageButton towerHudElement = new ImageButton(new TextureRegionDrawable(new Texture("textures/tower.png")));
        /**
         * add different eventListeners here for all the clickable elements of the hud
         */
        //TODO how to create a tower buyyer thingi maggie
        towerHudElement.addListener(new ClickListener(){ //TODO esetleg refractor
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inputHandler.draggingArcher();
                player.create(new ArcherTower(0,0,"Archer"));
            }
        });
        tableTop.top();
        tableTop.add(towerHudElement).height(Gdx.graphics.getHeight()/6f);


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
