package com.zbt.shared.networking;

import java.io.Serializable;
import com.zbt.shared.utils.Utils;

public class BluetoothResponse implements Serializable {
    private static final long serialVersionUID = Utils.stringToLong(BluetoothResponse.class.getName());

    private BluetoothDeviceInfo pairedDevice;
    private BluetoothServiceInfo deviceServices;
    private String adapterName;
    private String adapterAddress;
    private String[] adapterUUIDs;
    private byte[] l2capBytes;
    private int maxTU;

    private boolean solo = false;
    private boolean done = false;

    public BluetoothResponse(){

    }

    public BluetoothResponse(boolean solo){
        this.solo = true;
    }

    public BluetoothDeviceInfo getPairedDevice() {
        return pairedDevice;
    }

    public void setPairedDevice(BluetoothDeviceInfo pairedDevice) {
        this.pairedDevice = pairedDevice;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public void setAdapterName(String adapterName) {
        this.adapterName = adapterName;
    }

    public String getAdapterAddress() {
        return adapterAddress;
    }

    public void setAdapterAddress(String adapterAddress) {
        this.adapterAddress = adapterAddress;
    }

    public String[] getAdapterUUIDs() {
        return adapterUUIDs;
    }

    public void setAdapterUUIDs(String... adapterUUIDs) {
        this.adapterUUIDs = adapterUUIDs;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isSolo() {
        return solo;
    }

    public void setSolo(boolean solo) {
        this.solo = solo;
    }

    public BluetoothServiceInfo getDeviceServices() {
        return deviceServices;
    }

    public void setDeviceServices(BluetoothServiceInfo deviceServices) {
        this.deviceServices = deviceServices;
    }

    public byte[] getL2capBytes() {
        return l2capBytes;
    }

    public void setL2capBytes(byte[] l2capBytes) {
        this.l2capBytes = l2capBytes;
    }

    public int getMaxTU() {
        return maxTU;
    }

    public void setMaxTU(int maxTU) {
        this.maxTU = maxTU;
    }

    
}
