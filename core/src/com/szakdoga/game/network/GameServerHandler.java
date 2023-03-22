package com.szakdoga.game.network;

import com.szakdoga.game.network.DTO.DTO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class GameServerHandler implements Runnable{
    private static ObjectOutputStream objectOutputStream = null;
    private static ObjectInputStream objectInputStream = null;
    private final Socket clientSocket;
    private DTO dtoIn;
    private DTO dtoOut;
    private ExecutorService ex;
    public GameServerHandler(String ip,int port, ExecutorService ex) throws IOException {
        this.clientSocket=new Socket(ip,port);
        this.ex=ex;
        try {
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(true){
            try {
                receiveData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                sendData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void receiveData() throws IOException, ClassNotFoundException {
       Date date = (Date) objectInputStream.readObject(); // csinálni egy küllön jar file hogy ne legyen duplikálva szerver kliens oldalt
        System.out.println("Received data:\t"+date.getTime());
    }
    public void sendData() throws IOException{
        objectOutputStream.writeObject(new Date());
        objectOutputStream.flush();
    }
    public DTO getDtoIn() {
        return dtoIn;
    }

    public void setDtoIn(DTO dtoIn) {
        this.dtoIn = dtoIn;
    }

    public DTO getDtoOut() {
        return dtoOut;
    }

    public void setDtoOut(DTO dtoOut) {
        this.dtoOut = dtoOut;
    }
}

