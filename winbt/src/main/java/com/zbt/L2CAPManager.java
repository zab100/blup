package com.zbt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.bluetooth.L2CAPConnection;
import javax.microedition.io.Connector;

import com.intel.bluetooth.BluetoothConsts;
import com.zbt.shared.utils.Utils;

public class L2CAPManager {

    private static L2CAPManager instance;

    private L2CAPManager(){

    }

    public static L2CAPManager getInstance(){
        if(instance == null){
            instance = new L2CAPManager();
        }
        return instance;
    }

    public Map<String, L2CAPConnection> connections = new HashMap<>();

    public void createL2CAPConnection(String address, String psm) throws IOException {
        address = Utils.cleanAddress(address);
        if(!connections.containsKey(address)){
            // L2CAPConnection connection = (L2CAPConnection)Connector.open(BluetoothConsts.PROTOCOL_SCHEME_L2CAP + "://" + address + ":" + BluetoothConsts.L2CAP_PROTOCOL_UUID);
            L2CAPConnection connection = (L2CAPConnection)Connector.open(BluetoothConsts.PROTOCOL_SCHEME_L2CAP + "://" + address + ":" + Utils.cleanUUID(psm));
            System.out.println("Created l2cap connection " + address + " " + psm);
            connections.put(address, connection);
        }
    }

    public int getMaxTU(String address) throws IOException{
        return connections.get(address).getReceiveMTU();
    }

    public void sendBytes(String address, byte[] bytes) throws IOException{
        L2CAPConnection connection = connections.get(address);
        connection.send(bytes);
    }

    public byte[] receiveBytes(String address) throws IOException{
        L2CAPConnection connection = connections.get(address);
        byte[] bytes = new byte[connection.getReceiveMTU()];
        connection.receive(bytes);
        return bytes;
    }
}
