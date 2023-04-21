package com.szakdoga.game.network.DTO;

import com.szakdoga.game.Player;
import com.szakdoga.game.towers.Tower;
import com.szakdoga.game.units.Unit;
import org.datatransferobject.PlayerDTO;
import org.datatransferobject.TowerDTO;
import org.datatransferobject.UnitDTO;

import java.util.ArrayList;
import java.util.List;

public class Preparator {
    public static UnitDTO createUnitDTOFromUnit(Unit unit){
        if(unit==null){
            return null;
        }
        return new UnitDTO(
                unit.getSpeed(),
                unit.getHealth(),
                unit.getDamage(),
                unit.getPrice(),
                unit.getPreviousX(),
                unit.getPreviousY(),
                unit.getDeltaX(),
                unit.getDeltaY(),
                unit.getDistance(),
                unit.getX(),
                unit.getY(),
                new String(unit.getUnitClass()),
                unit.getId(),
                deepcopy(unit.getNextX()),
                deepcopy(unit.getNextY()),
                unit.getLastStep());
    }

    public static ArrayList<Integer> deepcopy(ArrayList<Integer> nextList) {
        ArrayList<Integer> copy=new ArrayList<Integer>();
        for(int elem:nextList){
            copy.add(elem);
        }
        return copy;
    }

    public static TowerDTO createTowerDTOfromTower(Tower tower){
        return new TowerDTO(
                (int) tower.getX(),
                (int) tower.getY(),
                tower.getDamage(),
                tower.getPrice(),
                tower.getRange(),
                createUnitDTOFromUnit(tower.getTarget()),
                tower.getAttackTime(),
                tower.getDeltaSum(),
                tower.getId(),
                tower.getTowerClass());
    }
    public static PlayerDTO createPlayerDTOFromPlayer(Player player){
        return new PlayerDTO(player.getMoney(),
                                player.getPositionX(),
                                player.getPositionY(),
                                player.getHealth());
    }
    public static ArrayList<UnitDTO> createUnitDTOListFromUnitList(List<Unit> units){
        ArrayList<UnitDTO> unitDTOs= new ArrayList<>();
        synchronized (units) {
            for (Unit unit : units) {
                try {
                    unitDTOs.add(Preparator.createUnitDTOFromUnit(unit));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return unitDTOs;
    }
    public static ArrayList<TowerDTO> createTowerDTOListFromTowertList(List<Tower> towers){
        ArrayList<TowerDTO> towerDTOS= new ArrayList<>();
        synchronized (towers) {
            for (Tower tower : towers) {
                towerDTOS.add(Preparator.createTowerDTOfromTower(tower));
            }
        }
        return towerDTOS;
    }
}
