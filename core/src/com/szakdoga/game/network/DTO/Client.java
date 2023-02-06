package com.szakdoga.game.network.DTO;

import com.szakdoga.game.network.FileServerHandler;
import com.szakdoga.game.network.GameServerHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class Client {
    private Socket socket;
    private String ip;
    private int port;
    private ExecutorService ex;

    public Client(String ip, int port, ExecutorService ex){
        this.ip = ip;
        this.port = port;
        this.ex=ex;
    }
    public void Start() throws IOException, InterruptedException {
        socket = new Socket(ip,port);
        new FileServerHandler(socket);
        new GameServerHandler(socket,ex);
    }

}
