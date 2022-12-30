package com.zbt.shared.networking;

import java.io.Serializable;

import com.zbt.shared.utils.Utils;

public class BluetoothDeviceConnected implements Serializable{
    private static final long serialVersionUID = Utils.stringToLong(BluetoothDeviceConnected.class.getName());

    private final String address;

    public BluetoothDeviceConnected(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
