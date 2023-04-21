package com.szakdoga.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.szakdoga.game.Logger;
import com.szakdoga.game.TowerDefence;

import java.util.regex.Pattern;

import static com.szakdoga.game.screens.MainMenu.UIscale;

public class InputScreen extends ScreenAdapter {
    private TowerDefence game;
    protected Stage stage;
    protected Table table;
    protected TextButton.TextButtonStyle style;
    private TextField ip;
    private TextField name;
    Label.LabelStyle labelStyle= new Label.LabelStyle(game.font, Color.RED);
    Label message = new Label("",labelStyle);
    public InputScreen(TowerDefence game){
        this.game=game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setDebug(true);
        table.setFillParent(true);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font=game.font;
        textFieldStyle.fontColor= Color.WHITE;
        TextButton.TextButtonStyle textButtonStyle =new TextButton.TextButtonStyle();
        textButtonStyle.font = game.font;
        textButtonStyle.font.setColor(Color.BLUE);

        TextButton startButton = new TextButton("Start", textButtonStyle);
        ip = new TextField("0.0.0.0",textFieldStyle);
        name = new TextField("Player",textFieldStyle);

        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(correctIPCheck(ip.getText())) {
                    Logger.writeLog("log", "startButton clicked w/ip:" + ip.getText() +
                            "and name:" +name.getText(),this.getClass().getSimpleName());
                    dispose();
                    game.setScreen(new GameScreen(game,ip.getText(),name.getText()));
                }
                else{
                    message.setText("The ip input was wrong try again.");
                    Logger.writeLogDisplayLog("error","Wrongly formated ip was given:"+
                            ip.getText(),this.getClass().getSimpleName());
                }
            }
        });
        table.add(startButton);
        table.row().minHeight((float) (game.screenHeight*0.15*UIscale));
        table.add(ip).width(Gdx.graphics.getWidth());
        table.row().minHeight((float) (game.screenHeight*0.15*UIscale));
        table.add(name).width(Gdx.graphics.getWidth());
        stage.addActor(table);
    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);
        stage.draw();

    }
    @Override
    public void dispose(){
        hide();
        stage.dispose();
        Logger.writeLogDisplayLog("log","Main Menu disposed",this.getClass().getSimpleName());
    }
    public boolean correctIPCheck(String text) {
        String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
        Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);
        if (text == null) {
            return false;
        }
        if (!IPv4_PATTERN.matcher(text).matches()) {
            return false;
        }
        String[] parts = text.split("\\.");
        // verify that each of the four subgroups of IPv4 addresses is legal
        try {
            for (String segment : parts) {
                // x.0.x.x is accepted but x.01.x.x is not
                if (Integer.parseInt(segment) > 255 || (segment.length() > 1 && segment.startsWith("0"))) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
