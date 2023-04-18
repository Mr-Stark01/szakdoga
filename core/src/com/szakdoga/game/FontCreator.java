package com.szakdoga.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import static com.szakdoga.game.screens.MainMenu.UIscale;

public class FontCreator {
    public static BitmapFont createFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Kanit-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (75*UIscale);
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }
    public static BitmapFont createFont(int size){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Kanit-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (size*UIscale);
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }
    public static BitmapFont createFont(int size, Color color){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Kanit-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (size*UIscale);
        parameter.color=color;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }
    public static BitmapFont createFont(int size, Color color,String fontHandle){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontHandle));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (size*UIscale);
        parameter.color=color;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }
}
