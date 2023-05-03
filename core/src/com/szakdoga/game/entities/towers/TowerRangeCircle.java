package com.szakdoga.game.entities.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.config.DisplayConfig;

public class TowerRangeCircle extends Sprite {
    private int range;
    public TowerRangeCircle(int range) {
        super(new Sprite(new Texture(DisplayConfig.CIRCLE_TEXTURE)));
        this.range=range;
        setX(0);
        setY(0);
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
