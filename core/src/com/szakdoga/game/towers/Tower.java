package com.szakdoga.game.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.CompareReturn;
import com.szakdoga.game.units.Unit;
import org.datatransferobject.TowerDTO;
import org.datatransferobject.UnitDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.szakdoga.game.screens.GameScreen.player;

public abstract class Tower extends Sprite { //TODO teszt osztály
    //TODO factory method és construcktorba csinálni egy new texturet
    protected float damage;
    protected int price;
    protected int range;
    protected Unit target=null;
    protected float deltaSum = 0;
    protected float attackTime;
    protected int id;
    protected String towerClass;
    public Tower(
            int damage, int price, int range, float attackTime, float spawnX, float spawnY,String towerClass) {
        this.towerClass=towerClass;
        this.damage = damage;
        this.price = price;
        this.range = range;
        this.attackTime = attackTime;
        spawnX=(float)Math.floor(spawnX);
        spawnY=(float)Math.floor(spawnY);
        setX(spawnX);
        setY(spawnY);
    }
    //TODO factory val csinálni ezt és madj a aunitot is
    public static ArcherTower createArcherTower(float spawnX,float spawnY){
        return new ArcherTower(spawnX, spawnY,"Archer");
    }
    public static ArcherTower createArcherTowerFromDTO(TowerDTO towerDTO){
        return new ArcherTower(towerDTO);
    }

    public void findTarget(List<Unit> units){
        if(target != null){
            return;
        }
        for(Unit unit:units) {
            if(
            Math.sqrt((Math.pow(unit.getX() - getX(), 2)) +
                      (Math.pow(unit.getY() - getY(), 2)))< range){
                target = unit;
                return;

            }
        }
    }

    public void checkIfEnemyStillInRangeAndAllive(List<Unit> units){//What a beauty
        if(target==null){
            findTarget(units);
        }
        else if(target.isDead()){
            units.remove(target);
            target = null;
            findTarget(units);
        }
        else if(
                Math.sqrt((Math.pow(target.getX() - getX(), 2)) +
                        (Math.pow(target.getY() - getY(), 2))) > range){
                findTarget(units);
            }
    }
    public void attack(List<Unit> units){
        deltaSum += Gdx.graphics.getDeltaTime(); //TODO deltaTime might not be the best course here java time might work
        if(deltaSum > attackTime){
            checkIfEnemyStillInRangeAndAllive(units);
            if(target!=null){
            target.attacked(damage);// TODO
            }
            deltaSum = 0;
        }
    }

    public static Tower createTowerFromDTO(TowerDTO towerDTO) {
        switch (towerDTO.getTowerClass()){
            case "Archer":
                return createArcherTowerFromDTO(towerDTO);
        }
        return null;
    }

    public CompareReturn compareToDTO(TowerDTO towerDTO){
        if(towerDTO.getId()==this.getId()){
            if(equalsToDTO(towerDTO)){
                return CompareReturn.SameIdSameValue;
            }
            else{
                return CompareReturn.SameIdDifferentValue;
            }
        }
        return null;
    }


    public boolean equalsToDTO(TowerDTO tower) {
        return Float.compare(tower.getDamage(), getDamage()) == 0 && getPrice() == tower.getPrice() && getRange() == tower.getRange() && Float.compare(tower.getDeltaSum(), getDeltaSum()) == 0 && Float.compare(tower.getAttackTime(), getAttackTime()) == 0 && Objects.equals(getTarget(), tower.getTarget());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tower tower = (Tower) o;
        return Float.compare(tower.getDamage(), getDamage()) == 0 && getPrice() == tower.getPrice() && getRange() == tower.getRange() && Float.compare(tower.getDeltaSum(), getDeltaSum()) == 0 && Float.compare(tower.getAttackTime(), getAttackTime()) == 0 && Objects.equals(getTarget(), tower.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDamage(), getPrice(), getRange(), getTarget(), getDeltaSum(), getAttackTime());
    }

    public void render(SpriteBatch batch, List<Unit> units){
        /*if(units.size()>0){
            attack(units);//TODO turned off attack
        }*/
        super.draw(batch);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice(){
        return price;
    }

    public float getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public Unit getTarget() {
        return target;
    }

    public float getDeltaSum() {
        return deltaSum;
    }

    public float getAttackTime() {
        return attackTime;
    }

    public void setValuesFromDTO(TowerDTO towerDTO) {
        this.id=towerDTO.getId();
        this.target=player.getUnitWithId(towerDTO.getTarget().getId());
        this.deltaSum=towerDTO.getDeltaSum();
        this.attackTime=towerDTO.getAttackTime();
        this.damage=towerDTO.getDamage();
        this.price=towerDTO.getPrice();
        this.range=towerDTO.getRange();
    }
    //TODO factory method ami csinál egy archerTower instancet
    //TODO teszttower ami
    //TODO teszt tower amit használni
}
