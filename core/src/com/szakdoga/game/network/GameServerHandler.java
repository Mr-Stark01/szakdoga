package com.szakdoga.game.network;

import com.szakdoga.game.network.DTO.DTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class GameServerHandler implements Callable<Integer> {
    private final Socket clientSocket;
    private static ObjectOutputStream objectOutputStream = null;
    private static ObjectInputStream objectInputStream = null;
    private DTO dtoIn;
    private DTO dtoOut;
    private ExecutorService ex;
    public GameServerHandler(Socket socket, ExecutorService ex) throws InterruptedException{
        this.clientSocket=socket;
        this.ex=ex;
        try {
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    @Override
    public Integer call() throws Exception {
        while(true){
            receiveData();
            sendData();
        }
    }
    public void receiveData() throws IOException, ClassNotFoundException {
        this.dtoIn = (DTO) objectInputStream.readObject();
    }
    public void sendData() throws IOException{
        objectOutputStream.writeObject(dtoOut);
        objectOutputStream.flush();
    }
    public DTO getDtoIn() {
        return dtoIn;
    }
    public DTO getDtoOut() {
        return dtoOut;
    }
    public void setDtoOut(DTO dtoOut) {
        this.dtoOut = dtoOut;
    }
    public void setDtoIn(DTO dtoIn) {
        this.dtoIn = dtoIn;
    }
}

