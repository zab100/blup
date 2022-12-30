package com.zbt.shared.networking;

import java.io.Serializable;
import com.zbt.NetworkingRequest;
import com.zbt.shared.utils.Utils;

public class BluetoothDeviceMessage implements Serializable {
    private static final long serialVersionUID = Utils.stringToLong(BluetoothDeviceMessage.class.getName());

    private NetworkingRequest request;

    private Object[] additional;

    public BluetoothDeviceMessage(){

    }

    public BluetoothDeviceMessage(NetworkingRequest request, Object... additional){
        this.request = request;
        this.additional = additional;
    }

    public NetworkingRequest getRequest(){
        return request;
    }

    public Object[] getAdditional() {
        return additional;
    }
}
