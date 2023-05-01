package com.szakdoga.game.entities.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.szakdoga.game.DisplayConfig;
import com.szakdoga.game.entities.EntitiesConfig;
import org.datatransferobject.UnitDTO;

import java.util.ArrayList;

import static com.szakdoga.game.network.DTO.Preparator.deepcopy;

public class WizardUnit extends Unit{

    public WizardUnit(float X,float Y) {
        super(1,50,20,40,X,Y, EntitiesConfig.WIZARD_UNIT);
        textureURL = DisplayConfig.WIZARD_UNIT_TEXTURE;
        sprite.set(new Sprite(new Texture(textureURL)));
        sprite.setSize(1,1);
        sprite.setX(X);
        sprite.setY(Y);
    }
    public WizardUnit(UnitDTO unitDTO){
        super(unitDTO.getSpeed(),unitDTO.getHealth(),unitDTO.getDamage(), unitDTO.getPrice(), unitDTO.getX(), unitDTO.getY(),unitDTO.getUnitClass());
        textureURL = DisplayConfig.WIZARD_UNIT_TEXTURE;
        sprite.setSize(1,1);
        sprite.setX(unitDTO.getX());
        sprite.setY(unitDTO.getY());
        this.nextXCoordinates=deepcopy((ArrayList<Integer>) unitDTO.getNextX());
        this.nextYCoordinates=deepcopy((ArrayList<Integer>) unitDTO.getNextY());
        this.deltaX=unitDTO.getDeltaX();
        this.deltaY=unitDTO.getDeltaY();
        this.id=unitDTO.getId();
        this.lastStep=unitDTO.getLastStep();
        this.distance=unitDTO.getDistance();
        this.PreviousX=unitDTO.getPreviousX();
        this.PreviousY=unitDTO.getPreviousY();
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
