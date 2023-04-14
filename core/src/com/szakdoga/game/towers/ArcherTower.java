package com.szakdoga.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.datatransferobject.TowerDTO;

import static com.szakdoga.game.screens.GameScreen.player;

public class ArcherTower extends Tower{
    private boolean hasTexture=false;
    public ArcherTower( float spawnX, float spawnY,String towerClass) {
        super(10, 10, 4,1f, spawnX, spawnY,towerClass);
        sprite.set(new Sprite(new Texture("textures/tower.png")));
        sprite.setSize(1,1);
    }
    public ArcherTower(TowerDTO towerDTO){
        super(10, 10, 4,1f, towerDTO.getX(), towerDTO.getY(),towerDTO.getTowerClass());
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
    public void render(SpriteBatch batch){
        if(id>0) {
            if(!hasTexture){
                sprite.set(new Sprite(new Texture("textures/tower.png")));
                sprite.setX((float)X);
                sprite.setY((float)Y);
                sprite.setSize(1,1);
                hasTexture=true;
            }
            sprite.draw(batch);
        }
    }
}
