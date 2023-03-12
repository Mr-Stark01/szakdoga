package com.szakdoga.game.screens;

import com.badlogic.gdx.Gdx;
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
import com.szakdoga.game.Player;
import com.szakdoga.game.TowerDefence;
import com.szakdoga.game.network.DTO.Client;
import com.szakdoga.game.pathFinder.PathFinder;
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
    public static Player player;
    public static Player enemyPlayer;
    private Thread t;
    public GameScreen(TowerDefence game){
        this.game = game;
        this.batch = new SpriteBatch();
        inputHandler = new InputHandler();
        client = new Client("123.123.123.123",123,executor);
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }
    @Override
    public void show(){
        bg = new Texture("textures/tower.png");
        //Importing and creating map
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("maps/map.tmx");
        tileyLayer = (TiledMapTileLayer) map.getLayers().get(0);
        scale = (float) tileyLayer.getTileWidth();
        renderer = new OrthogonalTiledMapRenderer(map, 1 / scale);

        //Player and pathfinder
        PathFinder pathfinder= new PathFinder(tileyLayer);
        player = new Player(pathfinder);
        enemyPlayer = new Player(pathfinder);

        camera = new OrthographicCamera();
        camera.viewportHeight = Gdx.graphics.getHeight() / scale;
        camera.viewportWidth = Gdx.graphics.getWidth() / scale;

        inputHandler.setView(camera,scale,renderer);
        ScreenUtils.clear(1, 0, 0, 1);


        multiplexer = new InputMultiplexer();
        hud = new Hud(multiplexer,batch,inputHandler);
        multiplexer.addProcessor(inputHandler);
        Gdx.input.setInputProcessor(multiplexer);
        batch.setProjectionMatrix(camera.combined);
        //new Pathfinder(map).showPath();
    }
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        batch.begin();
        inputHandler.render(batch);
        player.render(batch);
        batch.end();
        hud.render();
    }
}
