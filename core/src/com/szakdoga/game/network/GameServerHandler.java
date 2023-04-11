package com.szakdoga.game.network;

import com.szakdoga.game.network.DTO.Preparator;
import org.datatransferobject.DTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.szakdoga.game.screens.GameScreen.enemyPlayer;
import static com.szakdoga.game.screens.GameScreen.player;


public class GameServerHandler implements Runnable{
    private static ObjectOutputStream objectOutputStream ;
    private static ObjectInputStream objectInputStream ;
    private List<DTO> DTOList= new ArrayList<>();
    private final Socket clientSocket;
    private DTO dtoIn;
    private int id = 0; // Ez nem egy jó megoldás

    public GameServerHandler(String ip, int port) throws IOException {
        this.clientSocket=new Socket(ip,port);
        try {
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        //TODO alapból kapjon csak egy checket és csak azzután néze meg ehmaybe
        try {
            sendData();
            receiveData();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    protected void receiveData() throws IOException, ClassNotFoundException {
        System.out.println("receive 1");

        DTOList=((ArrayList<DTO>) objectInputStream.readObject());
        if(id==0) {
            id = DTOList.get(0).getId();
        }
        player.exchangeData(DTOList.get(0));
        System.out.println("enemy");
        enemyPlayer.exchangeData(DTOList.get(1));
        System.out.println("asd");
        System.out.println("Time"+new Date().getTime());
        System.out.println("Player id:"+DTOList.get(0).getUnitDTOs().size());
        System.out.println("Enemy id:"+DTOList.get(1).getUnitDTOs().size());
        System.out.println("Player date:"+DTOList.get(0).getDateOfCreation().getTime());
        System.out.println("Enemy date:"+DTOList.get(1).getDateOfCreation().getTime());
        if(player.getUnits().size()>0){
            System.out.println("own: \t"+
            player.getUnits().get(0).getId()
                    +"\t"+
            DTOList.get(0).getUnitDTOs().get(0).getId());
        }
        if(enemyPlayer.getUnits().size()>0){
            System.out.println("ENEMY: \t"+
                    enemyPlayer.getUnits().get(0).getId()
                            +"\t"+
                            DTOList.get(1).getUnitDTOs().get(0).getId());
        }
        System.out.println("receive 2");
    }
        protected void sendData() throws IOException {
        System.out.println("send data1");
        System.out.println(id);
        objectOutputStream.writeObject(new DTO(Preparator.createUnitDTOListFromUnitList(player.getUnits()),
                                                Preparator.createTowerDTOListFromTowertList(player.getTowers()),
                                                Preparator.createPlayerDTOFromPlayer(player),id));
        objectOutputStream.flush();
        System.out.println("send data2");
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

