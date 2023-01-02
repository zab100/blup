package com.zbt.shared.networking;

import java.io.Serializable;
import com.zbt.shared.utils.Utils;

public class BluetoothDeviceInfo implements Serializable{
    private static final long serialVersionUID = Utils.stringToLong(BluetoothDeviceInfo.class.getName());

    private String address;
    private String name;
    private int majorClassOfDevice;
    private int minorClassOfDevice;
    private int serviceOfDevice;

    private BluetoothServiceInfo services;

    public BluetoothDeviceInfo(){
        
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
        if(name == null){
            name = address;
        }
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getMajorClassOfDevice() {
        return majorClassOfDevice;
    }
    public void setMajorClassOfDevice(int majorClassOfDevice) {
        this.majorClassOfDevice = majorClassOfDevice;
    }
    public int getMinorClassOfDevice() {
        return minorClassOfDevice;
    }
    public void setMinorClassOfDevice(int minorClassOfDevice) {
        this.minorClassOfDevice = minorClassOfDevice;
    }
    
    public int getServiceOfDevice() {
        return serviceOfDevice;
    }

    public void setServiceOfDevice(int serviceOfDevice) {
        this.serviceOfDevice = serviceOfDevice;
    }

    public BluetoothServiceInfo getServices() {
        return services;
    }

    public void setServices(BluetoothServiceInfo services) {
        this.services = services;
    }

    @Override
    public String toString(){
        return "Bluetooth Device " + address + ": " + name; 
    }
}
