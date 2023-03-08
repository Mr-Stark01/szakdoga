package com.szakdoga.game.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.units.Unit;

public abstract class Tower extends Sprite {

    protected float damage;
    protected int price;
    protected int range;
    protected Unit target;
    public Tower(
            int damage, int price, int range, float spawnX, float spawnY) {
        this.damage = damage;
        this.price = price;
        this.range = range;
        spawnX=(float)Math.floor(spawnX);
        spawnY=(float)Math.floor(spawnY);
        setX(spawnX);
        setY(spawnY);
    }

    public int getPrice(){
        System.out.println("asd");
        return price;
    }
    public void render(SpriteBatch batch){
        super.draw(batch);
    }
}
