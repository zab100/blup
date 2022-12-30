/* This file was last modified by Zain B */

package com.android.bluetooth.windowsnativereplacements;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import com.zbt.NetworkingRequest;
import com.zbt.shared.networking.BluetoothDeviceMessage;
import com.zbt.shared.networking.BluetoothResponse;
import com.zbt.shared.networking.BluetoothSocketHeader;
import com.zbt.shared.networking.SocketType;
import com.zbt.shared.utils.ParamRunnable;

import android.util.Log;

public class WindowsCommunicator {
    private static String m_windowsServiceAddress;

    public static void setAddress(String windowsServiceAddress){
        m_windowsServiceAddress = windowsServiceAddress;
    }

    public static boolean isAddressSet(){
        return m_windowsServiceAddress != null && !m_windowsServiceAddress.isEmpty();
    }

    public static String getWindowsServiceAddress(){
        return m_windowsServiceAddress;
    }

    public static void sendToWindows(BluetoothDeviceMessage message){
        sendToWindows(message, null, null);
    }

    public static void sendToWindows(BluetoothDeviceMessage message, ParamRunnable<BluetoothResponse> responseRunnable){
        sendToWindows(message, responseRunnable, null);
    }

    public static void sendToWindows(BluetoothDeviceMessage message, ParamRunnable<BluetoothResponse> responseRunnable, ParamRunnable<Boolean> onDone){
        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    Socket clientSocket = null;
                    try {
                        clientSocket = new Socket("127.0.0.1", 5057);
                    } catch (UnknownHostException e){
                        Log.e("Modifyandroidsendtowindows", "cannot find localhost, falling back");
                        clientSocket = new Socket(m_windowsServiceAddress, 5057); 
                    }
                    Log.e("MODIFYANDROIDSYSTEM", "formulated message and sending to " + clientSocket.getInetAddress());
                    ObjectOutputStream outToWindows = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream inFromWindows = new ObjectInputStream(clientSocket.getInputStream());

                    try {
                        outToWindows.writeObject(message);
                        outToWindows.flush();

                        while(true) {
                            BluetoothResponse response = (BluetoothResponse) inFromWindows.readObject();

                            if(response.isSolo() || response.isDone()){
                                if(responseRunnable != null && response.isSolo()){
                                    responseRunnable.run(response);
                                }

                                if(onDone != null){
                                    onDone.run(true);
                                }
                                
                                break;
                            } else if (responseRunnable != null){
                                responseRunnable.run(response);
                            }
                            
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        if(onDone != null){
                            onDone.run(false);
                        }
                    }
                    clientSocket.close();
                } catch (Exception e){
                    if(onDone != null){
                        onDone.run(false);
                    }
                }
            }
        };

        t.start();
    }
}
