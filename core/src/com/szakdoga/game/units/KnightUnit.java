package com.szakdoga.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.datatransferobject.UnitDTO;

import java.util.ArrayList;

import static com.szakdoga.game.network.DTO.Preparator.deepcopy;

public class KnightUnit extends Unit{
    public KnightUnit(float X,float Y) {
        super(5,300,50,100,X,Y,"Knight");//TODO how
        textureURL="textures/knightunit.png";
        sprite.set(new Sprite(new Texture(textureURL)));
        sprite.setSize(1,1);
        sprite.setX(X);
        sprite.setY(Y);
    }
    public KnightUnit(UnitDTO unitDTO){//TODO teljesen átmásolni
        super(unitDTO.getSpeed(),unitDTO.getHealth(),unitDTO.getDamage(), unitDTO.getPrice(), unitDTO.getX(), unitDTO.getY(),unitDTO.getUnitClass());
        textureURL="textures/knightunit.png";
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
