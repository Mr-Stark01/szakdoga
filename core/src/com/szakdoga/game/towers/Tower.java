package com.szakdoga.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.units.Unit;

public abstract class Tower extends Sprite {
    protected float health;
    protected float damage;
    protected int price;
    protected int range;
    protected Unit target;
    public Tower(
            int damage, int price, int health, int range, float spawnX, float spawnY) {
        super(new Texture("placeholder.jpg"));
        this.damage = damage;
        this.price = price;
        this.health = health;
        this.range = range;
        spawnX=(float)Math.floor(spawnX);
        spawnY=(float)Math.floor(spawnY);
        setX(spawnX);
        setY(spawnY);
        setSize(2,2);
    }

    public int getPrice(){
        return price;
    }
    public void render(SpriteBatch batch){
        super.draw(batch);
    }
}
