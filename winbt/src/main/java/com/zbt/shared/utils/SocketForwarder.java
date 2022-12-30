package com.zbt.shared.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class SocketForwarder extends Thread{
    private final OutputStream outputStream;
    private final InputStream inputStream;

    private boolean snooping = false;

    /**
     * Forwards inputStream to outputStream until one is closed
     * @param outputStream
     * @param inputStream
     */
    public SocketForwarder(OutputStream outputStream, InputStream inputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    public void setSnooping(boolean snoop){
        this.snooping = snoop;
    }

    @Override
    public void run(){
        try {
            byte[] available = new byte[1024]; //Bluetooth MTU is usually pretty low
            int lenRead;
            while((lenRead = inputStream.read(available)) != -1){ 
                if(snooping){
                    System.out.println("copying bytes" + Arrays.toString(available));
                }
                outputStream.write(available, 0, lenRead);
                outputStream.flush();
                System.out.println("bytes copied" + lenRead);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
