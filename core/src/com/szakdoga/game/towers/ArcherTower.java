package com.szakdoga.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ArcherTower extends Tower{
    public ArcherTower( float spawnX, float spawnY) {
        super(10, 10, 10, 4, spawnX, spawnY);
        setTexture(new Texture("textures/tower.png"));
    }
}
