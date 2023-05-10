package com.szakdoga.game.ui;

import static com.szakdoga.game.entities.towers.Tower.*;
import static com.szakdoga.game.screens.GameScreen.enemyPlayer;
import static com.szakdoga.game.screens.GameScreen.player;
import static com.szakdoga.game.ui.InfoTable.InfoTableFactory;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.szakdoga.game.config.DisplayConfig;
import com.szakdoga.game.FontCreator;
import com.szakdoga.game.Logger;
import com.szakdoga.game.config.EntitiesConfig;
import com.szakdoga.game.entities.towers.ArcherTower;
import com.szakdoga.game.entities.towers.CrossBowTower;
import com.szakdoga.game.entities.towers.Tower;
import com.szakdoga.game.entities.towers.WizardTower;
import com.szakdoga.game.entities.units.KnightUnit;
import com.szakdoga.game.entities.units.PikeUnit;
import com.szakdoga.game.entities.units.Unit;
import com.szakdoga.game.entities.units.WizardUnit;
import com.szakdoga.game.screens.inputHandlers.InputHandler;
import java.util.Date;

/**
 * Displays HUD and handles HUD interactions
 */
public class Hud implements Disposable {
    private Stage stage;
    private Table tableTop;
    private Table tableBottom;
    private Table tableChat;
    private Table infoTable;
    private TextButton.TextButtonStyle style;
    private Label.LabelStyle labelStyle;
    private TextField.TextFieldStyle textFieldStyle;
    private Label chatTop;
    private Label chatBottom;
    private TextButton sendButton;
    private TextField chatInput;
    private Label moneyLabel;
    private Label healthLabel;
    private Label enemyHealthLabel;
    private Label unitNumberLabel;
    private Viewport viewport;
    private long lastPressedButton = new Date().getTime();

    public Hud(final InputMultiplexer multiplexer, SpriteBatch batch, final InputHandler inputHandler) {

        BitmapFont font = FontCreator.createFont(75);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);

        tableTop = new Table();
        tableTop.setFillParent(true);
        tableTop.setDebug(true);
        tableBottom = new Table();
        tableBottom.setFillParent(true);
        tableBottom.setDebug(true);
        tableChat = new Table();
        tableChat.setFillParent(true);
        tableChat.setDebug(true);


        style = new TextButton.TextButtonStyle();
        textFieldStyle=new TextField.TextFieldStyle();
        textFieldStyle.font=font;
        textFieldStyle.fontColor=Color.WHITE;

