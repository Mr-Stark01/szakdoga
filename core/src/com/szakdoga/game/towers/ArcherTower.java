package com.szakdoga.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ArcherTower extends Tower{
    public ArcherTower( float spawnX, float spawnY) {
        super(10, 10, 4,1f, spawnX, spawnY);
        set(new Sprite(new Texture("textures/tower.png")));
        setSize(1,1);
    }
}
