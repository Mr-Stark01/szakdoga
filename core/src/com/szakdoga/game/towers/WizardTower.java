package com.szakdoga.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.datatransferobject.TowerDTO;

import static com.szakdoga.game.screens.GameScreen.player;

public class WizardTower extends Tower{
    public WizardTower(float spawnX, float spawnY, String towerClass) {
        super(50, 70, 3,3f, spawnX, spawnY,towerClass);
        textureURL="textures/wizardtower.png";
        sprite.set(new Sprite(new Texture(textureURL)));
        sprite.setSize(1,1);
    }
    public WizardTower(TowerDTO towerDTO){
        super(towerDTO.getDamage(), towerDTO.getPrice(), towerDTO.getRange(),towerDTO.getAttackTime(), towerDTO.getX(), towerDTO.getY(),towerDTO.getTowerClass());
        textureURL="textures/wizardtower.png";
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
