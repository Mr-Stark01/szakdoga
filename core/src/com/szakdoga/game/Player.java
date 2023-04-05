package com.szakdoga.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.szakdoga.game.pathFinder.PathFinder;
import com.szakdoga.game.towers.Tower;
import com.szakdoga.game.units.Unit;
import org.datatransferobject.DTO;
import org.datatransferobject.PlayerDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private List<Tower> towers = Collections.synchronizedList(new ArrayList<Tower>());
    private List<Unit> units= Collections.synchronizedList(new ArrayList<Unit>());
    private int money=10000;
    private int positionX=10,positionY=10;
    private float health;
    private Tower towerInDraggingState;
    private PathFinder pathFinder;
    public Player(PathFinder pathfinder){
        this.pathFinder=pathfinder;

    }
    public Player(){


    }
    public synchronized boolean addTower(float x,float y){
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
            tower.render(batch, units);
        }
        for(Unit unit:units){
            unit.render(batch);
        }

    }

    public void exchangeData(DTO dto){
            PlayerDTO playerDTO =dto.getPlayerDTO();
            money = playerDTO.getMoney();
            health = playerDTO.getHealth();
            for(int i = 0; i<dto.getUnitDTOs().size(); i++){ //TODO this is garbage
                CompareReturn compareReturn=units.get(i).compareToDTO(dto.getUnitDTOs().get(i));
                switch (compareReturn){
                    case SameIdSameValue:
                        break;
                    case SameIdDifferentValue:
                        units.get(i).setValuesFromDTO(dto.getUnitDTOs().get(i));
                        break;
                    case DifferentId:
                        if(dto.getUnitDTOs().get(i).getId() == -1){
                            units.remove(i);
                            dto.getUnitDTOs().remove(i);
                        }
                        units.set(i,Unit.createUnitFromDTO(dto.getUnitDTOs().get(i)));
                        break;
                }
            }
    }

    public synchronized void buyUnit(Unit unit) {
            units.add(unit);
    }

    public List<Tower> getTowers() {
        return  towers;
    }

    public List<Unit> getUnits() {
        return  units;
    }

    public int getMoney() {
        return money;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public float getHealth() {
        return health;
    }

}
