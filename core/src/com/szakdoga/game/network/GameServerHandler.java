package com.szakdoga.game.network;

import com.szakdoga.game.Player;
import com.szakdoga.game.network.DTO.Preparator;
import org.datatransferobject.DTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class GameServerHandler implements Runnable{
    private static ObjectOutputStream objectOutputStream = null;
    private static ObjectInputStream objectInputStream = null;
    private final Socket clientSocket;
    private DTO dtoIn;
    private DTO dtoOut;
    private Player player;
    public GameServerHandler(String ip, int port, Player player) throws IOException {
        this.clientSocket=new Socket(ip,port);
        this.player=player;
        try {
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        refreshDtoOut();//TODO alapból kapjon csak egy checket és csak azzután néze meg ehmaybe
            try {
                sendData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                receiveData();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }

    protected void refreshDtoOut(){
        dtoOut=new DTO(Preparator.createUnitDTOListFromUnitList(player.getUnits()),Preparator.createTowerDTOListFromTowertList(player.getTowers()),Preparator.createPlayerDTOFromPlayer(player));
    }
    protected void receiveData() throws IOException, ClassNotFoundException {
        dtoIn = (DTO) objectInputStream.readObject();
        dtoIn = (DTO) objectInputStream.readObject();
    }
    protected void sendData() throws IOException{
        objectOutputStream.writeObject(dtoOut);
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

}

