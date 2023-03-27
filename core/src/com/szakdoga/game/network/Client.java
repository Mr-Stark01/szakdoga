package com.szakdoga.game.network;

import com.szakdoga.game.network.FileServerHandler;
import com.szakdoga.game.network.GameServerHandler;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

public class Client implements Runnable{
    private Socket socket;
    private String ip;
    private int port;
    private ScheduledExecutorService ex;

    public Client(String ip, int port, ExecutorService ex) {
        this.ip = ip;
        this.port = port;
    }
    public void run() {
        /*try {

            while (true){
                //gameServerHandler.setDtoOut();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

    }

}