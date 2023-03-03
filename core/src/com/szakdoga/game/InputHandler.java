package com.szakdoga.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.szakdoga.game.towers.Tower;

import java.util.Arrays;

import static com.szakdoga.game.screens.GameScreen.UIscale;

public class InputHandler implements InputProcessor {
    private OrthographicCamera camera;
    private float scale;
    private float limit=10f*UIscale;
    private OrthogonalTiledMapRenderer renderer;
    public void setView(OrthographicCamera camera, float scale, OrthogonalTiledMapRenderer renderer){
        this.camera=camera;//Maybe throw already has camera excpetion?
        this.scale=scale;
        this.renderer=renderer;
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float x = Gdx.input.getDeltaX() / scale;
        float y = Gdx.input.getDeltaY() / scale;
        System.out.println(camera.position.x+x);
        x = x * camera.zoom;
        y = y * camera.zoom;
        if((camera.position.x-x>-limit && camera.position.y+y>-limit) && (camera.position.x-x<((TiledMapTileLayer) renderer.getMap().getLayers().get(0)).getWidth()+limit && camera.position.y+y<((TiledMapTileLayer) renderer.getMap().getLayers().get(0)).getHeight()+limit)){
            camera.translate(-x, y);
        }

        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
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

    /**
     *
     * @param tower The object that should be bought
     * @param actor The actor which listens to the player input button,text,img,etc..
     */
    public static void createListener(Object tower, Actor actor) {
        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //player.add(new tower());
                System.out.println("gdfs");
            }
        });
    }
}
