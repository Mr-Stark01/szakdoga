package com.szakdoga.game.units;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.datatransferobject.UnitDTO;

public class PikeUnit extends Unit{
    public PikeUnit(float X,float Y) {
    super(10,100,5,20,X,Y,"Pike");//TODO how
    set(new Sprite(new Texture("placeholder.jpg")));
    setSize(1,1);
    setX(X);
    setY(Y);
    }
    public PikeUnit(UnitDTO unitDTO){//TODO teljesen átmásolni
        super(10,100,5,20,unitDTO.getX(), unitDTO.getY(),"Pike");
        set(new Sprite(new Texture("placeholder.jpg")));
        setSize(1,1);
        setX(unitDTO.getX());
        setY(unitDTO.getY());
        this.nextXCoordinates=unitDTO.getNextX();
        this.nextYCoordinates=unitDTO.getNextY();
        this.deltaX=unitDTO.getDeltaX();
        this.deltaY=unitDTO.getDeltaY();
        this.id=unitDTO.getId();
        this.id=unitDTO.getLastStep();
    }
}
