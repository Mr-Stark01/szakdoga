package com.szakdoga.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Unit extends Sprite{
    protected float speed;
    protected float health;
    protected float damage;
    protected int price;

    public Unit(){
        super(new Sprite(new Texture("assets/placeholder.jpg")));// TODO fix assets
    }
}
