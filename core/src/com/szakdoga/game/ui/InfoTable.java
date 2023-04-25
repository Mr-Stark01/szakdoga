package com.szakdoga.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.szakdoga.game.FontCreator;
import com.szakdoga.game.towers.Tower;
import com.szakdoga.game.units.Unit;

import static com.szakdoga.game.TowerDefence.UIscale;


public class InfoTable {
    private static Table table;
    private static BitmapFont font=FontCreator.createFont((int) (25*UIscale));
    private static Label.LabelStyle labelStyle=new Label.LabelStyle(font, Color.WHITE);
    public static Table InfoTableFactory(float damage,int price,int range,float attackTime,String typeName){
        table = new Table();
        table.row().height(20*UIscale);
        table.add(new Label("Name:",labelStyle));
        table.add(new Label(typeName,labelStyle));
        table.row().height(20*UIscale);
        table.add(new Label("Damage:",labelStyle));
        table.add(new Label(String.valueOf(damage),labelStyle));
        table.row().height(20*UIscale);
        table.add(new Label("Price:",labelStyle));
        table.add(new Label(String.valueOf(price),labelStyle));
        table.row().height(20*UIscale);
        table.add(new Label("Range:",labelStyle));
        table.add(new Label(String.valueOf(range),labelStyle));
        table.row().height(20*UIscale);
        table.add(new Label("AttackTime:",labelStyle));
        table.add(new Label(String.valueOf(attackTime),labelStyle));
        return table;
    }
    public static Table InfoTableFactory(float health,float speed,float damage,
                                         int price,String typeName){
        table = new Table();
        table.row().height(20*UIscale);
        table.add(new Label("Name:",labelStyle));
        table.add(new Label(typeName,labelStyle));
        table.row().height(20*UIscale);
        table.add(new Label("Health:",labelStyle));
        table.add(new Label(String.valueOf(health),labelStyle));
        table.row().height(20*UIscale);
        table.add(new Label("Speed:",labelStyle));
        table.add(new Label(String.valueOf(speed),labelStyle));
        table.row().height(20*UIscale);
        table.add(new Label("Damage:",labelStyle));
        table.add(new Label(String.valueOf(damage),labelStyle));
        table.row().height(20*UIscale);
        table.add(new Label("Price:",labelStyle));
        table.add(new Label(String.valueOf(price),labelStyle));
        return table;
    }


}
