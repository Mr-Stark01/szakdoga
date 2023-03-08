package com.szakdoga.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.towers.ArcherTower;
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
            tower.render(batch);
        }
        for(Unit unit:units){
            //unit.render(batch);
        }
    }

}
