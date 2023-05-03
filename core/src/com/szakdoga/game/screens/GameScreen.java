package com.szakdoga.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.szakdoga.game.config.DisplayConfig;
import com.szakdoga.game.Logger;
import com.szakdoga.game.Player;
import com.szakdoga.game.TowerDefence;
import com.szakdoga.game.network.FileServerHandler;
import com.szakdoga.game.network.GameServerHandler;
import com.szakdoga.game.screens.inputHandlers.InputHandler;
import com.szakdoga.game.ui.Hud;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameScreen extends ScreenAdapter {
    private static final String MAP_URI="maps/defmap.tmx";
    public static Player player;
    public static Player enemyPlayer;
    public static TiledMapTileLayer tileLayer;
    static float tileScale;
    final TowerDefence game;
    private final String ip;
    private final String name;
    private SpriteBatch batch;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(20);
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private InputHandler inputHandler;
    private Hud hud;
    private InputMultiplexer multiplexer;
    public GameScreen(TowerDefence game,String ip,String name){
        this.ip=ip;
        this.name=name;
        this.game = game;
        this.batch = new SpriteBatch();
        inputHandler = new InputHandler();
    }
    @Override
    public void show(){
        //Acquire map from server
        //new FileServerHandler(ip,56227);
        //Importing and creating map
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load(MAP_URI);
        tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
        tileScale = (float) tileLayer.getTileWidth();
        renderer = new OrthogonalTiledMapRenderer(map, 1 / tileScale);

        player = new Player(DisplayConfig.TOWER_TEXTURE,DisplayConfig.BLUE_COLOR);
        enemyPlayer = new Player(DisplayConfig.DRAGON_TEXTURE,DisplayConfig.RED_COLOR);


        GameServerHandler gameServerHandler;
        try {
            gameServerHandler = new GameServerHandler(ip,56227,name);
            Logger.writeLogDisplayLog("LOG","Succesfully connected to server",this.getClass().getSimpleName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        executor.scheduleAtFixedRate(gameServerHandler,0,10,TimeUnit.MILLISECONDS);
        Logger.writeLogDisplayLog("LOG","executor started and linked with server handler",this.getClass().getSimpleName());

        //Player

        camera = new OrthographicCamera();
        camera.viewportHeight = Gdx.graphics.getHeight() / tileScale;
        camera.viewportWidth = Gdx.graphics.getWidth() / tileScale;
        camera.position.x=tileLayer.getWidth()/2;
        camera.position.y=tileLayer.getHeight()/2;
        Logger.writeLogDisplayLog("LOG","Camera setup with Height:"+camera.viewportHeight+"  Width:"+camera.viewportWidth,this.getClass().getSimpleName());

        inputHandler.setView(camera,tileScale,renderer);
        ScreenUtils.clear(1, 0, 0, 1);

        multiplexer = new InputMultiplexer();
        hud = new Hud(multiplexer,batch,inputHandler);
        multiplexer.addProcessor(inputHandler);
        Gdx.input.setInputProcessor(multiplexer);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize (int width, int height) {
        camera.viewportHeight = Gdx.graphics.getHeight() / tileScale;
        camera.viewportWidth = Gdx.graphics.getWidth() / tileScale;
        camera.update();
        hud.resize(width,height);
        Logger.writeLogDisplayLog("LOG","Camera updated with Height:"+camera.viewportHeight+"  Width:"+camera.viewportWidth,this.getClass().getSimpleName());
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
        enemyPlayer.render(batch);
        batch.end();
        hud.render();
        finished();
    }
    @Override
    public void dispose(){
        batch.dispose();
        renderer.dispose();
        executor.shutdown();
    }
    public void finished(){
        if(enemyPlayer.getHealth()<=0) {
            executor.shutdown();
            game.setScreen(new EndScreen(DisplayConfig.WIN_TEXT));
            hide();
        }
        if(player.getHealth()<=0) {
            executor.shutdown();
            game.setScreen(new EndScreen(DisplayConfig.LOSS_TEXT)); 
            hide();
        }
    }
}
