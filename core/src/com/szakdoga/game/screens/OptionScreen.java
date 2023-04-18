package com.szakdoga.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.szakdoga.game.TowerDefence;

import static com.szakdoga.game.screens.MainMenu.UIscale;


public class OptionScreen extends ScreenAdapter {
    SpriteBatch spriteBatch = new SpriteBatch();
    TextField UIscaleField;
    protected TextButton.TextButtonStyle style;
    protected Stage stage;
    protected Table table;
    public OptionScreen(TowerDefence game)  {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Kanit-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (75*UIscale);
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
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
        table.row().minHeight((float) (game.screenHeight*0.25*UIscale)).minWidth(game.screenWidth);//gets inherited


        TextButton UIScale = new TextButton("UI Scale write a number here 1-10 can be float", style);
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


        UIscaleField = new TextField("", textFieldStyle);
        UIscaleField.setSize(300, 150);
        UIscaleField.setMessageText("Input");

        table.row().minHeight((float) (game.screenHeight*0.15*UIscale));
        table.add(UIScale).fill();
        table.row().minHeight((float) (game.screenHeight*0.15*UIscale));
        table.add(UIscaleField).fill();
        table.row().minHeight((float) (game.screenHeight*0.15*UIscale));
        table.add(exit).fill();


        stage.addActor(table);
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(1, 0, 0, 1);
        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();
        try{
        UIscale=Float.parseFloat(UIscaleField.getText());
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

    }
    @Override
    public void dispose(){
        stage.dispose();
    }
}
