package com.szakdoga.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.szakdoga.game.InputHandler;
import com.szakdoga.game.TowerDefence;
import com.szakdoga.game.network.DTO.Client;
import com.szakdoga.game.ui.Hud;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameScreen extends ScreenAdapter {
    final TowerDefence game;
    private Client client;
    public static float UIscale=1;
    private SpriteBatch batch;
    static float scale;
    private ExecutorService executor = Executors.newFixedThreadPool(20);
    //Map make own class???
    private TiledMapTileLayer tileyLayer;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private InputHandler inputHandler;
    private Hud hud;
    private InputMultiplexer multiplexer;
    Texture bg;
    public GameScreen(TowerDefence game){
        this.game = game;
        this.batch = game.batch;
        inputHandler = new InputHandler();
        client = new Client("123.123.123.123",123,executor);


    }
    @Override
    public void show(){
        bg = new Texture("menu/start_menu.png");
        //Importing and creating map
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("maps/map.tmx");
        tileyLayer = (TiledMapTileLayer) map.getLayers().get(0);
        scale = (float) tileyLayer.getTileWidth();
        renderer = new OrthogonalTiledMapRenderer(map, 1 / scale);
        camera = new OrthographicCamera();
        camera.viewportHeight = Gdx.graphics.getHeight() / scale;
        camera.viewportWidth = Gdx.graphics.getWidth() / scale;
        renderer.setView(camera);
        inputHandler.setView(camera,scale,renderer);
        ScreenUtils.clear(1, 0, 0, 1);
        game.batch.setProjectionMatrix(camera.combined);
        multiplexer = new InputMultiplexer();
        hud = new Hud(multiplexer);
        multiplexer.addProcessor(inputHandler);
        Gdx.input.setInputProcessor(multiplexer);

    }
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        camera.update();
        batch.begin();
        hud.render();
        batch.end();
    }
}
