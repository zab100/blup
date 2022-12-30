package com.zbt.shared.networking;

import java.io.Serializable;

import com.zbt.shared.utils.Utils;

public class BluetoothSocketHeader implements Serializable{
    private static final long serialVersionUID = Utils.stringToLong(BluetoothSocketHeader.class.getName());

    private final SocketType socketType;
    private final String address;
    private final String uuid;
    private final int channel;

    public BluetoothSocketHeader(SocketType socketType, String address, String uuid, int channel){
        this.socketType = socketType;
        this.address = address;
        this.uuid = uuid;
        this.channel = channel;
    }

    public String getAddress() {
        return address;
    }

    public String getUuid() {
        return uuid;
    }

    public int getChannel() {
        return channel;
    }

    public SocketType getSocketType() {
        return socketType;
    }

    
}
