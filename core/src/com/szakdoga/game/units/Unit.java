package com.szakdoga.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;import com.badlogic.gdx.math.MathUtils;

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
    private float distance=0.1f;


    public Unit(float speed, float health, float damage, int price,float X,float Y){
        this.speed=speed;
        this.health=health;
        this.damage=damage;
        this.price=price;
        this.PreviousX=(int)X;
        this.PreviousY=(int)Y;
        setX(X);//TODO WHY does this not work?
        setY(Y);
    }

    public void render(SpriteBatch batch){
        //System.out.println(getX()+"\t"+getY());
        super.draw(batch);
    }

    public static PikeUnit createPikeUnit(float X,float Y){
        return new PikeUnit(X,Y);
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
                        NextY - getY(), NextX - getX());
        deltaX = MathUtils.cos(angle);
        deltaY = MathUtils.sin(angle);
    }

    public float getDistance() {
        return distance;
    }

    public void attacked(float damage) {
        health-=damage;
    }
}
