package com.szakdoga.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.szakdoga.game.CompareReturn;
import org.datatransferobject.UnitDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.szakdoga.game.network.DTO.Preparator.deepcopy;

public abstract class Unit {
  protected float speed;
  protected float health;
  protected float damage;
  protected int price;
  protected int PreviousX;
  protected int PreviousY;
  protected float deltaX;
  protected float deltaY;
  protected float distance = 0.1f;
  protected String unitClass;
  protected int id=0;
  protected long lastStep;
  protected List<Integer> nextXCoordinates;
  protected List<Integer> nextYCoordinates;
  protected Sprite sprite;

  public Unit(float speed, float health, float damage, int price, float X, float Y,String unitClass) {
    this.speed = speed;
    this.health = health;
    this.damage = damage;
    this.price = price;
    this.PreviousX = (int) X;
    this.PreviousY = (int) Y;
    sprite=new Sprite();
    sprite.setX(X); // TODO WHY does this not work?
    sprite.setY(Y);
    this.unitClass=unitClass;
    nextXCoordinates = new ArrayList<>();
    nextYCoordinates = new ArrayList<>();
  }

  public static PikeUnit createPikeUnitFromDTO(UnitDTO unitDto) {
    return new PikeUnit(unitDto);
  }

  public static Unit createUnitFromDTO(UnitDTO unitDTO) {
    switch (unitDTO.getUnitClass()){
      case "Pike":
      case "PikeUnitPlaceHolder":
        return createPikeUnitFromDTO(unitDTO);
    }
    return null;
  }

  public static Unit createPikeUnit(int X, int Y, String unitName) {
    return new PikeUnit(X,Y);
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
    if(/*new Date().getTime() - lastStep > 1000/speed*/Math.sqrt((Math.pow(this.sprite.getX()-this.getNextX().get(0),2))+(Math.pow(this.sprite.getY()-this.getNextY().get(0),2))) < 0.1f ) {
      System.out.println("delete first from list");
      nextXCoordinates.remove(0);
      nextYCoordinates.remove(0);
      lastStep = new Date().getTime();

      float angle = MathUtils.atan2(nextYCoordinates.get(0) - sprite.getY(), nextXCoordinates.get(0) - sprite.getX());
      deltaX = MathUtils.cos(angle);
      deltaY = MathUtils.sin(angle);
    }
  }
  public void step(){
    if(nextXCoordinates.get(0)==-1 && nextXCoordinates.get(0)==-1){
      sprite.setX(Math.round(getX()));
      sprite.setY(Math.round(getY()));
      return;
    }
    calculateAngle();
    sprite.setX(sprite.getX()+(getSpeed() * getDeltaX() * Gdx.graphics.getDeltaTime()));
    sprite.setY(sprite.getY()+(getSpeed() * getDeltaY() * Gdx.graphics.getDeltaTime()));
  }

  public  void render(SpriteBatch batch) {
    // System.out.println(getX()+"\t"+getY());
    if(id!=0) {
      synchronized (this) {
        step();
        sprite.draw(batch);
      }
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

  public synchronized void setValuesFromDTO(UnitDTO unitDTO) {
      this.speed = unitDTO.getSpeed();
      this.health = unitDTO.getHealth();
      this.damage = unitDTO.getDamage();
      this.price = unitDTO.getPrice();
      this.PreviousX = unitDTO.getPreviousX();
      this.PreviousY = unitDTO.getPreviousY();
      this.nextXCoordinates = deepcopy((ArrayList<Integer>) unitDTO.getNextX());
      this.nextYCoordinates = deepcopy((ArrayList<Integer>) unitDTO.getNextY());
      this.deltaX = unitDTO.getDeltaX();
      this.deltaY = unitDTO.getDeltaY();
      this.lastStep = unitDTO.getLastStep();
      this.id = unitDTO.getId();

      System.out.println("setdatafromdtoX" + nextXCoordinates.size() + "\t" + unitDTO.getNextX().size());
      System.out.println("setdatafromdtoY" + nextYCoordinates.size() + "\t" + unitDTO.getNextY().size());
  }

  public boolean equalsToDTO(UnitDTO unit){
    return Float.compare(unit.getSpeed(), getSpeed()) == 0 && Float.compare(unit.getHealth(), getHealth()) == 0 && Float.compare(unit.getDamage(), getDamage()) == 0 && getPrice() == unit.getPrice() && getPreviousX() == unit.getPreviousX() && getPreviousY() == unit.getPreviousY() && getNextX().equals(unit.getNextX()) && getNextY().equals(unit.getNextY()) && Float.compare(unit.getDeltaX(), getDeltaX()) == 0 && Float.compare(unit.getDeltaY(), getDeltaY()) == 0 && Float.compare(unit.getDistance(), getDistance()) == 0;
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
    return (ArrayList<Integer>)  nextXCoordinates;
  }

  public void setNextXCoordinates(ArrayList<Integer> nextXCoordinates) {
    this.nextXCoordinates = nextXCoordinates;
  }

  public ArrayList<Integer> getNextY() {
    return (ArrayList<Integer>) nextYCoordinates;
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

  public float getX(){
    return sprite.getX();
  }
  public float getY(){
    return sprite.getY();
  }


}
