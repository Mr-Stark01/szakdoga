package com.szakdoga.game.entities.units;

import static com.szakdoga.game.network.DTO.Preparator.deepcopy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.szakdoga.game.DisplayConfig;
import com.szakdoga.game.entities.EntitiesConfig;
import java.util.ArrayList;
import org.datatransferobject.UnitDTO;

public class PikeUnit extends Unit{
    public PikeUnit(float X,float Y) {
    super(2,100,5,25,X,Y, EntitiesConfig.PIKE_UNIT);
    textureURL = DisplayConfig.PIKE_UNIT_TEXTURE;
    sprite.set(new Sprite(new Texture(textureURL)));
    sprite.setSize(1,1);
    sprite.setX(X);
    sprite.setY(Y);

    }
    public PikeUnit(UnitDTO unitDTO){
        super(unitDTO.getSpeed(),unitDTO.getHealth(),unitDTO.getDamage(), unitDTO.getPrice(), unitDTO.getX(), unitDTO.getY(),unitDTO.getUnitClass());
        textureURL = DisplayConfig.PIKE_UNIT_TEXTURE;
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
