package com.szakdoga.game.pathFinder;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.szakdoga.game.units.Unit;

public class PathFinder {
    TiledMapTileLayer tiledMapTileLayer ;
    public PathFinder(TiledMapTileLayer tiledMapTileLayer){
        this.tiledMapTileLayer=tiledMapTileLayer;
    }

    public void checkNextStep(Unit unit){
        if(tiledMapTileLayer.getCell(unit.getPreviousX(),unit.getPreviousY()+1).getTile().getProperties().containsKey("road")){
            unit.setX(unit.getPreviousX());
            unit.setY(unit.getPreviousY()+1);
            unit.setNextX(unit.getPreviousX());
            unit.setNextY(unit.getPreviousY()+1);
        }
        if(tiledMapTileLayer.getCell(unit.getPreviousX(),unit.getPreviousY()-1).getTile().getProperties().containsKey("road")){
            unit.setX(unit.getPreviousX());
            unit.setY(unit.getPreviousY()-1);
            unit.setNextX(unit.getPreviousX());
            unit.setNextY(unit.getPreviousY()-1);
        }
        if(tiledMapTileLayer.getCell(unit.getPreviousX()+1,unit.getPreviousY()+1).getTile().getProperties().containsKey("road")){
            unit.setX(unit.getPreviousX()+1);
            unit.setY(unit.getPreviousY()+1);
            unit.setNextX(unit.getPreviousX()+1);
            unit.setNextY(unit.getPreviousY()+1);
        }
        if(tiledMapTileLayer.getCell(unit.getPreviousX()+1,unit.getPreviousY()-1).getTile().getProperties().containsKey("road")){
            unit.setX(unit.getPreviousX()+1);
            unit.setY(unit.getPreviousY()-1);
            unit.setNextX(unit.getPreviousX()+1);
            unit.setNextY(unit.getPreviousY()-1);
        }
        if(tiledMapTileLayer.getCell(unit.getPreviousX()+1,unit.getPreviousY()).getTile().getProperties().containsKey("road")){
            unit.setX(unit.getPreviousX()+1);
            unit.setY(unit.getPreviousY());
            unit.setNextX(unit.getPreviousX()+1);
            unit.setNextY(unit.getPreviousY());
        }
        if(tiledMapTileLayer.getCell(unit.getPreviousX()-1,unit.getPreviousY()+1).getTile().getProperties().containsKey("road")){
            unit.setX(unit.getPreviousX()-1);
            unit.setY(unit.getPreviousY()+1);
            unit.setNextX(unit.getPreviousX()-1);
            unit.setNextY(unit.getPreviousY()+1);
        }
        if(tiledMapTileLayer.getCell(unit.getPreviousX()-1,unit.getPreviousY()-1).getTile().getProperties().containsKey("road")){
            unit.setX(unit.getPreviousX()-1);
            unit.setY(unit.getPreviousY()-1);
            unit.setNextX(unit.getPreviousX()-1);
            unit.setNextY(unit.getPreviousY()-1);
        }
        if(tiledMapTileLayer.getCell(unit.getPreviousX()-1,unit.getPreviousY()).getTile().getProperties().containsKey("road")){
            unit.setX(unit.getPreviousX()-1);
            unit.setY(unit.getPreviousY());
            unit.setNextX(unit.getPreviousX()-1);
            unit.setNextY(unit.getPreviousY());
        }

    }
}
