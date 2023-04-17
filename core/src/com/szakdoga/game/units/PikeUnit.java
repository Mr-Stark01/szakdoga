package com.szakdoga.game.units;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.datatransferobject.UnitDTO;

import java.util.ArrayList;

import static com.szakdoga.game.network.DTO.Preparator.deepcopy;

public class PikeUnit extends Unit{
    public PikeUnit(float X,float Y) {
    super(1,100,5,20,X,Y,"Pike");//TODO how
    sprite.set(new Sprite(new Texture("placeholder.jpg")));
    sprite.setSize(1,1);
    sprite.setX(X);
    sprite.setY(Y);
    }
    public PikeUnit(UnitDTO unitDTO){//TODO teljesen átmásolni
        super(10,100,5,20,unitDTO.getX(), unitDTO.getY(),"Pike");
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
}
