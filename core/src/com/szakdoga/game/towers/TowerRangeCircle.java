package com.szakdoga.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TowerRangeCircle extends Sprite {
    private int range;
    public TowerRangeCircle(int range) {
        super(new Sprite(new Texture("textures/circle.png")));//todo kiemelni display log
        this.range=range;
        setX(10);//todo kiemelni display log startingpos
        setY(10);
        setSize(range*2, range*2);
    }
    public void draw(SpriteBatch spriteBatch){
        super.draw(spriteBatch);
    }

    public void updatePos(float x, float y) {
        setX(x-range);
        setY(y-range);
    }
}
