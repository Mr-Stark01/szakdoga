package com.szakdoga.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.datatransferobject.TowerDTO;

import static com.szakdoga.game.screens.GameScreen.player;

public class ArcherTower extends Tower{

    public ArcherTower( float spawnX, float spawnY,String towerClass) {
        super(10, 10, 4,1f, spawnX, spawnY,towerClass);
        textureURL="textures/archertower.png";
        sprite.set(new Sprite(new Texture(textureURL)));
        sprite.setSize(1,1);
    }
    public ArcherTower(TowerDTO towerDTO){
        super(towerDTO.getDamage(), towerDTO.getPrice(), towerDTO.getRange(),towerDTO.getAttackTime(), towerDTO.getX(), towerDTO.getY(),towerDTO.getTowerClass());
        textureURL="textures/archertower.png";
        sprite.setSize(1,1);
        this.X=towerDTO.getX();
        this.Y=towerDTO.getY();
        setX(towerDTO.getX());
        setY(towerDTO.getY());
        this.towerClass=towerDTO.getTowerClass();
        this.id=towerDTO.getId();
        this.target=towerDTO.getTarget()==null?null:player.getUnitWithId(towerDTO.getTarget().getId());
        this.deltaSum=towerDTO.getDeltaSum();
        this.attackTime=towerDTO.getAttackTime();
        this.damage=towerDTO.getDamage();
        this.price=towerDTO.getPrice();
        this.range=towerDTO.getRange();

    }
    @Override
    public void addTexture(){
        if(!hasTexture){
            float X=getX(),Y=getY();
            sprite.set(new Sprite(new Texture(textureURL)));
            sprite.setX(X);
            sprite.setY(Y);
            sprite.setSize(1,1);
            hasTexture=true;
        }
    }
}
