package com.szakdoga.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.CompareReturn;
import com.szakdoga.game.units.Unit;
import org.datatransferobject.TowerDTO;

import java.util.Objects;

import static com.szakdoga.game.screens.GameScreen.player;

public abstract class Tower{ //TODO teszt osztály
    //TODO factory method és construcktorba csinálni egy new texturet
    protected float damage;
    protected int price;
    protected int range;
    protected Unit target=null;
    protected float deltaSum = 0;
    protected float attackTime;
    protected int id;
    protected String towerClass;
    protected Sprite sprite;
    protected int X,Y;
    protected boolean hasTexture=false;
    protected String textureURL;
    public Tower(
            float damage, int price, int range, float attackTime, float spawnX, float spawnY,String towerClass) {
        this.towerClass=towerClass;
        this.damage = damage;
        this.price = price;
        this.range = range;
        this.attackTime = attackTime;
        spawnX=(float)Math.floor(spawnX);
        spawnY=(float)Math.floor(spawnY);
        sprite=new Sprite();
        sprite.setX(spawnX);
        sprite.setY(spawnY);
        X=(int)spawnX;
        Y=(int)spawnY;
    }

    /**
     * Factory method creation of its own class might be good idea.
     * @param towerDTO
     * @return
     */
    public static Tower createTowerFromDTO(TowerDTO towerDTO) {
        switch (towerDTO.getTowerClass()){
            case "Archer":
                return createArcherTowerFromDTO(towerDTO);
            case "Wizard":
                return createWizardTowerFromDTO(towerDTO);
            case "CrossBow":
                return createCrossBowTowerFromDTO(towerDTO);
        }
        return null;
    }

    public static Tower createTower(float spawnX,float spawnY,String towerClass) {
        switch (towerClass){
            case "Archer":
                return createArcherTower(spawnX,spawnY);
            case "Wizard":
                return createWizardTower(spawnX,spawnY);
            case "CrossBow":
                return createCrossBowTower(spawnX,spawnY);
        }
        return null;
    }

    /**
     * Archer factory method
     * @param spawnX
     * @param spawnY
     * @return
     */
    public static Tower createArcherTower(float spawnX,float spawnY){
        return new ArcherTower(spawnX, spawnY,"Archer");
    }
    public static Tower createWizardTower(float spawnX,float spawnY){
        return new WizardTower(spawnX, spawnY,"Wizard");
    }
    public static Tower createCrossBowTower(float spawnX,float spawnY){
        return new CrossBowTower(spawnX, spawnY,"CrossBow");
    }

    /**
     * Factory merthod for DTO
     * @param towerDTO
     * @return
     */
    public static Tower createArcherTowerFromDTO(TowerDTO towerDTO){
        return new ArcherTower(towerDTO);
    }
    public static Tower createWizardTowerFromDTO(TowerDTO towerDTO){
        return new WizardTower(towerDTO);
    }
    public static Tower createCrossBowTowerFromDTO(TowerDTO towerDTO){
        return new CrossBowTower(towerDTO);
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
        return CompareReturn.DifferentId;
    }

    /**
     * This exists because textures can only be initialized on the main thread otherwise there is a problem with native method calls.
     * Only needed for the enemy since tower creation otherwise happens on the main thread.
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

    /**
     * Main render method gets called for every frame everything visual in this class should be called here such as projectiles
     * @param batch
     */
    public void render(SpriteBatch batch){
        if (id > 0) {
            addTexture();
            sprite.draw(batch);
        }
    }

    /**
     * Value copy from DTO with null check if target is non-existent.
     * @param towerDTO
     */
    public void setValuesFromDTO(TowerDTO towerDTO) {
        this.id = towerDTO.getId();
        this.target = towerDTO.getTarget() == null ? null : player.getUnitWithId(towerDTO.getTarget().getId());
        this.deltaSum = towerDTO.getDeltaSum();
        this.attackTime = towerDTO.getAttackTime();
        this.damage = towerDTO.getDamage();
        this.price = towerDTO.getPrice();
        this.range = towerDTO.getRange();
    }
    //Only autoGenerated method beyond this.
    public boolean equalsToDTO(TowerDTO tower) {
        return Float.compare(tower.getDamage(), getDamage()) == 0 &&
                getPrice() == tower.getPrice() &&
                getRange() == tower.getRange() &&
                Float.compare(tower.getDeltaSum(), getDeltaSum()) == 0 &&
                Float.compare(tower.getAttackTime(), getAttackTime()) == 0 &&
                Objects.equals(getTarget(), tower.getTarget());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tower tower = (Tower) o;
        return Float.compare(tower.getDamage(), getDamage()) == 0 &&
                getPrice() == tower.getPrice() &&
                getRange() == tower.getRange()
                && Float.compare(tower.getDeltaSum(), getDeltaSum()) == 0 &&
                Float.compare(tower.getAttackTime(), getAttackTime()) == 0 &&
                Objects.equals(getTarget(), tower.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDamage(), getPrice(), getRange(), getTarget(), getDeltaSum(), getAttackTime());
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

    public String getTowerClass() {
        return towerClass;
    }

    public void setTowerClass(String towerClass) {
        this.towerClass = towerClass;
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

    public void setX(float X){
        this.X=(int)X;
    }
    public void setY(float Y){
        this.Y=(int)Y;
    }
    public float getX(){
        return X;
    }
    public float getY(){
        return Y;
    }
}
