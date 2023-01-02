package com.zbt.shared.networking;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.zbt.shared.utils.AndroidConversions;
import com.zbt.shared.utils.Utils;

public class BluetoothServiceInfo implements Serializable{
    private static final long serialVersionUID = Utils.stringToLong(BluetoothServiceInfo.class.getName());

    private String address;
    private Set<String> uuids = new HashSet<>();

    public BluetoothServiceInfo(){

    }

    public BluetoothServiceInfo(String address){
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public Set<String> getUuids() {
        return uuids;
    }

    public void addUuid(String uuid) {
        if(!uuid.contains("-")){
            uuid = AndroidConversions.convertUUIDString(uuid);
        }
        uuids.add(uuid);
    }

    public void addAllUuids(BluetoothServiceInfo serviceInfo) {
        uuids.addAll(serviceInfo.getUuids());
    }

    @Override
    public String toString(){
        return address + ", " + uuids.size() + " uuids " + uuids.toString();
    }

    
}
