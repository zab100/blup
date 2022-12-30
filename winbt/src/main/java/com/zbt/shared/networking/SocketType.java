package com.zbt.shared.networking;

@Deprecated //likely no longer needed
//socket type should be requested from android side from now on using bluetoothdevicemessage
public enum SocketType{
    RFCOMM,
    L2CAP,
    OBEX,
    UNKNOWN;
}
