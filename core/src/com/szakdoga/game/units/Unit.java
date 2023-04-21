package com.szakdoga.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.szakdoga.game.CompareReturn;
import org.datatransferobject.UnitDTO;

import java.util.ArrayList;
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
  protected String textureURL;
  protected Boolean hasTexture=false;

  public Unit(float speed, float health, float damage, int price, float X, float Y,String unitClass) {
    this.speed = speed;
    this.health = health;
    this.damage = damage;
    this.price = price;
    this.PreviousX = (int) X;
    this.PreviousY = (int) Y;
    sprite=new Sprite(); //Creates a sprite
    sprite.setX(X);
    sprite.setY(Y);
    this.unitClass=unitClass;
    nextXCoordinates = new ArrayList<>();
    nextYCoordinates = new ArrayList<>();
  }

  /**
   * Factory method
   * @param unitDto
   * @return
   */
  public static PikeUnit createPikeUnitFromDTO(UnitDTO unitDto) {
    return new PikeUnit(unitDto);
  }
  public static WizardUnit createKnightUnitFromDTO(UnitDTO unitDto) {
    return new WizardUnit(unitDto);
  }
  public static KnightUnit createWizardUnitFromDTO(UnitDTO unitDto) {
    return new KnightUnit(unitDto);
  }

  /**
   * Factory method again just for receiving data from server converting DTO to proper specialized unit class
   * @param unitDTO
   * @return
   */
  public static Unit createUnitFromDTO(UnitDTO unitDTO) {
    switch (unitDTO.getUnitClass()){
      case "Pike":
        return createPikeUnitFromDTO(unitDTO);
      case "Wizard":
        return createKnightUnitFromDTO(unitDTO);
      case "Knight":
        return createWizardUnitFromDTO(unitDTO);
    }
    return null;
  }

  /**
   * A factory method for creating unit
   * @param X starting place
   * @param Y starting place
   * @param unitName class Name in string form es
   * @return Unit
   */
  public static Unit createPikeUnit(int X, int Y, String unitName) {
    return new PikeUnit(X,Y);
  }
  public static Unit createWizardUnit(int X, int Y, String unitName) {
    return new WizardUnit(X,Y);
  }
  public static Unit createKnightUnit(int X, int Y, String unitName) {
    return new KnightUnit(X,Y);
  }


  /**
   * Calculates the deltas for stepping how much should it move between frames
   */
  public void calculateAngle() {
      float angle = MathUtils.atan2(nextYCoordinates.get(0) - sprite.getY(), nextXCoordinates.get(0) - sprite.getX());
      deltaX = MathUtils.cos(angle);
      deltaY = MathUtils.sin(angle);
  }

  /**
   * This functions calculates the next step it's purely graphical
   * game from a logic point only counts rounded coordinates
   */
  public void step(){
    //If reached destination it stops and waits for destruction
    if(nextXCoordinates.get(0)==-1 && nextYCoordinates.get(0)==-1){
      sprite.setX(Math.round(getX()));
      sprite.setY(Math.round(getY()));
      return;
    }
    calculateAngle();
    sprite.setX(sprite.getX()+(getSpeed() * getDeltaX() * Gdx.graphics.getDeltaTime()));
    sprite.setY(sprite.getY()+(getSpeed() * getDeltaY() * Gdx.graphics.getDeltaTime()));
  }

  /**
   * This exists because textures can only be initialized on the main thread otherwise there is a problem with native method calls.
   * Only needed for the enemy since unit creation otherwise happens on the main thread.
   */
  public void addTexture(){
    if(!hasTexture){
      float X=getX(),Y=getY();
      sprite.set(new Sprite(new Texture("textures/tower.png")));
      sprite.setX(X);
      sprite.setY(Y);
      sprite.setSize(1,1);
      hasTexture=true;
    }
  }

  public void render(SpriteBatch batch){
    if (id > 0) {
      addTexture();
      step();
      sprite.draw(batch);
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

  /**
   * Update values coming from Server
   * @param unitDTO
   */
  public synchronized void setValuesFromDTO(UnitDTO unitDTO) {
      this.speed = unitDTO.getSpeed();
      this.health = unitDTO.getHealth();
      this.damage = unitDTO.getDamage();
      this.price = unitDTO.getPrice();
      if(sprite.getX()-unitDTO.getX()>0.3f || sprite.getY()-unitDTO.getY()>0.3f) {
        this.sprite.setX(unitDTO.getX());
        this.sprite.setY(unitDTO.getY());
      }
      this.PreviousX = unitDTO.getPreviousX();
      this.PreviousY = unitDTO.getPreviousY();
      this.nextXCoordinates = deepcopy((ArrayList<Integer>) unitDTO.getNextX());
      this.nextYCoordinates = deepcopy((ArrayList<Integer>) unitDTO.getNextY());
      this.deltaX = unitDTO.getDeltaX();
      this.deltaY = unitDTO.getDeltaY();
      this.lastStep = unitDTO.getLastStep();
      this.id = unitDTO.getId();

  }
  // Bellow this are mostly auto generated functions
  public boolean equalsToDTO(UnitDTO unit){
    return Float.compare(unit.getSpeed(), getSpeed()) == 0 &&
            Float.compare(unit.getHealth(), getHealth()) == 0 &&
            Float.compare(unit.getDamage(), getDamage()) == 0 &&
            getPrice() == unit.getPrice() && getPreviousX() == unit.getPreviousX() &&
            getPreviousY() == unit.getPreviousY() && getNextX().equals(unit.getNextX()) &&
            getNextY().equals(unit.getNextY()) && Float.compare(unit.getDeltaX(), getDeltaX()) == 0 &&
            Float.compare(unit.getDeltaY(), getDeltaY()) == 0 &&
            Float.compare(unit.getDistance(), getDistance()) == 0;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Unit unit = (Unit) o;
    return Float.compare(unit.getSpeed(), getSpeed()) == 0 &&
            Float.compare(unit.getHealth(), getHealth()) == 0 &&
            Float.compare(unit.getDamage(), getDamage()) == 0 &&
            getPrice() == unit.getPrice() &&
            getPreviousX() == unit.getPreviousX() &&
            getPreviousY() == unit.getPreviousY() &&
            getNextX() == unit.getNextX() && getNextY() == unit.getNextY() &&
            Float.compare(unit.getDeltaX(), getDeltaX()) == 0 && Float.compare(unit.getDeltaY(), getDeltaY()) == 0 &&
            Float.compare(unit.getDistance(), getDistance()) == 0;
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

  public long getLastStep() {
    return lastStep;
  }

  public void setLastStep(long lastStep) {
    this.lastStep = lastStep;
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


}
