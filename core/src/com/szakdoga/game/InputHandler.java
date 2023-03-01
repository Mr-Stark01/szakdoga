package com.szakdoga.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.Arrays;

public class InputHandler implements InputProcessor {
    private OrthographicCamera camera;
    private float scale;
    public void setView(OrthographicCamera camera, float scale){
        this.camera=camera;//Maybe throw already has camera excpetion?
        this.scale=scale;
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
        x = x * camera.zoom;
        y = y * camera.zoom;
        camera.translate(-x, y);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        amountY = amountY / 10;

        if (amountY > 0 && camera.zoom + amountY < 3) {
            camera.zoom = camera.zoom + amountY;
        }
        if (amountY < 0 && camera.zoom + amountY > 0.1) {
            camera.zoom = camera.zoom + amountY;
        }
        return true;
    }
}
