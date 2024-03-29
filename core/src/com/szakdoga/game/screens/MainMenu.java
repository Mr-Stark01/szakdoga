package com.szakdoga.game.screens;

import static com.szakdoga.game.TowerDefence.UIscale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.szakdoga.game.config.DisplayConfig;
import com.szakdoga.game.FontCreator;
import com.szakdoga.game.Logger;
import com.szakdoga.game.TowerDefence;

public class MainMenu extends ScreenAdapter {
    final TowerDefence game;
    private TextButton.TextButtonStyle style;
    private Stage stage;
    private Table table;
    private Texture bg;
    private TextButton.TextButtonStyle styleHover;
    public MainMenu(final TowerDefence game){
        this.game=game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        style = new TextButton.TextButtonStyle();
        style.font = FontCreator.createFont(DisplayConfig.MEDIUM_FONT_SIZE);
        TextButton.TextButtonStyle styleTitle = new TextButton.TextButtonStyle();
        styleTitle.font = FontCreator.createFont(DisplayConfig.BIG_FONT_SIZE, DisplayConfig.CRIMSON_COLOR);
        table.row().minHeight((float) (game.screenHeight*DisplayConfig.MAIN_MENU_TABLE_ADJUSTMENT*UIscale)).minWidth(game.screenWidth);
        table.add(new TextButton(DisplayConfig.GAME_TITLE, styleTitle));
        TextButton startButton = new TextButton(DisplayConfig.START_TEXT, style);
        TextButton options = new TextButton(DisplayConfig.OPTION_TEXT, style);
        TextButton exit = new TextButton(DisplayConfig.EXIT_TEXT, style);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Logger.writeLog("log","startButton clicked",this.getClass().getName());
                dispose();
                game.setScreen( new InputScreen(game) );
            }
        });
        options.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Logger.writeLog("log","optionsButton clicked",this.getClass().getName());
                dispose();
                game.setScreen( new OptionScreen(game) );
            }
        });
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Logger.writeLog("log","exitButton clicked",this.getClass().getName());
                dispose();
                Gdx.app.exit();
                System.exit(-1);
            }
        });

        table.row().minHeight((float) (game.screenHeight*DisplayConfig.HUD_TABLE_ADJUSTMENT*UIscale));
        table.add(startButton).fill();
        table.row().minHeight((float) (game.screenHeight*DisplayConfig.HUD_TABLE_ADJUSTMENT*UIscale));
        table.add(options).fill();
        table.row().minHeight((float) (game.screenHeight*DisplayConfig.HUD_TABLE_ADJUSTMENT*UIscale));
        table.add(exit).fill();
        stage.addActor(table);
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

    }
    @Override
    public void show(){
        bg = new Texture(DisplayConfig.MAIN_MENU_BACKGROUND_TEXTURE);
        styleHover = new TextButton.TextButtonStyle();
        styleHover.font = game.fontHover;
    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(1, 0, 0, 1);
        game.batch.begin();
        game.batch.draw(bg,0,0,game.screenWidth,game.screenHeight);
        game.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        //This is awfull
        if(((TextButton)((Table)stage.getActors().get(0)).getCells().get(1).getActor()).getClickListener().isOver()){
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(1).getActor()).setStyle((styleHover));
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(2).getActor()).setStyle((style));
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(3).getActor()).setStyle((style));
        }
        else if(((TextButton)((Table)stage.getActors().get(0)).getCells().get(2).getActor()).getClickListener().isOver()){
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(1).getActor()).setStyle((style));
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(2).getActor()).setStyle((styleHover));
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(3).getActor()).setStyle((style));
        }
        else if(((TextButton)((Table)stage.getActors().get(0)).getCells().get(3).getActor()).getClickListener().isOver()){
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(1).getActor()).setStyle((style));
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(2).getActor()).setStyle((style));
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(3).getActor()).setStyle((styleHover));
        }
        else {
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(1).getActor()).setStyle((style));
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(2).getActor()).setStyle((style));
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(3).getActor()).setStyle((style));
        }
    }
    @Override
    public void dispose(){
        hide();
        stage.dispose();
        Logger.writeLogDisplayLog("log","Main Menu disposed",this.getClass().getName());
    }
    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width,height);
    }
    @Override
    public void hide(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
