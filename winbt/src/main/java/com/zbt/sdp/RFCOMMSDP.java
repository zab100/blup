package com.zbt.sdp;

import java.util.Enumeration;

import javax.bluetooth.DataElement;
import javax.bluetooth.ServiceRecord;

public class RFCOMMSDP extends SDPBase{

    public RFCOMMSDP(ServiceRecord record) {
        super(record);
        //TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
    public byte getChannel(){
        DataElement protocolInfo = record.getAttributeValue(4);
        Enumeration<DataElement> protocolInfoDataSeq = (Enumeration<DataElement>) protocolInfo.getValue();
        protocolInfoDataSeq.nextElement(); //l2cap dataseq
        Enumeration<DataElement> rfcommDataSeq = (Enumeration<DataElement>) protocolInfoDataSeq.nextElement().getValue();
        rfcommDataSeq.nextElement(); //rfcomm uuid
        return Byte.valueOf((byte)rfcommDataSeq.nextElement().getLong());
    }
}
