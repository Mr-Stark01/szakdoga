package com.szakdoga.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.entities.towers.Tower;
import com.szakdoga.game.entities.units.Unit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.datatransferobject.DTO;
import org.datatransferobject.PlayerDTO;

public class Player {
    private final Color color;
    private List<Tower> towers = Collections.synchronizedList(new ArrayList<Tower>());
    private List<Unit> units= Collections.synchronizedList(new ArrayList<Unit>());
    private int money=10000;
    private int positionX=-1,positionY=-1;
    private float health=100;
    private Tower towerInDraggingState;
    private Sprite base;



    public Player(String baseURL,Color color){
        base=new Sprite();
        base.set(new Sprite(new Texture(baseURL)));
        base.setSize(1,1);
        this.color=color;

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
            towers.get(i).render(batch,color);
        }
        for (int i=0;i<units.size();i++) {
            units.get(i).render(batch,color);
        }
        updateBasePosition();
        base.draw(batch);

    }
    public void updateBasePosition(){
        base.setX(positionX);
        base.setY(positionY);
        base.setColor(color);
        base.setSize(1,1);
    }


    public void exchangeData(DTO dto){
        PlayerDTO playerDTO = dto.getPlayerDTO();
        money = playerDTO.getMoney();
        health = playerDTO.getHealth();
        positionX = playerDTO.getPositionX();
        positionY = playerDTO.getPositionY();
        //Create new units only used if it's enemy data
        for(int i = units.size(); dto.getUnitDTOs().size()>i; i++) {
            if(dto.getUnitDTOs().get(i).getId()!=-1) {
                units.add(Unit.createUnitFromDTO(dto.getUnitDTOs().get(i)));
            }
            else{
                dto.getUnitDTOs().remove(i);
                i--;
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

        for(int i = towers.size(); dto.getTowerDTOs().size()>i ; i++) {
            if(dto.getTowerDTOs().get(i).getId()!=-1) {
                towers.add(Tower.createTowerFromDTO(dto.getTowerDTOs().get(i)));
            }
            else{
                dto.getTowerDTOs().remove(i);
                i--;
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
