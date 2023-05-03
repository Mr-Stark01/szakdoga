package com.szakdoga.game.network;

import com.badlogic.gdx.Gdx;
import com.szakdoga.game.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class FileServerHandler {
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;
    private Socket socket;
    public FileServerHandler(String ip, int port){
        try{
            this.socket=new Socket();
            socket.connect(new InetSocketAddress(ip, port), 1000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            receiveFile(Gdx.files.internal("maps/defmap.tmx").path());
            socket.close();
        }catch (IOException e){
            Logger.displayError("error",e.getMessage());
    }
    }
    private static void receiveFile(String path) throws IOException{
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
