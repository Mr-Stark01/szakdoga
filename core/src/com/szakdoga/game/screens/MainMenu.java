package com.szakdoga.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.szakdoga.game.TowerDefence;

public class MainMenu extends ScreenAdapter {
    final TowerDefence game;
    protected OrthographicCamera camera;
    protected ClickListener startButtonListener;
    protected TextButton.TextButtonStyle style;
    protected Stage stage;
    protected Table table;
    int i=0;
    Texture bg;
    public MainMenu(final TowerDefence game){
        this.game=game;
        //Camera Setup
        camera = new OrthographicCamera();
        camera.setToOrtho(false,game.screenWidth, game.screenHeight);
        game.batch.setProjectionMatrix(camera.combined);
        //Setup the backend for the clickable menu options
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        style = new TextButton.TextButtonStyle();
        style.font = game.font;
        style.font.getData().setScale(0.7f,0.7f);
        style.font.setColor(Color.BLUE);
        table.row().minHeight((float) (game.screenHeight*0.25)).minWidth(game.screenWidth);//gets inherited
        table.add(new TextButton("Szakdoga", style));
        style.font.getData().setScale(0.4f,0.4f);
        TextButton startButton = new TextButton("Start", style);
        TextButton options = new TextButton("Options", style);
        TextButton misc = new TextButton("Misc", style);
        TextButton exit = new TextButton("Exit", style);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen( new GameScreen(game) );
            }
        });
        options.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen( new OptionScreen(game) );
            }
        });
        misc.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen( new MiscScreen(game) );
            }
        });
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                Gdx.app.exit();
            }
        });

        table.row().minHeight((float) (game.screenHeight*0.15));
        table.add(startButton).fill();
        table.row().minHeight((float) (game.screenHeight*0.15));
        table.add(options).fill();
        table.row().minHeight((float) (game.screenHeight*0.15));
        table.add(misc).fill();
        table.row().minHeight((float) (game.screenHeight*0.15));
        table.add(exit).fill();
        stage.addActor(table);
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

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
        game.batch.draw(bg,0,0,game.screenWidth,game.screenHeight);
        game.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if(((TextButton)((Table)stage.getActors().get(0)).getCells().get(1).getActor()).getClickListener().inTapSquare()){
            TextButton.TextButtonStyle styleHover = new TextButton.TextButtonStyle();
            styleHover.font = game.fontHover;
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(1).getActor()).setStyle((styleHover));
        }
        else {
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(1).getActor()).setStyle((style));
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(2).getActor()).setStyle((style));
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(3).getActor()).setStyle((style));
            ((TextButton)((Table)stage.getActors().get(0)).getCells().get(4).getActor()).setStyle((style));
        }
    }
    @Override
    public void dispose(){
        hide();
        stage.dispose();
    }
    @Override
    public void hide(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
