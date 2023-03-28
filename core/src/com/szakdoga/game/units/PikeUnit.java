package com.szakdoga.game.units;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PikeUnit extends Unit{
    public PikeUnit(float X,float Y) {
    super(10,100,5,20,X,Y,"Pike");
    set(new Sprite(new Texture("placeholder.jpg")));
    setSize(1,1);
    setX(X);
    setY(Y);
    System.out.println(getPreviousX());
    }
}
