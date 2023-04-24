package com.szakdoga.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private int positionX=-1,positionY=-1;
    private float health=10000;
    private Tower towerInDraggingState;

    public Player(){


    }
    public synchronized boolean addTower(float x,float y){
        if(money-towerInDraggingState.getPrice()>0){
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
        for (int i=0;i<towers.size();i++) {
            towers.get(i).render(batch);
        }
        for (int i=0;i<units.size();i++) {
            units.get(i).render(batch);
        }

    }

    public void exchangeData(DTO dto){
        PlayerDTO playerDTO = dto.getPlayerDTO();
        money = playerDTO.getMoney();
        health = playerDTO.getHealth();
        positionX = playerDTO.getPositionX();
        positionY = playerDTO.getPositionY();
        //Create new units only used if it's enemy data
        for(int i = units.size(); dto.getUnitDTOs().size()>units.size(); i++) {
            if(dto.getUnitDTOs().get(i).getId()!=-1) {
                units.add(Unit.createUnitFromDTO(dto.getUnitDTOs().get(i)));
            }
        }
        //Updating data from server to already existing units
        for(int i = 0; i<dto.getUnitDTOs().size(); i++){
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
                    else {
                        units.get(i).setValuesFromDTO(dto.getUnitDTOs().get(i));
                    }
                    break;
            }
        }
        //Creating enemy towers
        for(int i = towers.size(); dto.getTowerDTOs().size()>towers.size() ; i++) {
            if(dto.getTowerDTOs().get(i).getId()!=-1) {
                towers.add(Tower.createTowerFromDTO(dto.getTowerDTOs().get(i)));
            }
        }
        //In its current form essentially unused towers can't really change
        for(int i = 0; i<dto.getTowerDTOs().size();i++){
            CompareReturn compareReturn=towers.get(i).compareToDTO(dto.getTowerDTOs().get(i));
            switch (compareReturn){
                case SameIdSameValue:
                    break;
                case SameIdDifferentValue:
                    towers.get(i).setValuesFromDTO(dto.getTowerDTOs().get(i));
                    break;
                case DifferentId:
                    if(dto.getTowerDTOs().get(i).getId() == -1){
                        towers.remove(i);
                        dto.getTowerDTOs().remove(i);
                    }
                    else {
                        towers.get(i).setValuesFromDTO(dto.getTowerDTOs().get(i));
                    }
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

    public Unit getUnitWithId(int id) {
        for(Unit unit:units){
            if(unit.getId()==id){
                return unit;
            }
        }
        return null;
    }
}
