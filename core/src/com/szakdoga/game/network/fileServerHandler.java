package com.szakdoga.game.network;

import com.badlogic.gdx.Gdx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class fileServerHandler {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    public fileServerHandler(int port){
        try{
            Socket socket = new Socket("localhost",port);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            receiveFile(Gdx.files.internal("maps/Base.tmx").path());

            dataInputStream.close();
            dataOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void receiveFile(String path) throws Exception{
        int bytes;
        FileOutputStream fileOutputStream = new FileOutputStream(path);

        long size = dataInputStream.readLong();
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;
        }
        fileOutputStream.close();
    }
}
