package com.szakdoga.game.config;

import com.badlogic.gdx.graphics.Color;

public class DisplayConfig {
    //Ingame Texts
    public static final String GAME_TITLE = "Tower Defence";
    public static final String WIN_TEXT = "Win";
    public static final String LOSS_TEXT = "Loss";
    public static final String START_TEXT = "Start";
    public static final String BACK_TEXT = "Back";
    public static final String OPTION_TEXT = "Option";
    public static final String EXIT_TEXT = "Exit";
    public static final String FULLSCREEN_ON_TEXT = "Fullscreen:ON";
    public static final String FULLSCREEN_OFF_TEXT = "Fullscreen:OFF";
    //Camera
    public static final float CAMERA_AREA_LIMIT = 10f;
    public static final int STANDARD_WINDOW_SIZE_WIDTH=1000;
    public static final int STANDARD_WINDOW_SIZE_HEIGHT=540;
    //Textures
    public static final String MAIN_MENU_BACKGROUND_TEXTURE = "menu/start_menu.png";
    public static final String TOWER_TEXTURE = "textures/tower.png";
    public static final String DRAGON_TEXTURE = "textures/dragon.png";
    public static final String CIRCLE_TEXTURE = "textures/circle.png";
    public static final String PROJECTILE_TEXTURE = "textures/projectile.png";
    public static final String COIN_TEXTURE = "textures/coin.png";
    public static final String HEALTH_TEXTURE = "textures/health.png";
    public static final String ENEMY_HEALTH_TEXTURE = "textures/enemyhealth.png";
    public static final String PIKE_UNIT_TEXTURE = "textures/pikeunit.png";
    public static final String KNIGHT_UNIT_TEXTURE = "textures/knightunit.png";
    public static final String WIZARD_UNIT_TEXTURE = "textures/wizardunit.png";
    public static final String ARCHER_TOWER_TEXTURE = "textures/archertower.png";
    public static final String CROSS_BOW_TOWER_TEXTURE = "textures/crossbowtower.png";
    public static final String WIZARD_TOWER_TEXTURE = "textures/wizardtower.png";
    //Colors
    public static final Color RED_COLOR = Color.RED;
    public static final Color BLUE_COLOR = Color.BLUE;
    public static final Color CRIMSON_COLOR = Color.valueOf("#B21031");
    //Fonts
    public static final String STANDARD_FONT = "fonts/Kanit-Black.ttf";
    //Standard fix ratio
    public static final double HUD_TABLE_ADJUSTMENT  = 0.15;
    public static final double MAIN_MENU_TABLE_ADJUSTMENT = 0.25;
    //Text size
    public static final int STANDART_FONT_SIZE = 75;
    public static final int MEDIUM_FONT_SIZE = 100;
    public static final int BIG_FONT_SIZE = 120;

}
