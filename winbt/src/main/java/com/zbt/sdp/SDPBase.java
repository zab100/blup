package com.zbt.sdp;

import java.util.Enumeration;

import javax.bluetooth.DataElement;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

import com.zbt.shared.utils.Utils;

public abstract class SDPBase {
    public final String serviceType; //attribute 0x0001
    
    public final ServiceRecord record;

    public SDPBase(ServiceRecord record) {
        this.record = record;

        DataElement serviceType = record.getAttributeValue(1);
        @SuppressWarnings("unchecked")
        Enumeration<DataElement> serviceTypeContainer = (Enumeration<DataElement>) serviceType.getValue();
        UUID service = (UUID) serviceTypeContainer.nextElement().getValue();
        System.out.println("Got uuid in sdpbase " + service);
        this.serviceType = Utils.cleanUUID(service.toString());
    }
}
