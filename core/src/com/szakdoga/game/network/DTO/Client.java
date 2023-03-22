package com.szakdoga.game.network.DTO;

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
        this.ex= Executors.newScheduledThreadPool(10);
    }
    public void run() {
        try {
            ex.scheduleAtFixedRate(new GameServerHandler(ip,port,ex),0,100, TimeUnit.MILLISECONDS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}