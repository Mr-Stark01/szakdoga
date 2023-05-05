package com.szakdoga.game.screens;

import static com.szakdoga.game.TowerDefence.UIscale;
import static com.szakdoga.game.TowerDefence.font;

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
import com.szakdoga.game.config.DisplayConfig;
import com.szakdoga.game.Logger;
import com.szakdoga.game.TowerDefence;
import java.util.regex.Pattern;

public class InputScreen extends ScreenAdapter {
    private static final String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
    private static final int LOAD_DELAY_LIMIT=4;
    protected Stage stage;
    protected Table table;
    protected TextButton startButton;
    protected TextButton backButton;
    Label.LabelStyle labelStyle= new Label.LabelStyle(font, Color.RED);
    Label message = new Label("",labelStyle);
    private TowerDefence game;
    private TextField ip;
    private TextField port;
    private TextField name;
    private boolean startNewScreen=false;
    private int loadDelayCounter =0;
    public InputScreen(TowerDefence game){
        this.game=game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setDebug(true);
        table.setFillParent(true);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font=font;
        textFieldStyle.fontColor= Color.WHITE;
        TextButton.TextButtonStyle textButtonStyle =new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.font.setColor(DisplayConfig.BLUE_COLOR);

        startButton = new TextButton(DisplayConfig.START_TEXT, textButtonStyle);
        backButton = new TextButton(DisplayConfig.BACK_TEXT, textButtonStyle);
        ip = new TextField("0.0.0.0",textFieldStyle);
        port = new TextField("56227",textFieldStyle);
        name = new TextField("Player",textFieldStyle);

        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(correctIPCheck(ip.getText(),port.getText())) {
                    startButton.setText("LOADING");
                    Logger.writeLog("log", "startButton clicked w/ip:" + ip.getText() +
                            "and name:" +name.getText(),this.getClass().getSimpleName());
                    startNewScreen=true;
                }
                else{
                    message.setText("The ip input was wrong try again.");
                    Logger.writeLogDisplayLog("error","Wrongly formated ip was given:"+
                            ip.getText(),this.getClass().getSimpleName());
                }
            }
        });
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            game.setScreen(new MainMenu(game));
            dispose();
            }

        });
        table.add(startButton);
        table.row().minHeight((float) (game.screenHeight*DisplayConfig.HUD_TABLE_ADJUSTMENT *UIscale));
        table.add(ip).width(Gdx.graphics.getWidth());
        table.row().minHeight((float) (game.screenHeight*DisplayConfig.HUD_TABLE_ADJUSTMENT *UIscale));
        table.add(port).width(Gdx.graphics.getWidth());
        table.row().minHeight((float) (game.screenHeight*DisplayConfig.HUD_TABLE_ADJUSTMENT *UIscale));
        table.add(name).width(Gdx.graphics.getWidth());
        table.row().minHeight((float) (game.screenHeight*DisplayConfig.HUD_TABLE_ADJUSTMENT *UIscale));
        table.add(backButton).width(Gdx.graphics.getWidth());
        stage.addActor(table);
    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);
        stage.draw();
        setGameScreen();
    }
    @Override
    public void dispose(){
        hide();
        stage.dispose();
        Logger.writeLogDisplayLog("log","Main Menu disposed",this.getClass().getSimpleName());
    }

    public void setGameScreen(){
        if(startNewScreen) {
            loadDelayCounter++;
            if(loadDelayCounter>4) {
                game.setScreen(new GameScreen(game, ip.getText(), Integer.valueOf(port.getText()), name.getText()));
                dispose();
            }
        }
    }
    public boolean correctIPCheck(String text,String port) {
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
        if(!port.matches("-?\\d+")){
            return false;
        }
        return true;
    }
}
