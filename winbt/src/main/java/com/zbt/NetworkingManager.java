package com.zbt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import com.zbt.shared.networking.BluetoothDeviceMessage;

public class NetworkingManager {
    private ServerSocket serverSocket;

    private Thread mainNetworkingThread;

    private final Set<Socket> openClients = Collections.synchronizedSet(new HashSet<>());

    public NetworkingManager(){
        try {
            serverSocket = new ServerSocket(5057);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void beginLoop(){
        mainNetworkingThread = new Thread(){
            private boolean interrupted = false;

            @Override
            public void interrupt() {
                super.interrupt();
                interrupted = true;
            }

            @Override
            public void run(){
                while(!interrupted){
                    try {
                        Socket client = serverSocket.accept();
                        openClients.add(client);
                        if(!interrupted){
                            new Thread(() -> {
                                try {
                                    ObjectOutputStream responseWriter = new ObjectOutputStream(client.getOutputStream());
                                    ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                                    BluetoothDeviceMessage request = (BluetoothDeviceMessage) ois.readObject();
                                    request.getRequest().execute(responseWriter, ois, request.getAdditional());
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                try {
                                    client.close();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                openClients.remove(client);
                            }).start();
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        mainNetworkingThread.start();
    }

    public void closeAllConnections() {
        mainNetworkingThread.interrupt();
        try {
            serverSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Set<Socket> clients = new HashSet<>(openClients);
        for(Socket client : clients){
            try {
                client.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
