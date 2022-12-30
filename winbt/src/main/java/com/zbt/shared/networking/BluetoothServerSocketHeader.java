package com.zbt.shared.networking;

import java.io.Serializable;

import com.zbt.shared.utils.Utils;

public class BluetoothServerSocketHeader implements Serializable{
    private static final long serialVersionUID = Utils.stringToLong(BluetoothServerSocketHeader.class.getName());

    private SocketType socketType;
    private final String ip;
    private final int port;
    private final String uuid;
    private final int channel;

    public BluetoothServerSocketHeader(SocketType socketType, String ip, int port, String uuid, int channel){
        this.port = port;
        this.uuid = uuid;
        this.channel = channel;
        this.socketType = socketType;
        this.ip = ip;
    }

    public int getPort() {
        return port;
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

    public String getIp() {
        return ip;
    }
}
