package com.szakdoga.game;

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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.szakdoga.game.towers.ArcherTower;
import com.szakdoga.game.towers.Tower;import com.szakdoga.game.units.Unit;

import java.util.Arrays;

import static com.szakdoga.game.screens.GameScreen.UIscale;
import static com.szakdoga.game.screens.GameScreen.player;

public class InputHandler implements InputProcessor {
    private OrthographicCamera camera;
    private float scale;
    private float limit=10f*UIscale;
    private OrthogonalTiledMapRenderer renderer;
    private static Sprite currentlyDragging;
    public void setView(OrthographicCamera camera, float scale, OrthogonalTiledMapRenderer renderer){
        this.camera=camera;//Maybe throw already has camera excpetion?
        this.scale=scale;
        this.renderer=renderer;
    }
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE){
            Gdx.app.exit();
        }
        if(keycode == Input.Keys.P){
            player.buyUnit(Unit.createPikeUnit(6,43));
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
        Vector3 mouse2 = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)); //For getting coordinates
        System.out.println(mouse2.x+"\t"+mouse2.y);
        if(currentlyDragging != null && button == Input.Buttons.LEFT){
            Vector3 mouse = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            player.addTower(mouse.x,mouse.y);
            currentlyDragging=null;
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
            currentlyDragging.setX(mouse.x);
            currentlyDragging.setY(mouse.y);
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
        }
    }
    public void draggingArcher() {
        currentlyDragging=new Sprite(new Texture("textures/tower.png"));
        currentlyDragging.setSize(1,1);
    }
}
