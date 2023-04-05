package com.szakdoga.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PlaceHolderUnit extends Unit{
    public PlaceHolderUnit(float X,float Y,String unitType) {
        super(0,0,0,0,X,Y,unitType);
        set(new Sprite(new Texture("placeholder.jpg")));
        setSize(1,1);
        setX(X);
        setY(Y);
    }
}
