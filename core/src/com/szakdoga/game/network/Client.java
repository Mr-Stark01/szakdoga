package com.szakdoga.game.network;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

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