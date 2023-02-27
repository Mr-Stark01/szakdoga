package com.szakdoga.game.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.szakdoga.game.TowerDefence;
import com.szakdoga.game.network.DTO.Client;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameScreen extends ScreenAdapter {
    final TowerDefence game;
    private Client client;
    private SpriteBatch batch;
    static float scale;
    private ExecutorService executor = Executors.newFixedThreadPool(20);
    //Map make own class???
    private TiledMapTileLayer tileyLayer;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    Texture bg;
    public GameScreen(TowerDefence game){
        this.game = game;
        this.batch = game.batch;
        client = new Client("123.123.123.123",123,executor);

    }
    @Override
    public void show(){
        bg = new Texture("menu/start_menu.png");
        //Importing and creating map
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("maps/map_01.tmx");
        tileyLayer = (TiledMapTileLayer) map.getLayers().get(0);
        scale = (float) tileyLayer.getTileWidth();
        renderer = new OrthogonalTiledMapRenderer(map, 1 / scale);
        camera = new OrthographicCamera();
        renderer.setView(camera);
    }
    @Override
    public void render(float delta){
        batch.begin();
        batch.draw(bg,0,0,game.screenWidth,game.screenHeight);
        batch.end();
        renderer.render();
    }
}
