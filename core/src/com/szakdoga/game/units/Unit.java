package com.szakdoga.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.szakdoga.game.CompareReturn;
import com.szakdoga.game.pathFinder.PathFinder;
import org.datatransferobject.UnitDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public abstract class Unit extends Sprite {
  protected float speed;
  protected float health;
  protected float damage;
  protected int price;
  protected int PreviousX;
  protected int PreviousY;
  private float deltaX;
  private float deltaY;
  private float distance = 0.1f;
  private String unitClass;
  private int id=0;
  private long lastStep;
  private ArrayList<Integer> nextXCoordinates;
  private ArrayList<Integer> nextYCoordinates;

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
    nextXCoordinates = new ArrayList<>();
    nextYCoordinates = new ArrayList<>();
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

  public String getUnitClass() {
    return unitClass;
  }

  public void setUnitClass(String unitClass) {
    this.unitClass = unitClass;
  }
  public void calculateAngle() {
    if(new Date().getTime() - lastStep > 1000/speed) {
      nextXCoordinates.remove(0);
      nextYCoordinates.remove(0);
      lastStep = new Date().getTime();
    }
      float angle = MathUtils.atan2(nextYCoordinates.get(0) - getY(), nextXCoordinates.get(0) - getX());
      deltaX = MathUtils.cos(angle);
      deltaY = MathUtils.sin(angle);
  }
  public void step(){
    calculateAngle();
    setX(getX()+(getSpeed() * getDeltaX() * Gdx.graphics.getDeltaTime()));
    setY(getY()+(getSpeed() * getDeltaY() * Gdx.graphics.getDeltaTime()));
  }

  public void render(SpriteBatch batch) {
    // System.out.println(getX()+"\t"+getY());
    if(id!=0) {
      step();
      super.draw(batch);
    }
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
    nextXCoordinates = unitDTO.getNextX();
    nextYCoordinates = unitDTO.getNextY();
    deltaX = unitDTO.getDeltaX();
    deltaY = unitDTO.getDeltaY();
    lastStep = unitDTO.getLastStep();
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

  public void setDeltaX(float deltaX) {
    this.deltaX = deltaX;
  }

  public void setDeltaY(float deltaY) {
    this.deltaY = deltaY;
  }

  public ArrayList<Integer> getNextX() {
    return nextXCoordinates;
  }

  public void setNextXCoordinates(ArrayList<Integer> nextXCoordinates) {
    this.nextXCoordinates = nextXCoordinates;
  }

  public ArrayList<Integer> getNextY() {
    return nextYCoordinates;
  }

  public void setNextYCoordinates(ArrayList<Integer> nextYCoordinates) {
    this.nextYCoordinates = nextYCoordinates;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSpeed(), getHealth(), getDamage(), getPrice(), getPreviousX(), getPreviousY(), getNextX(), getNextY(), getDeltaX(), getDeltaY(), getDistance());
  }

  public int getId() {
    return id;
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
