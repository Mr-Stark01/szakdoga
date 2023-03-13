package com.szakdoga.game.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.units.Unit;

import java.util.ArrayList;

public abstract class Tower extends Sprite { //TODO teszt osztály
    //TODO factory method és construcktorba csinálni egy new texturet
    protected float damage;
    protected int price;
    protected int range;
    protected Unit target;
    protected float deltaSum = 0;
    protected float attackTime;
    public Tower(
            int damage, int price, int range, float attackTime, float spawnX, float spawnY) {
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
        return new ArcherTower(spawnX, spawnY);
    }

    public int getPrice(){
        System.out.println("asd");
        return price;
    }
    public void findTarget(ArrayList<Unit> units){
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

    public void checkIfEnemyStillInRangeAndAllive(ArrayList<Unit> units){//What a beauty
        if(target == null){
            findTarget(units);
        }
        else if(
                    Math.sqrt((Math.pow(target.getX() - getX(), 2)) +
                            (Math.pow(target.getY() - getY(), 2))) > range){
                findTarget(units);
            }
    }
    public void attack(ArrayList<Unit> units){
        deltaSum += Gdx.graphics.getDeltaTime(); //TODO deltaTime might not be the best course here java time might work
        checkIfEnemyStillInRangeAndAllive(units);
        if(deltaSum > attackTime){
            target.attacked(damage);
        }

    }
    public void render(SpriteBatch batch,ArrayList<Unit> units){
        attack(units);
        super.draw(batch);
    }
    //TODO factory method ami csinál egy archerTower instancet
    //TODO teszttower ami
    //TODO teszt tower amit használni
}
