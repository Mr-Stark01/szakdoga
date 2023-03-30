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
        System.out.println("preparator");
        return new UnitDTO(unit.getSpeed(),
                            unit.getHealth(),
                            unit.getDamage(),
                            unit.getPrice(),
                            unit.getPreviousX(),
                            unit.getPreviousY(),
                            unit.getNextX(),
                            unit.getNextY(),
                            unit.getX(),
                            unit.getY(),
                            unit.getDeltaX(),
                            unit.getDeltaY(),
                            unit.getDistance(),
                            unit.getUnitClass(),
                            unit.getId());
    }
    public static TowerDTO createTowerDTOfromTower(Tower tower){
        return new TowerDTO(tower.getDamage(),
                tower.getPrice(),
                tower.getRange(),
                createUnitDTOFromUnit(tower.getTarget()),
                tower.getAttackTime());
    }
    public static PlayerDTO createPlayerDTOFromPlayer(Player player){
        return new PlayerDTO(player.getMoney(),
                                player.getPositionX(),
                                player.getPositionY(),
                                player.getHealth());
    }
    public static ArrayList<UnitDTO> createUnitDTOListFromUnitList(List<Unit> units){
        ArrayList<UnitDTO> unitDTOs= new ArrayList<UnitDTO>();
        synchronized (units) {
            for (Unit unit : units) {
                unitDTOs.add(Preparator.createUnitDTOFromUnit(unit));
            }
        }
        return unitDTOs;
    }
    public static ArrayList<TowerDTO> createTowerDTOListFromTowertList(List<Tower> towers){
        ArrayList<TowerDTO> towerDTOS= new ArrayList<TowerDTO>();
        synchronized (towers) {
            for (Tower tower : towers) {
                towerDTOS.add(Preparator.createTowerDTOfromTower(tower));
            }
        }
        return towerDTOS;
    }
}
