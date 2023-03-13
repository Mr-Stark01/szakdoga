package com.szakdoga.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public abstract class Unit extends Sprite{
    protected float speed;
    protected float health;
    protected float damage;
    protected int price;
    protected int PreviousX;
    protected int PreviousY;

    protected int NextX;
    protected int NextY;
    private float deltaX;
    private float deltaY;
    private float distance;


    public Unit(float speed, float health, float damage, int price){
        this.speed=speed;
        this.health=health;
        this.damage=damage;
        this.price=price;
        this.PreviousX=(int)getX();
        this.PreviousY=(int)getY();
    }

    public int getPreviousX() {
        return PreviousX;
    }

    public void setPreviousX(int previousX) {
        PreviousX = previousX;
    }

    public int getPreviousY() {
        return PreviousY;
    }

    public void setPreviousY(int previousY) {
        PreviousY = previousY;
    }

    public int getNextX() {
        return NextX;
    }

    public void setNextX(int nextX) {
        NextX = nextX;
    }

    public int getNextY() {
        return NextY;
    }

    public float getDeltaX() {
        return deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }

    public void setNextY(int nextY) {
        NextY = nextY;
    }

    public float getSpeed() {
        return speed;
    }

    public void calculateAngle() {
        float angle =
                MathUtils.atan2(
                        NextY - PreviousY, NextX - PreviousX);
        deltaX = MathUtils.cos(angle) * speed;
        deltaY = MathUtils.sin(angle) * speed;
    }

    public float getDistance() {
        return distance;
    }

    public void attacked(float damage) {
        health-=damage;
    }
}
