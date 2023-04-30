package com.szakdoga.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.szakdoga.game.FontCreator;
import com.szakdoga.game.Logger;
import com.szakdoga.game.TowerDefence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.szakdoga.game.TowerDefence.UIscale;
import static com.szakdoga.game.TowerDefence.font;


public class OptionScreen extends ScreenAdapter {
    SpriteBatch spriteBatch = new SpriteBatch();
    TextField UIscaleField;
    protected TextButton.TextButtonStyle style;
    TextButton.TextButtonStyle styleHover;
    protected Stage stage;
    protected Table table;
    protected List<Map.Entry<Integer,Integer>> res= new ArrayList<>();
    protected int resCursor=0;
    protected boolean fullScreen=Gdx.graphics.isFullscreen();
    protected TowerDefence game;
    public OptionScreen(TowerDefence game)  {

        this.game=game;
        styleHover = new TextButton.TextButtonStyle();
        styleHover.font = game.fontHover;
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font=font;
        textFieldStyle.fontColor=Color.WHITE;


        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setDebug(true);
        table.setFillParent(true);
        style = new TextButton.TextButtonStyle();
        style.font = font;
        style.font.setColor(Color.BLUE);
        table.row().minHeight((float) (game.screenHeight*0.25*UIscale)).maxWidth(Gdx.graphics.getWidth());//gets inherited


        TextButton UIScale = new TextButton("UI Scale write a number \n here 0-3 can be float", style);
        TextButton fullscreen = new TextButton("Fullscreen: OFF",style);
        TextButton exit = new TextButton("Back to menu", style);
        UIScale.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new MainMenu(game));
            }
        });
        fullscreen.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                if(!fullScreen) {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    fullscreen.setText("Fullscreen: ON"); //TODO kiemelni tit
                    fullScreen=true;
                    Logger.writeLogDisplayLog("log","Diplayed switched to fullscreen",this.getClass().getName());
                }
                else{
                    Gdx.graphics.setWindowedMode(1000,540);//TODO kiemelni display conf
                    fullscreen.setText("Fullscreen: OFF"); //TODO kiemelni display conf
                    fullScreen=false;
                    Logger.writeLogDisplayLog("log","Diplayed switched to windowed",this.getClass().getName());
                }
                game.setScreen(new OptionScreen(game));
            }
        });

        UIscaleField = new TextField("", textFieldStyle);
        UIscaleField.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        UIscaleField.setMessageText("Input"); //TODO kiemelni display conf

        table.row().minHeight((float) (game.screenHeight*0.15*UIscale)); //TODO kiemelni display conf
        table.add(UIScale).fill();
        table.row().minHeight((float) (game.screenHeight*0.15*UIscale));
        table.add(UIscaleField).fill();
        table.row().minHeight((float) (game.screenHeight*0.15*UIscale));
        table.add(fullscreen).fill();
        table.row().minHeight((float) (game.screenHeight*0.15*UIscale));
        table.add(exit).fill();


        stage.addActor(table);
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);
        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();
        if(((TextButton)((Table)stage.getActors().get(0)).getCells().get(2).getActor()).getClickListener().isOver()){
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(0).getActor()).setStyle((style));
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(2).getActor()).setStyle((styleHover));
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(3).getActor()).setStyle((style));
        }

        try{
            String tmpScale=UIscaleField.getText();
            if(!tmpScale.isEmpty()) {
                UIscale = Float.parseFloat(tmpScale);
                if (UIscale > 0 && UIscale < 3) {
                    Logger.writeLogDisplayLog("log", "UIscale set to:" + UIscale,this.getClass().getName());
                    //Readjust fonts for the new scale
                    font = FontCreator.createFont();
                    game.fontHover = FontCreator.createFont(150, Color.ORANGE, "fonts/Kanit-Black.ttf");
                    game.setScreen(new OptionScreen(game));
                }
            }
        }catch (NumberFormatException e){
            Logger.writeLogDisplayLog("error","UIscale given wrong input",this.getClass().getName());
        }

    }
    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width,height);
    }
    @Override
    public void dispose(){
        spriteBatch.dispose();
        stage.dispose();
    }
}
