package com.szakdoga.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Unit extends Sprite{
    protected float speed;
    protected float health;
    protected float damage;
    protected int price;

    public Unit(float speed,float health,float damage,int price){
        super(new Sprite(new Texture("assets/placeholder.jpg")));// TODO fix assets
        this.speed=speed;
        this.health=health;
        this.damage=damage;
        this.price=price;
    }
}
