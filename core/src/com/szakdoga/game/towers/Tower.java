package com.szakdoga.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Tower extends Sprite {
    protected float health;
    protected float damage;
    protected int price;

    public Tower(){
        super(new Sprite(new Texture("assets/placeholder.jpg")));//TODO fix assets folders structure should still be up on the repo
    }

}
