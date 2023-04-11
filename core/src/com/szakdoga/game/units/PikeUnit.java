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
    set(new Sprite(new Texture("placeholder.jpg")));
    setSize(1,1);
    setX(X);
    setY(Y);
    }
    public PikeUnit(UnitDTO unitDTO){//TODO teljesen átmásolni
        super(1,100,5,20,unitDTO.getX(), unitDTO.getY(),"Pike");
        setSize(1,1);
        setX(unitDTO.getX());
        setY(unitDTO.getY());
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
    public void addSkin(){
        float x=getX(),y=getY();
        set(new Sprite(new Texture("placeholder.jpg")));
        setSize(1,1);
        setX(x);
        setY(y);
    }
    @Override
    public void render(SpriteBatch batch) {
        // System.out.println(getX()+"\t"+getY());
        addSkin();
        if(id!=0) {
            step();
            super.draw(batch);
        }
    }
}