        labelStyle = new Label.LabelStyle();
        labelStyle.font=font;
        style.font = font;
        style.font.setColor(Color.BLUE);
        ImageButton archerTowerHudElement = new ImageButton(new TextureRegionDrawable(new Texture(DisplayConfig.ARCHER_TOWER_TEXTURE)));
        ImageButton crossBowTowerHudElement = new ImageButton(new TextureRegionDrawable(new Texture(DisplayConfig.CROSS_BOW_TOWER_TEXTURE)));
        ImageButton wizzardTowerHudElement = new ImageButton(new TextureRegionDrawable(new Texture(DisplayConfig.WIZARD_TOWER_TEXTURE)));
        /**
         * add different eventListeners here for all the clickable elements of the hud
         */
        tableTop.top();
        archerTowerHudElement.addListener(getClickListener(archerTowerHudElement,new ArcherTower(-10000,-1000, EntitiesConfig.ARCHER_TOWER)
                ,inputHandler,DisplayConfig.ARCHER_TOWER_TEXTURE));
        tableTop.add(archerTowerHudElement).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        crossBowTowerHudElement.addListener(getClickListener(crossBowTowerHudElement,new CrossBowTower(-10000,-1000,EntitiesConfig.CROSSBOW_TOWER)
                ,inputHandler,DisplayConfig.CROSS_BOW_TOWER_TEXTURE));
        tableTop.add(crossBowTowerHudElement).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        wizzardTowerHudElement.addListener(getClickListener(wizzardTowerHudElement,new WizardTower(-10000,-1000,EntitiesConfig.WIZARD_TOWER)
                ,inputHandler,DisplayConfig.WIZARD_TOWER_TEXTURE));
        tableTop.add(wizzardTowerHudElement).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);

        ImageButton knightUnitHudElement = new ImageButton(new TextureRegionDrawable(new Texture(DisplayConfig.KNIGHT_UNIT_TEXTURE)));
        ImageButton wizardUnitHudElement = new ImageButton(new TextureRegionDrawable(new Texture(DisplayConfig.WIZARD_UNIT_TEXTURE)));
        ImageButton pikeUnitHudElement = new ImageButton(new TextureRegionDrawable(new Texture(DisplayConfig.PIKE_UNIT_TEXTURE)));

        knightUnitHudElement.addListener(getClickListener(knightUnitHudElement,new KnightUnit(10,10)));
        tableTop.add(knightUnitHudElement).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        wizardUnitHudElement.addListener(getClickListener(wizardUnitHudElement,new WizardUnit(10,10)));
        tableTop.add(wizardUnitHudElement).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        pikeUnitHudElement.addListener(getClickListener(pikeUnitHudElement,new PikeUnit(10,10)));
        tableTop.add(pikeUnitHudElement).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);



        tableBottom.bottom();

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        moneyLabel=new Label(Integer.toString(0),labelStyle);
        healthLabel=new Label(Integer.toString(0),labelStyle);
        enemyHealthLabel=new Label(Integer.toString(0),labelStyle);
        unitNumberLabel=new Label(Integer.toString(0),labelStyle);
        Image coin = new Image(new TextureRegionDrawable(new Texture(DisplayConfig.COIN_TEXTURE)));
        Image health = new Image(new TextureRegionDrawable(new Texture(DisplayConfig.HEALTH_TEXTURE)));
        Image enemyHealth = new Image(new TextureRegionDrawable(new Texture(DisplayConfig.ENEMY_HEALTH_TEXTURE)));
        Image unit = new Image(new TextureRegionDrawable(new Texture(DisplayConfig.TOWER_TEXTURE)));
        tableBottom.add(coin).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        tableBottom.add(moneyLabel);
        tableBottom.add(health).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        tableBottom.add(healthLabel);
        tableBottom.add(enemyHealth).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        tableBottom.add(enemyHealthLabel);
        tableBottom.add(unit).height(Gdx.graphics.getHeight()/6f).width(Gdx.graphics.getHeight()/6f);
        tableBottom.add(unitNumberLabel);

        tableBottom.add(horizontalGroup).height(Gdx.graphics.getHeight()/6f);


        tableChat.center();
        tableChat.left();


        chatBottom = new Label("you",labelStyle);
        chatTop = new Label("enemy",labelStyle);
        chatInput = new TextField("Input:",textFieldStyle);
        sendButton = new TextButton("Send",style);

        chatInput.addListener(
        new ClickListener() {
        @Override
        public void enter(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
            if(!(Gdx.app.getType() == Application.ApplicationType.Android)){
            stage.setKeyboardFocus(chatInput);
            }
        }
          @Override
          public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
              if(!(Gdx.app.getType() == Application.ApplicationType.Android)){
                 stage.setKeyboardFocus(null);
              }
            }
        });

        sendButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.sendMessage();
            }
        });
        chatInput.setBlinkTime(1);
        chatInput.setMaxLength(20);
        tableChat.add(chatBottom);
        tableChat.row();
        tableChat.add(chatTop);
        tableChat.row();
        tableChat.add(chatInput);
        tableChat.row();
        tableChat.add(sendButton);

        stage.addActor(tableChat);
        stage.addActor(tableTop);
        stage.addActor(tableBottom);
        style.font.getData().setScale(0.4f,0.4f);
        multiplexer.addProcessor(stage);
    }

    public void render(){
        updateMoney();
        updateHealth();
        updateUnitNumber();
        updateChat();
        stage.act();
        if(infoTable!=null){
            stage.addActor(infoTable);
        }
        stage.draw();
    }
    public void updateMoney(){
        moneyLabel.setText(Integer.toString(player.getMoney()));
    }
    public void updateHealth(){
        enemyHealthLabel.setText(Float.toString(enemyPlayer.getHealth()));
        healthLabel.setText(Float.toString(player.getHealth()));
    }
    public void updateUnitNumber(){
        unitNumberLabel.setText(player.getTowers().size());
    }
    public void updateChat(){
        player.setChat(chatInput.getText());
        if(enemyPlayer.getChat()!=null && !enemyPlayer.getChat().equals(chatBottom.getText())){
            chatTop.setText(enemyPlayer.getChat());
        }
        if(player.getChat()!=null && player.getSendMessage()){
            chatBottom.setText(player.getChat());
        }
    }
    public ClickListener getClickListener(ImageButton imageButton,Unit TMP){
        ClickListener clickListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(new Date().getTime()-lastPressedButton>1000) {
                    player.buyUnit(Unit.createUnit(player.getPositionX(), player.getPositionY(), TMP.getUnitClass()));
                    lastPressedButton=new Date().getTime();
                    if(infoTable!=null) {
                        infoTable.setPosition(-10000, -10000);
                    }
                    Logger.writeLogDisplayLog("log","player bought "+TMP.getUnitClass()+" unit",this.getClass().getSimpleName());
                }
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor){
                if(pointer!=0) {
                    infoTable = InfoTableFactory(TMP.getHealth(), TMP.getSpeed(), TMP.getDamage(), TMP.getPrice(), TMP.getUnitClass());
                    infoTable.setPosition(imageButton.getX(), imageButton.getY() - 50);
                }
            }
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, @Null Actor toActor){
                if(infoTable!=null) {
                    infoTable.remove();
                    infoTable.setPosition(-1000, -1000);
                    infoTable = null;
                }
            }
        };
        return clickListener;
    }
    public ClickListener getClickListener(ImageButton imageButton, Tower TMP,InputHandler inputHandler,String textureHandle){
        return new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(new Date().getTime()-lastPressedButton>1000) {
                    inputHandler.draggingArcher(textureHandle, TMP.getRange());
                    player.create(createTower(0, 0, TMP.getTowerClass()));
                    lastPressedButton = new Date().getTime();
                    Logger.writeLogDisplayLog("log", "player bought " + TMP.getTowerClass() + " tower", this.getClass().getSimpleName());
                    if(infoTable!=null) {
                        infoTable.setPosition(-1000, -1000);
                    }
                }
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor){
                if(pointer!=0) {
                    infoTable = InfoTableFactory(TMP.getDamage(), TMP.getPrice(), TMP.getRange(), TMP.getAttackTime(), TMP.getTowerClass());
                    infoTable.setPosition(imageButton.getX(), imageButton.getY() - 50);
                }
            }
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, @Null Actor toActor){
                if(infoTable!=null) {
                    infoTable.remove();
                    infoTable.setPosition(-1000, -1000);
                    infoTable = null;
                }
            }
        };
    }
    public void resize (int width, int height) {
        stage.getViewport().update(width,height);
    }
    public void dispose() {

    }
}
