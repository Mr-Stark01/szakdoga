package com.szakdoga.game.pathFinder;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.szakdoga.game.units.Unit;

public class PathFinder {
    TiledMapTileLayer tiledMapTileLayer ;
    public PathFinder(TiledMapTileLayer tiledMapTileLayer){
        this.tiledMapTileLayer=tiledMapTileLayer;
    }

    public void checkNextStep(Unit unit){
        int X = Math.round(unit.getX());
        int Y = Math.round(unit.getY());
        unit.setX(X);
        unit.setY(Y);


        if(tiledMapTileLayer.getCell(X,Y+1).getTile().getProperties().containsKey("road") &&
                !(unit.getPreviousX() == X && Y + 1 == unit.getPreviousY())){
            unit.setNextX(X);
            unit.setNextY(Y+1);
            unit.setPreviousX(X);
            unit.setPreviousY(Y);
            return;//TODO végig irni
        }
        if(tiledMapTileLayer.getCell(X,Y - 1).getTile().getProperties().containsKey("road") &&
                !(unit.getPreviousX() == X && Y - 1 == unit.getPreviousY())){
            unit.setNextX(X);
            unit.setNextY(Y-1);
            unit.setPreviousX(X);
            unit.setPreviousY(Y);
            return;
        }
        if(tiledMapTileLayer.getCell(X + 1,Y + 1).getTile().getProperties().containsKey("road") &&
                !(unit.getPreviousX() == X + 1 && Y + 1 == unit.getPreviousY())){
            unit.setNextX(X+1);
            unit.setNextY(Y+1);
            unit.setPreviousX(X);
            unit.setPreviousY(Y);
            return;
        }
        if(tiledMapTileLayer.getCell(X + 1,Y - 1).getTile().getProperties().containsKey("road") &&
                !(unit.getPreviousX() == X + 1 && Y - 1 == unit.getPreviousY())){
            unit.setNextX(X+1);
            unit.setNextY(Y-1);
            unit.setPreviousX(X);
            unit.setPreviousY(Y);
            return;
        }
        if(tiledMapTileLayer.getCell(X + 1,Y).getTile().getProperties().containsKey("road") &&
                !(unit.getPreviousX() == X + 1 && Y == unit.getPreviousY())){
            unit.setNextX(X+1);
            unit.setNextY(Y);
            unit.setPreviousX(X);
            unit.setPreviousY(Y);
            return;
        }
        if(tiledMapTileLayer.getCell(X - 1,Y + 1).getTile().getProperties().containsKey("road") &&
                !(unit.getPreviousX() == X - 1 && Y + 1 == unit.getPreviousY())){
            unit.setNextX(X-1);
            unit.setNextY(Y+1);
            unit.setPreviousX(X);
            unit.setPreviousY(Y);
            return;
        }
        if(tiledMapTileLayer.getCell(X - 1,Y - 1).getTile().getProperties().containsKey("road") &&
                !(unit.getPreviousX() == X - 1 && Y - 1 == unit.getPreviousY())){
            unit.setNextX(X-1);
            unit.setNextY(Y-1);
            unit.setPreviousX(X);
            unit.setPreviousY(Y);
            return;
        }
        if(tiledMapTileLayer.getCell(X - 1 ,Y).getTile().getProperties().containsKey("road") &&
                !(unit.getPreviousX() == X - 1 && Y == unit.getPreviousY())){
            unit.setNextX(X-1);
            unit.setNextY(Y);
            unit.setPreviousX(X);
            unit.setPreviousY(Y);
            return;
        }


        //System.out.println("next"+unit.getNextX() +"\t" + unit.getNextY());
    }
}
