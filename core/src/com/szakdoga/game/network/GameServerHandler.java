package com.szakdoga.game.network;

import com.szakdoga.game.Logger;
import com.szakdoga.game.network.DTO.Preparator;
import org.datatransferobject.DTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.szakdoga.game.screens.GameScreen.enemyPlayer;
import static com.szakdoga.game.screens.GameScreen.player;


public class GameServerHandler implements Runnable{
    private static ObjectOutputStream objectOutputStream ;
    private static ObjectInputStream objectInputStream ;
    private List<DTO> DTOList= new ArrayList<>();
    private final Socket clientSocket;
    private DTO dtoIn;
    private String name;
    private static AtomicInteger id=new AtomicInteger(0);

    public GameServerHandler(String ip, int port,String name) throws IOException {
        this.clientSocket=new Socket();
        clientSocket.connect(new InetSocketAddress(ip, port), 10000);
        this.name=name;
        try {
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
            Logger.writeLog("error",e.getMessage(),this.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public void run() {
        //TODO alapból kapjon csak egy checket és csak azzután néze meg ehmaybe
            try {
                sendData();
                receiveData();
                if (id.get() < 0) {
                    clientSocket.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                Logger.writeLog("error",e.getMessage(),this.getClass().getSimpleName());
                System.exit(-1);
            }
    }

    protected void receiveData() throws IOException, ClassNotFoundException {
        Logger.displayLog("log","Currently waiting for data from server");
        DTOList=((ArrayList<DTO>) objectInputStream.readObject());
        Logger.displayLog("log","Received data from server");
        Logger.displayLog("log","This client's data:");
        Logger.displayLog("log","Enemy client's data:");
        id.set(DTOList.get(0).getId());
        player.exchangeData(DTOList.get(0));
        enemyPlayer.exchangeData(DTOList.get(1));
        Logger.displayLog("log","Succesfully received and exchanged data from server");
    }
        protected void sendData() throws IOException {
        Logger.displayLog("log","Sending data");
        objectOutputStream.writeObject(new DTO(Preparator.createUnitDTOListFromUnitList(player.getUnits()),
                                                Preparator.createTowerDTOListFromTowertList(player.getTowers()),
                                                Preparator.createPlayerDTOFromPlayer(player),id.get(),name));
        objectOutputStream.flush();
        Logger.displayLog("log","Data sent");
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
    public static int getId(){
        return id.get();
    }
}

