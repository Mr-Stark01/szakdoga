package com.szakdoga.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.pathFinder.PathFinder;
import com.szakdoga.game.towers.Tower;
import com.szakdoga.game.units.Unit;
import java.util.ArrayList;

public class Player {
    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<Unit> units= new ArrayList<>();
    private int money=10000;
    private int positionX=10,positionY=10;
    private float health;
    private Tower towerInDraggingState;
    private PathFinder pathFinder;
    public Player(PathFinder pathfinder){
        this.pathFinder=pathfinder;

    }
    public boolean addTower(float x,float y){
        if(money-towerInDraggingState.getPrice()>0){
            money-= towerInDraggingState.getPrice();
            x=(float)Math.floor(x);
            y=(float)Math.floor(y);
            towerInDraggingState.setX(x);
            towerInDraggingState.setY(y);
            towers.add(towerInDraggingState);
            towerInDraggingState=null;
            return true;
        }
        else{
            return false;
        }
    }
    public void create(Tower tower){
        this.towerInDraggingState = tower;
    }
    public void render(SpriteBatch batch){
        for(Tower tower:towers){
            tower.render(batch,units);
        }
        for(Unit unit:units){
            if(Math.sqrt((Math.pow(unit.getX()-unit.getNextX(),2))+(Math.pow(unit.getY()-unit.getNextY(),2))) < 0.1f){ //TODO ha túll gyors vagy lassan fút a játék átugorhat pontot
                pathFinder.checkNextStep(unit);
                unit.calculateAngle();
            }
            else{
                if(Math.sqrt((Math.pow(unit.getX()-unit.getNextX(),2))+(Math.pow(unit.getY()-unit.getNextY(),2))) > 1.51f){
                    unit.setX(unit.getNextX());
                    unit.setY(unit.getNextY());
                }
                else{
                    unit.setX(unit.getX()+(unit.getSpeed() * unit.getDeltaX() * Gdx.graphics.getDeltaTime()));
                    unit.setY(unit.getY()+(unit.getSpeed() * unit.getDeltaY() * Gdx.graphics.getDeltaTime()));
                }
            }
            unit.render(batch);
        }
    }

    public void buyUnit(Unit unit) {
            units.add(unit);
            pathFinder.checkNextStep(unit);
            unit.calculateAngle();
    }
}
