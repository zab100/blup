package com.zbt.sdp;

import java.util.Enumeration;

import javax.bluetooth.DataElement;
import javax.bluetooth.ServiceRecord;

@Deprecated // L2CAP is not supported by winsock
public class L2CAPSDP extends SDPBase {

    public L2CAPSDP(ServiceRecord record) {
        super(record);
        //TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
    public short getL2CAPPSM(){
        DataElement protocolInfo = record.getAttributeValue(4);
        Enumeration<DataElement> protocolInfoDataSeq = (Enumeration<DataElement>) protocolInfo.getValue();
        Enumeration<DataElement> l2capdataseq = (Enumeration<DataElement>) protocolInfoDataSeq.nextElement().getValue();
        l2capdataseq.nextElement(); //l2cap uuid
        return Short.valueOf((short) l2capdataseq.nextElement().getLong());
    }
}
