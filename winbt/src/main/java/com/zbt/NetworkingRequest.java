package com.zbt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.UUID;

import com.intel.bluetooth.BluetoothConsts;
import com.zbt.shared.networking.BluetoothResponse;
import com.zbt.shared.networking.BluetoothServerSocketHeader;
import com.zbt.shared.networking.BluetoothServiceInfo;
import com.zbt.shared.networking.BluetoothSocketHeader;
import com.zbt.shared.utils.Utils;

public enum NetworkingRequest {
    REQUEST_PAIRED_DEVICES ((outputStream, inputStream, parameters) -> {
        Object lock = new Object();

        try {
            BluetoothManager.getInstance().getPairedDevices((device) -> {
                BluetoothResponse pairedDeviceResponse = new BluetoothResponse();
                pairedDeviceResponse.setPairedDevice(device);

                synchronized(lock){
                    try {
                        outputStream.writeObject(pairedDeviceResponse);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        // e.printStackTrace();
                        // connection was closed: do nothing
                    }
                }
            });
        } catch (BluetoothStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //getPairedDevices blocks until discovery finishes
        BluetoothResponse done = new BluetoothResponse();
        done.setDone(true);
        try {
            outputStream.writeObject(done);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }),
    CANCEL_PAIRED_DEVICE_SEARCH((outputStream, inputStream, parameters) -> {
        try {
            BluetoothManager.getInstance().cancelPairedDevicesSearch();
        } catch (BluetoothStateException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            outputStream.writeObject(new BluetoothResponse(true));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }),

    REQUEST_BLUETOOTH_SOCKET_CONNECTION ((outputStream, inputStream, parameters) -> {
        try {
            BluetoothSocketHeader socketHeader = (BluetoothSocketHeader) inputStream.readObject();

            System.out.println("android wants to connect to " + socketHeader.getAddress());

            try {
                BluetoothManager.getInstance().createRFCOMMConnection(socketHeader.getAddress(), socketHeader.getUuid(), outputStream, inputStream); //blocks
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
            
        } catch (ClassNotFoundException | IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }),
    @Deprecated //currently broken
    REQUEST_BLUETOOTH_SERVER_SOCKET_CONNECTION ((outputStream, inputStream, parameters) -> {
        try {
            BluetoothServerSocketHeader serverSocketHeader = (BluetoothServerSocketHeader) inputStream.readObject();

            System.out.println("uuid " + serverSocketHeader.getUuid());

            String url = "";

            //TODO: finish
            // if(serverSocketHeader.getUuid().length == 0 || Utils.allBytes0(serverSocketHeader.getUuid())){
            //     url = serverSocketHeader.getSocketType().getPrefix() + "://localhost:" + (serverSocketHeader.getChannel() == -2 ? serverSocketHeader.getPort() : serverSocketHeader.getChannel()) + ";name=Windows Subsystem for Android";
            // } else {
            //     UUID serverSocketUUID = new UUID(ByteBuffer.wrap(serverSocketHeader.getUuid()).getLong());
            //     url = serverSocketHeader.getSocketType().getPrefix() + "://localhost:" + serverSocketUUID.toString() + ";name=Windows Subsystem for Android";
            // }
            
            try {
                BluetoothManager.getInstance().createRFCOMMServerConnection(serverSocketHeader.getUuid(), serverSocketHeader.getIp(), serverSocketHeader.getPort());
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }),

    // REQUEST_BLUETOOTH_ADAPTER_NAME,
    // REQUEST_BLUETOOTH_ADAPTER_ADDRESS,
    // REQUEST_BLUETOOTH_ADAPTER_UUIDS,
    // REQUEST_BLUETOOTH_ADAPTER_CLASS_OF_DEVICE,
    // REQUEST_BLUETOOTH_ADAPTER_TYPE_OF_DEVICE,

    REQUEST_BLUETOOTH_DEVICE_SERVICE_UUID ((outputStream, inputStream, parameters) -> {
        try {
            UUID converteUuid = new UUID(((String)parameters[1]).replace("-", ""), false);
            BluetoothServiceInfo servicesFound = BluetoothManager.getInstance().getServicesForDeviceWithoutFail((String)parameters[0], converteUuid);
            BluetoothResponse response = new BluetoothResponse();
            response.setDeviceServices(servicesFound);
            response.setSolo(true);
            outputStream.writeObject(response);
        } catch (BluetoothStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }),
    REQUEST_BLUETOOTH_DEVICE_ALL_BASIC_UUIDS ((outputStream, inputStream, parameters) -> {
        try {
            BluetoothManager manager = BluetoothManager.getInstance();

            BluetoothServiceInfo serviceForDevice = new BluetoothServiceInfo();

            // only RFCOMM is supported by Windows
            // serviceForDevice.addAllUuids(manager.getServicesForDeviceWithoutFail((String)parameters[0], BluetoothConsts.L2CAP_PROTOCOL_UUID));
            serviceForDevice.addAllUuids(manager.getServicesForDeviceWithoutFail((String)parameters[0], BluetoothConsts.RFCOMM_PROTOCOL_UUID));
            // serviceForDevice.addAllUuids(manager.getServicesForDeviceWithoutFail((String)parameters[0], BluetoothConsts.OBEX_PROTOCOL_UUID));
            // serviceForDevice.addAllUuids(manager.getServicesForDeviceWithoutFail((String)parameters[0], BluetoothConsts.SERIAL_PORT_UUID));

            BluetoothResponse response = new BluetoothResponse();
            response.setDeviceServices(serviceForDevice);
            response.setSolo(true);
            outputStream.writeObject(response);
        } catch (BluetoothStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }),

    REQUEST_BLUETOOTH_L2CAP_CONNECTION ((outputStream, inputStream, parameters) -> {
        try {
            L2CAPManager.getInstance().createL2CAPConnection((String)parameters[0], (String)parameters[1]);

            BluetoothResponse response = new BluetoothResponse();
            response.setDone(true);
            outputStream.writeObject(response);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }),
    REQUEST_BLUETOOTH_HID_CONNECTION ((outputStream, inputStream, parameters) -> {
        // throw new UnsupportedOperationException("L2CAP not supported on winsock");
        // try {
        //     String address = Utils.cleanAddress((String)parameters[0]);

        //     L2CAPManager.getInstance().createL2CAPConnection(address, BluetoothManager.getInstance().getL2CAPPSM(KnownUUIDs.HID, address));

        //     BluetoothResponse response = new BluetoothResponse();
        //     response.setDone(true);
        //     outputStream.writeObject(response);
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
    }),
    REQUEST_BLUETOOTH_L2CAP_SEND ((outputStream, inputStream, parameters) -> {
        try {
            L2CAPManager.getInstance().sendBytes((String)parameters[0], (byte[])parameters[1]);
            BluetoothResponse response = new BluetoothResponse();
            response.setDone(true);
            outputStream.writeObject(response);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }),
    REQUEST_BLUETOOTH_L2CAP_READ((outputStream, inputStream, parameters) -> {
        try {
            byte[] bytes = L2CAPManager.getInstance().receiveBytes((String)parameters[0]);

            BluetoothResponse response = new BluetoothResponse();
            response.setL2capBytes(bytes);
            response.setSolo(true);
            outputStream.writeObject(response);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }),
    REQUEST_BLUETOOTH_L2CAP_MAXTU((outputStream, inputStream, parameters) -> {
        try {
            int maxTU = L2CAPManager.getInstance().getMaxTU((String)parameters[0]);

            BluetoothResponse response = new BluetoothResponse();
            response.setMaxTU(maxTU);
            response.setSolo(true);
            outputStream.writeObject(response);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    });

    @FunctionalInterface
    public static interface RunnerWithParameters {
        public void run(ObjectOutputStream outputStream, ObjectInputStream inputStream, Object... parameters);
    }

    private RunnerWithParameters event;
    private NetworkingRequest(RunnerWithParameters event){
        this.event = event;
    }

    public void execute(ObjectOutputStream outputStream, ObjectInputStream inputStream, Object... parameters){
        event.run(outputStream, inputStream, parameters);
    }
}