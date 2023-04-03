package com.szakdoga.game.pathFinder;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.szakdoga.game.units.Unit;
import org.datatransferobject.UnitDTO;

public class PathFinder {
    TiledMapTileLayer tiledMapTileLayer ;
    public PathFinder(TiledMapTileLayer tiledMapTileLayer){
        this.tiledMapTileLayer=tiledMapTileLayer;
    }
    public static void calculateAngle(Unit unit) {
        float angle = MathUtils.atan2( unit.getNextY().get(0) - unit.getY(), unit.getNextX().get(0) - unit.getX());
        unit.setDeltaX(MathUtils.cos(angle));
        unit.setDeltaY(MathUtils.sin(angle));
    }

    public void calculateNextStep(Unit unit, int X, int Y, int previousX, int previousY){
        if (tiledMapTileLayer.getCell(X, Y + 1).getTile().getProperties().containsKey("road") &&
                !(previousX == X && Y + 1 == previousY)) {
            unit.getNextX().add(X);
            unit.getNextY().add(Y + 1);
            setPreviousCoordinates(X,Y,unit);
            return;
        }
        if (tiledMapTileLayer.getCell(X, Y - 1).getTile().getProperties().containsKey("road") &&
                !(previousX == X && Y - 1 == previousY)) {
            unit.getNextX().add(X);
            unit.getNextY().add(Y - 1);
            setPreviousCoordinates(X,Y,unit);
            return;
        }
        if (tiledMapTileLayer.getCell(X - 1, Y).getTile().getProperties().containsKey("road") &&
                !(previousX == X - 1 && Y == previousY)) {
            unit.getNextX().add(X - 1);
            unit.getNextY().add(Y);
            setPreviousCoordinates(X,Y,unit);
            return;
        }
        if (tiledMapTileLayer.getCell(X + 1, Y).getTile().getProperties().containsKey("road") &&
                !(previousX == X + 1 && Y == previousY)) {
            unit.getNextX().add(X + 1);
            unit.getNextY().add(Y);
            setPreviousCoordinates(X,Y,unit);
            return;
        }
        if (tiledMapTileLayer.getCell(X + 1, Y + 1).getTile().getProperties().containsKey("road") &&
                !(previousX == X + 1 && Y + 1 == previousY)) {
            unit.getNextX().add(X + 1);
            unit.getNextY().add(Y + 1);
            setPreviousCoordinates(X,Y,unit);
            return;
        }
        if (tiledMapTileLayer.getCell(X + 1, Y - 1).getTile().getProperties().containsKey("road") &&
                !(previousX == X + 1 && Y - 1 == previousY)) {
            unit.getNextX().add(X + 1);
            unit.getNextY().add(Y - 1);
            setPreviousCoordinates(X,Y,unit);
            return;
        }

        if (tiledMapTileLayer.getCell(X - 1, Y + 1).getTile().getProperties().containsKey("road") &&
                !(previousX == X - 1 && Y + 1 == previousY)) {
            unit.getNextX().add(X - 1);
            unit.getNextY().add(Y + 1);
            setPreviousCoordinates(X,Y,unit);
            return;
        }
        if (tiledMapTileLayer.getCell(X - 1, Y - 1).getTile().getProperties().containsKey("road") &&
                !(previousX == X - 1 && Y - 1 == previousY)) {
            unit.getNextX().add(X - 1);
            unit.getNextY().add(Y - 1);
            setPreviousCoordinates(X,Y,unit);
        }
    }
    private void setPreviousCoordinates(int X,int Y,Unit unit){
        unit.setPreviousX(X);
        unit.setPreviousY(Y);
    }
}
