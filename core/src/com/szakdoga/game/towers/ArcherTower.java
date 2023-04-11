package com.szakdoga.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.datatransferobject.TowerDTO;

import static com.szakdoga.game.screens.GameScreen.player;

public class ArcherTower extends Tower{
    public ArcherTower( float spawnX, float spawnY,String towerClass) {
        super(10, 10, 4,1f, spawnX, spawnY,towerClass);
        set(new Sprite(new Texture("textures/tower.png")));
        setSize(1,1);
    }
    public ArcherTower(TowerDTO towerDTO){
        super(10, 10, 4,1f, towerDTO.getX(), towerDTO.getY(),towerDTO.getTowerClass());
        setSize(1,1);
        setX(towerDTO.getX());
        setY(towerDTO.getY());
        this.towerClass=towerDTO.getTowerClass();
        this.id=towerDTO.getId();
        this.target=player.getUnitWithId(towerDTO.getTarget().getId());
        this.deltaSum=towerDTO.getDeltaSum();
        this.attackTime=towerDTO.getAttackTime();
        this.damage=towerDTO.getDamage();
        this.price=towerDTO.getPrice();
        this.range=towerDTO.getRange();
    }
}
