package com.szakdoga.game.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Projectile {
    private Sprite sprite;
    private float deltaX,deltaY,targetX,targetY;
    private float speed=10f;
    private float dieSum;
    public Projectile(float X,float Y,float targetX,float targetY){
        sprite=new Sprite(new Texture("textures/projectile.png"));
        sprite.setPosition(X,Y);
        sprite.setSize(0.5f,0.5f);
        this.targetX=targetX;
        this.targetY=targetY;
        calculateAngle();
    }
    public void calculateAngle() {
        float angle = MathUtils.atan2(targetY - sprite.getY(), targetX - sprite.getX());
        deltaX = MathUtils.cos(angle);
        deltaY = MathUtils.sin(angle);
    }
    public void shot(SpriteBatch batch){
        sprite.setX(sprite.getX()+(speed* Gdx.graphics.getDeltaTime()*deltaX));
        sprite.setY(sprite.getY()+(speed* Gdx.graphics.getDeltaTime()*deltaY));
        sprite.draw(batch);
        dieSum+=Gdx.graphics.getDeltaTime();
    }
    public boolean reached(){
        return dieSum>2;
    }
}
