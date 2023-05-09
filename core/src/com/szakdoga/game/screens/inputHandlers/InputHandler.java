package com.szakdoga.game.screens.inputHandlers;

import static com.szakdoga.game.TowerDefence.UIscale;
import static com.szakdoga.game.screens.GameScreen.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.szakdoga.game.config.DisplayConfig;
import com.szakdoga.game.Logger;
import com.szakdoga.game.entities.towers.TowerRangeCircle;
import com.szakdoga.game.entities.units.Unit;

public class InputHandler implements InputProcessor {
    private static Sprite currentlyDragging;
    private static TowerRangeCircle towerRangeCircle;
    private OrthographicCamera camera;
    private float scale;
    private float limit = DisplayConfig.CAMERA_AREA_LIMIT * UIscale;
    private OrthogonalTiledMapRenderer renderer;

    public void setView(OrthographicCamera camera, float scale, OrthogonalTiledMapRenderer renderer){
        this.camera=camera;
        this.scale=scale;
        this.renderer=renderer;
    }
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE){
            Gdx.app.exit();
            System.exit(-1);
        }
        if(keycode == Input.Keys.P){
            player.buyUnit(Unit.createPikeUnit(player.getPositionX(), player.getPositionY(), "PikeUnitPlaceHolder"));
            Logger.writeLogDisplayLog("log","player bought pikeunit",this.getClass().getName());
        }
        if(keycode == Input.Keys.ENTER){
            player.sendMessage();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(currentlyDragging != null && button == Input.Buttons.LEFT){
            Vector3 mouse = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            player.addTower(mouse.x,mouse.y);
            currentlyDragging=null;
            towerRangeCircle=null;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float x = Gdx.input.getDeltaX() / scale;
        float y = Gdx.input.getDeltaY() / scale;
        x = x * camera.zoom;
        y = y * camera.zoom;
        if((camera.position.x-x>-limit && camera.position.y+y>-limit) &&
                (camera.position.x-x<((TiledMapTileLayer) renderer.getMap().getLayers().get(0)).getWidth()+limit &&
                camera.position.y+y<((TiledMapTileLayer) renderer.getMap().getLayers().get(0)).getHeight()+limit)){
            camera.translate(-x, y);
        }

        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if(currentlyDragging != null){
            Vector3 mouse = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            currentlyDragging.setX(mouse.x-0.5f);
            currentlyDragging.setY(mouse.y-0.5f);
            towerRangeCircle.updatePos(mouse.x,mouse.y);
        }
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        amountY = amountY / 10;

        if (amountY > 0 && camera.zoom + amountY < 10) {
            camera.zoom = camera.zoom + amountY;
        }
        if (amountY < 0 && camera.zoom + amountY > 0.1) {
            camera.zoom = camera.zoom + amountY;
        }
        return true;
    }
    public void render(SpriteBatch batch){
        if(currentlyDragging != null) {
            currentlyDragging.draw(batch);
            towerRangeCircle.draw(batch);
        }
    }
    public void draggingArcher(String fileHandle,int range) {
        currentlyDragging=new Sprite(new Texture(fileHandle));
        towerRangeCircle = new TowerRangeCircle(range);
        currentlyDragging.setSize(1,1);
    }
}
