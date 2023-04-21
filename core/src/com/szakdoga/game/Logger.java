package com.szakdoga.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Logger {
    static Date now = new Date();
    static String format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss", Locale.ENGLISH).format(now);
    private static FileHandle logFile = Gdx.files.local("log/"+format1+"log.txt");
    public static void writeLog(String lvl,String log,String clazz){
        log = new Date().getTime() + "\t "+ lvl +" \t "+clazz+ "\t"+ log;
        logFile.writeString(log, true);
        logFile.writeString("\n", true);
    }
    public static void writeLogDisplayLog(String lvl,String log,String clazz){
        lvl=lvl.toLowerCase().strip();
        log = new Date().getTime() + "\t "+ lvl +" \t"+clazz+"\t"+ log;
        logFile.writeString(log, true);
        logFile.writeString("\n", true);
        switch (lvl){
            case "log":
                displayLog("STD",log);
                break;
            case "debug":
                displayDebug("STD",log);
                break;
            case "error":
                displayError("STD",log);
                break;
        }
    }
    public static void displayLog(String tag,String message){
        Gdx.app.log(tag,message);
    }
    public static void displayError(String tag,String message){
        Gdx.app.error(tag,message);
    }
    public static void displayDebug(String tag,String message){
        Gdx.app.debug(tag,message);
    }
}
