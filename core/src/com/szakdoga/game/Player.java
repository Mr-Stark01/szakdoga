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
            tower.render(batch);
        }
        for(Unit unit:units){
            unit.render(batch);
        }

    }

    public void exchangeData(DTO dto){
        System.out.println("echange data1");
        PlayerDTO playerDTO =dto.getPlayerDTO();
        money = playerDTO.getMoney();
        health = playerDTO.getHealth();
        System.out.println("echange data2");
        System.out.println("dtosize:"+dto.getUnitDTOs().size()+"\t"+"units size:"+units.size());
        for(int i = dto.getUnitDTOs().size()-units.size()-1; dto.getUnitDTOs().size()>units.size(); i++) { //TODO this is garbage
            units.add(Unit.createUnitFromDTO(dto.getUnitDTOs().get(i)));
            System.out.println("stuck??");
        }
        System.out.println("echange data3");
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
                    else {
                        System.out.println("exchanger");
                        units.get(i).setValuesFromDTO(dto.getUnitDTOs().get(i));
                        System.out.println(units.get(i).getId());
                    }
                    break;
            }
        }
        System.out.println("echange data4");
        for(int i = dto.getTowerDTOs().size()-towers.size()-1; dto.getTowerDTOs().size()>towers.size() && i>=0; i++) { //TODO this is garbage
            try {
                towers.add(Tower.createTowerFromDTO(dto.getTowerDTOs().get(i)));
            }
            catch (Exception e){
                e.printStackTrace();
                try {
                    Thread.sleep(100000);
                }
                catch (InterruptedException a){
                    a.printStackTrace();
                }
            }
            System.out.println("stuck??");
        }
        System.out.println("echange data5");
        for(int i = 0; i<dto.getTowerDTOs().size();i++){
            System.out.println("tower data");
            CompareReturn compareReturn=towers.get(i).compareToDTO(dto.getTowerDTOs().get(i));
            System.out.println("tower data 2");
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
                        System.out.println("exchanger");
                        towers.get(i).setValuesFromDTO(dto.getTowerDTOs().get(i));
                        System.out.println(towers.get(i).getId());
                    }
                    break;
            }
        }
    }

    public void exchangeDataEnemy(DTO dto){
        PlayerDTO playerDTO = dto.getPlayerDTO();
        money = playerDTO.getMoney();
        health = playerDTO.getHealth();
        System.out.println("exchangerEnemy");

        System.out.println(dto.getUnitDTOs().size()+"\t"+units.size()+"exchangerEnemy2");
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
                    else {

                        units.get(i).setValuesFromDTO(dto.getUnitDTOs().get(i));
                        System.out.println(units.get(i).getId());
                    }
                    break;
            }
        }
        System.out.println(dto.getUnitDTOs().size()+"\t"+units.size()+"exchangerEnemy3");
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
