package com.szakdoga.game.entities.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.szakdoga.game.CompareReturn;
import com.szakdoga.game.DisplayConfig;
import com.szakdoga.game.entities.EntitiesConfig;
import com.szakdoga.game.entities.units.Unit;
import java.util.Objects;
import org.datatransferobject.TowerDTO;

public abstract class Tower{
    protected float damage;
    protected int price;
    protected int range;
    protected Unit target=null;
    protected long lastTimeOfAttack;
    protected float attackTime;
    protected int id;
    protected String towerClass;
    protected Sprite sprite;
    protected int X,Y;
    protected boolean hasTexture=false;
    protected String textureURL;
    protected Projectile projectile;
    private float deltaSumForShoot;
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
            case EntitiesConfig.ARCHER_TOWER:
                return createArcherTowerFromDTO(towerDTO);
            case EntitiesConfig.WIZARD_TOWER:
                return createWizardTowerFromDTO(towerDTO);
            case EntitiesConfig.CROSSBOW_TOWER:
                return createCrossBowTowerFromDTO(towerDTO);
        }
        return null;
    }

    public static Tower createTower(float spawnX,float spawnY,String towerClass) {
        switch (towerClass){
            case EntitiesConfig.ARCHER_TOWER:
                return createArcherTower(spawnX,spawnY);
            case EntitiesConfig.WIZARD_TOWER:
                return createWizardTower(spawnX,spawnY);
            case EntitiesConfig.CROSSBOW_TOWER:
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
        return new ArcherTower(spawnX, spawnY, EntitiesConfig.ARCHER_TOWER);
    }
    public static Tower createWizardTower(float spawnX,float spawnY){
        return new WizardTower(spawnX, spawnY,EntitiesConfig.WIZARD_TOWER);
    }
    public static Tower createCrossBowTower(float spawnX,float spawnY){
        return new CrossBowTower(spawnX, spawnY,EntitiesConfig.CROSSBOW_TOWER);
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
            sprite.set(new Sprite(new Texture(DisplayConfig.TOWER_TEXTURE)));
            sprite.setX(X);
            sprite.setY(Y);
            sprite.setSize(1,1);
            hasTexture=true;
        }
    }

    public void shot(SpriteBatch batch){
        deltaSumForShoot+=Gdx.graphics.getDeltaTime();
        if(target != null && !target.isDead()){
            int toShotX,toShotY;
            synchronized (this){
                if(target!=null &&target.getNextX().size()>2) {
                    toShotX = target.getNextX().get(1);
                    toShotY = target.getNextY().get(1);
                }
                else{
                    return ;
                }
            }
            if(deltaSumForShoot*0.8f>attackTime && target.getNextX().size()>2){
                deltaSumForShoot=0;
                projectile=new Projectile(X,Y,toShotX,toShotY);
                projectile.shot(batch);
            }
        }
        else{
            projectile=null;
        }
        if(projectile!=null){
            projectile.shot(batch);
            if(projectile.reached()){
                projectile=null;
            }
        }
    }


    /**
     * Main render method gets called for every frame everything visual in this class should be called here such as projectiles
     * @param batch
     */
    public void render(SpriteBatch batch, Color color){
        if (id > 0) {
            addTexture();
            shot(batch);
            sprite.setColor(color);
            sprite.draw(batch);
        }
    }

    /**
     * Value copy from DTO with null check if target is non-existent.
     * @param towerDTO
     */
    public void setValuesFromDTO(TowerDTO towerDTO) {
        this.id = towerDTO.getId();
        this.target = towerDTO.getTarget() == null ? null : Unit.createUnitFromDTO(towerDTO.getTarget());
        this.attackTime = towerDTO.getAttackTime();
        this.lastTimeOfAttack=towerDTO.getLastTimeOfAttack();
        this.damage = towerDTO.getDamage();
        this.price = towerDTO.getPrice();
        this.range = towerDTO.getRange();
    }
    //Only autoGenerated method beyond this.
    public boolean equalsToDTO(TowerDTO tower) {
        return Float.compare(tower.getDamage(), getDamage()) == 0 &&
                getPrice() == tower.getPrice() &&
                getRange() == tower.getRange() &&
                Float.compare(tower.getLastTimeOfAttack(), getLastTimeOfAttack()) == 0 &&
                Float.compare(tower.getAttackTime(), getAttackTime()) == 0 &&
                tower.getLastTimeOfAttack() == getLastTimeOfAttack() &&
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
                && Float.compare(tower.getLastTimeOfAttack(), getLastTimeOfAttack()) == 0 &&
                Float.compare(tower.getAttackTime(), getAttackTime()) == 0 &&
                Objects.equals(getTarget(), tower.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDamage(), getPrice(), getRange(), getTarget(), getLastTimeOfAttack(), getAttackTime());
    }

    public long getLastTimeOfAttack() {
        return lastTimeOfAttack;
    }

    public void setLastTimeOfAttack(long lastTimeOfAttack) {
        this.lastTimeOfAttack = lastTimeOfAttack;
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

    public float getAttackTime() {
        return attackTime;
    }

    public float getX(){
        return X;
    }

    public void setX(float X){
        this.X=(int)X;
    }

    public float getY(){
        return Y;
    }

    public void setY(float Y){
        this.Y=(int)Y;
    }
}
