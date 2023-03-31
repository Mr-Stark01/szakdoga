package com.szakdoga.game.network;

import com.szakdoga.game.Player;
import com.szakdoga.game.network.DTO.Preparator;
import com.szakdoga.game.screens.GameScreen;
import com.szakdoga.game.towers.Tower;
import org.datatransferobject.DTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.szakdoga.game.screens.GameScreen.enemyPlayer;


public class GameServerHandler implements Runnable{
    private static ObjectOutputStream objectOutputStream = null;
    private static ObjectInputStream objectInputStream = null;
    private List<DTO> DTOList= new ArrayList<DTO>();
    private final Socket clientSocket;
    private DTO dtoIn;
    private DTO dtoOut;
    private Player player;
    private int id = 0; // Ez nem egy jó megoldás

    public GameServerHandler(String ip, int port) throws IOException {
        this.clientSocket=new Socket(ip,port);
        this.player= GameScreen.player;
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
                e.printStackTrace();
            }
            try {
                receiveData();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    protected void refreshDtoOut(){
        dtoOut=new DTO(Preparator.createUnitDTOListFromUnitList(player.getUnits()),
                Preparator.createTowerDTOListFromTowertList(player.getTowers()),
                Preparator.createPlayerDTOFromPlayer(player),id);
    }
    protected void receiveData() throws IOException, ClassNotFoundException {
        System.out.println(objectInputStream.readObject());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(objectInputStream.readObject());
        /*System.out.println("received data 1");
        DTOList.clear();
        DTOList.add((DTO) objectInputStream.readObject());
        System.out.println("received data 2");
        DTOList.add((DTO) objectInputStream.readObject());
        player.exchangeData(DTOList.get(0));
        enemyPlayer.exchangeData(DTOList.get(1));
        System.out.println("received data 3");*/
    }
        protected void sendData() throws IOException{
        System.out.println("send data 1");
        //if(dtoOut.getUnitDTOs().size()>0) {
        //    System.out.println(dtoOut.getUnitDTOs().get(0).getX());
        //}
        objectOutputStream.writeObject("client");
        System.out.println("send data 2");
        objectOutputStream.flush();
        System.out.println("send data 3");
    }
    public DTO getDtoIn() {
        return dtoIn;
    }

    public void setDtoIn(DTO dtoIn) {
        this.dtoIn = dtoIn;
    }

    public List<DTO> getDtoOut() {
        return DTOList;
    }
}

