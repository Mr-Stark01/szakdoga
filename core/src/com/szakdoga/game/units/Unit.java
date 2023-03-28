package com.szakdoga.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.szakdoga.game.CompareReturn;
import com.szakdoga.game.pathFinder.PathFinder;
import org.datatransferobject.UnitDTO;

import java.util.Objects;

public abstract class Unit extends Sprite {
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
  private float distance = 0.1f;
  private int id=0;
  private String unitClass;

  public Unit(float speed, float health, float damage, int price, float X, float Y,String unitClass) {
    this.speed = speed;
    this.health = health;
    this.damage = damage;
    this.price = price;
    this.PreviousX = (int) X;
    this.PreviousY = (int) Y;
    setX(X); // TODO WHY does this not work?
    setY(Y);
    this.unitClass=unitClass;
  }

  public static PikeUnit createPikeUnit(float X, float Y) {
    return new PikeUnit(X, Y);
  }

  public static Unit createUnitFromDTO(UnitDTO unitDTO) {
    switch (unitDTO.getUnitClass()){
      case "Pike":
        return createPikeUnit(unitDTO.getX(),unitDTO.getY());
    }
    return null;
  }

  public void attacked(float damage) {
    health -= damage;
  }

  public boolean isDead() {
    return health < 0;
  }

  public void calculateAngle() {
    float angle = MathUtils.atan2(NextY - getY(), NextX - getX());
    deltaX = MathUtils.cos(angle);
    deltaY = MathUtils.sin(angle);
  }
  public void step(){
    setX(getX()+(getSpeed() * getDeltaX() * Gdx.graphics.getDeltaTime()));
    setY(getY()+(getSpeed() * getDeltaY() * Gdx.graphics.getDeltaTime()));
  }

  public void render(SpriteBatch batch) {
    // System.out.println(getX()+"\t"+getY());
    step();
    super.draw(batch);
  }

  public CompareReturn compareToDTO(UnitDTO unitDTO){
    if(unitDTO.getId()==this.getId()){
      if(equalsToDTO(unitDTO)){
        return CompareReturn.SameIdSameValue;
      }
      else{
        return CompareReturn.SameIdDifferentValue;
      }
    }
    return CompareReturn.DifferentId;
  }

  public void setValuesFromDTO(UnitDTO unitDTO) {
    speed = unitDTO.getSpeed();
    health = unitDTO.getHealth();
    damage = unitDTO.getDamage();
    price = unitDTO.getPrice();
    PreviousX = unitDTO.getPreviousX();
    PreviousY = unitDTO.getPreviousY();
    NextX = unitDTO.getNextX();
    NextY = unitDTO.getNextY();
    deltaX = unitDTO.getDeltaX();
    deltaY = unitDTO.getDeltaY();
  }

  public boolean equalsToDTO(UnitDTO unit){
    return Float.compare(unit.getSpeed(), getSpeed()) == 0 && Float.compare(unit.getHealth(), getHealth()) == 0 && Float.compare(unit.getDamage(), getDamage()) == 0 && getPrice() == unit.getPrice() && getPreviousX() == unit.getPreviousX() && getPreviousY() == unit.getPreviousY() && getNextX() == unit.getNextX() && getNextY() == unit.getNextY() && Float.compare(unit.getDeltaX(), getDeltaX()) == 0 && Float.compare(unit.getDeltaY(), getDeltaY()) == 0 && Float.compare(unit.getDistance(), getDistance()) == 0;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Unit unit = (Unit) o;
    return Float.compare(unit.getSpeed(), getSpeed()) == 0 && Float.compare(unit.getHealth(), getHealth()) == 0 && Float.compare(unit.getDamage(), getDamage()) == 0 && getPrice() == unit.getPrice() && getPreviousX() == unit.getPreviousX() && getPreviousY() == unit.getPreviousY() && getNextX() == unit.getNextX() && getNextY() == unit.getNextY() && Float.compare(unit.getDeltaX(), getDeltaX()) == 0 && Float.compare(unit.getDeltaY(), getDeltaY()) == 0 && Float.compare(unit.getDistance(), getDistance()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSpeed(), getHealth(), getDamage(), getPrice(), getPreviousX(), getPreviousY(), getNextX(), getNextY(), getDeltaX(), getDeltaY(), getDistance());
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

  public void setNextY(int nextY) {
    NextY = nextY;
  }

  public float getDeltaX() {
    return deltaX;
  }

  public float getDeltaY() {
    return deltaY;
  }

  public float getSpeed() {
    return speed;
  }

  public float getDistance() {
    return distance;
  }

  public float getHealth() {
    return health;
  }

  public float getDamage() {
    return damage;
  }

  public int getPrice() {
    return price;
  }


}
