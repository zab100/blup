package com.zbt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import com.intel.bluetooth.BlueCoveConfigProperties;
import com.intel.bluetooth.BlueCoveImpl;
import com.intel.bluetooth.BluetoothConsts;
import com.zbt.shared.networking.BluetoothDeviceConnected;
import com.zbt.shared.networking.BluetoothDeviceInfo;
import com.zbt.shared.networking.BluetoothServiceInfo;
import com.zbt.shared.utils.ParamRunnable;
import com.zbt.shared.utils.SocketForwarder;
import com.zbt.shared.utils.Utils;

public class BluetoothManager {
    private final Object synchronizeBluetoothDeviceDiscovery = new Object();
    private final Object synchronizeBluetoothServiceDiscovery = new Object();
    private final LocalDevice device;

    private final Map<String, RemoteDevice> discoveredDevices = new HashMap<>();
    private ParamRunnable<BluetoothDeviceInfo> onDeviceFound;
    private final Map<Integer, ServiceRecord[]> services = new HashMap<>();

    // private final Map<String, List<SDPBase>> sdpFound = new HashMap<>();

    private DiscoveryListener listener = new DiscoveryListener() {
        @Override
        public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
            discoveredDevices.put(btDevice.getBluetoothAddress(), btDevice);

            BluetoothDeviceInfo info = new BluetoothDeviceInfo();
            info.setAddress(btDevice.getBluetoothAddress());
            try {
                info.setName(btDevice.getFriendlyName(false));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            info.setMajorClassOfDevice(cod.getMajorDeviceClass());
            info.setMinorClassOfDevice(cod.getMinorDeviceClass());
            info.setServiceOfDevice(cod.getServiceClasses());

            // info.setServices(getServicesForDeviceWithoutFail(btDevice, BluetoothConsts.RFCOMM_PROTOCOL_UUID));

            onDeviceFound.run(info);
        }

        @Override
        public void inquiryCompleted(int discType) {
            synchronized(synchronizeBluetoothDeviceDiscovery){
                synchronizeBluetoothDeviceDiscovery.notifyAll();
            }
        }

        @Override
        public void serviceSearchCompleted(int transID, int respCode) {
            System.out.println("Service search completed, response code: " + respCode);
            services.putIfAbsent(transID, new ServiceRecord[0]);
            synchronized(synchronizeBluetoothServiceDiscovery){
                synchronizeBluetoothServiceDiscovery.notifyAll();
            }
        }

        @Override
        public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            // System.out.println("Service discovered " + transID + " " + Arrays.toString(servRecord));
            services.put(transID, servRecord);
        }
    };

    private static BluetoothManager instance;

    /**
     * Initialize Bluecove
     * @throws BluetoothStateException when Bluecove fails to initialize
     */
    private BluetoothManager() throws BluetoothStateException{
        BlueCoveImpl.setConfigProperty(BlueCoveConfigProperties.PROPERTY_JSR_82_PSM_MINIMUM_OFF, "true");
        device = LocalDevice.getLocalDevice();
    }

    public String getAdapterName() {
        return device.getFriendlyName();
    }

    public String getAdapterAddress(){
        return device.getBluetoothAddress();
    }

    public static BluetoothManager getInstance() throws BluetoothStateException{
        if(instance == null){
            instance = new BluetoothManager();
        }
        return instance;
    }

    public void getPairedDevices(ParamRunnable<BluetoothDeviceInfo> onDeviceFound) throws BluetoothStateException {
        this.onDeviceFound = onDeviceFound;

        synchronized(synchronizeBluetoothDeviceDiscovery) {
            boolean started = device.getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
            if(started){
                try {
                    synchronizeBluetoothDeviceDiscovery.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void cancelPairedDevicesSearch(){
        device.getDiscoveryAgent().cancelInquiry(listener);
    }

    private final Semaphore oneAtATime = new Semaphore(3); //TODO: tune this to the maximum number of inquiries BlueCove allows

    public BluetoothServiceInfo getServicesForDeviceWithoutFail(RemoteDevice btDevice, UUID uuid) {
        
        String address = btDevice.getBluetoothAddress();
        System.out.println("looking up device " + address + " from " + discoveredDevices);

        int search = -1;

        synchronized(synchronizeBluetoothServiceDiscovery){
            while(!services.containsKey(search)){
                try {
                    oneAtATime.acquire();
                    search = device.getDiscoveryAgent().searchServices(null, new UUID[]{uuid}, btDevice, listener);
                    synchronizeBluetoothServiceDiscovery.wait();
                    oneAtATime.release();
                } catch (BluetoothStateException | InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Sdp search found " + services.get(search) + " for " + address);

        // sdpFound.put(Utils.cleanAddress(address), new ArrayList<>());

        BluetoothServiceInfo info = new BluetoothServiceInfo();
        
        File log = null;
        try {
            log = File.createTempFile("bluetoothlogdoesnotexist", "txt");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if(App.logging){
            log = new File("./logs/" + address + "_0x" + uuid.toString().substring(4,8));
            int count = 1;
            while(log.exists()){
                log = new File("./logs/" + address + "_0x" + uuid.toString().substring(4,8) + "-" + count++);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(log))) {
            ServiceRecord[] records = services.remove(search);
            for(ServiceRecord record : records){
                if(App.logging){
                    writer.write("Viewing record of " + record.getHostDevice() + "\n");
                }

                //TODO: change this to choose the right protocol if anything other than rfcomm only is supported
                // sdpFound.get(address).add(new RFCOMMSDP(record));

                for(int attributeID : record.getAttributeIDs()){
                    if(App.logging){
                        writer.write("Begin attribute id " + attributeID + "\n");
                    }
                    DataElement element = record.getAttributeValue(attributeID);
                    info = recurseElements(element, info, 1, writer);
                }
                if(App.logging){
                    writer.write("\n");
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(info);

        return info;
    }

    public BluetoothServiceInfo getServicesForDeviceWithoutFail(String address, UUID uuid) {
        address = Utils.cleanAddress(address);

        System.out.println("looking up device " + address + " from " + discoveredDevices);

        RemoteDevice[] devices = device.getDiscoveryAgent().retrieveDevices(DiscoveryAgent.PREKNOWN);
        for(RemoteDevice btDevice : devices){
            if(btDevice.getBluetoothAddress().equals(address)){
                return getServicesForDeviceWithoutFail(btDevice, uuid);
            }
        }
        return new BluetoothServiceInfo();
    }

    private BluetoothServiceInfo recurseElements(DataElement element, BluetoothServiceInfo info, int level, BufferedWriter log){
        if(App.logging){
            String tabs = "";
            for(int i = 0; i < level; i++){
                tabs += "   ";
            }
            try {
                log.write(tabs);
            } catch (IOException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        }

        switch(element.getDataType()){
            case DataElement.DATALT:
                if(App.logging){
                    try {
                        log.write("entering datalt\n");
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            case DataElement.DATSEQ:{
                if(App.logging){
                    try {
                        log.write("entering datseq\n");
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }

                @SuppressWarnings("unchecked") //was checked by switch statement
                Enumeration<DataElement> value = (Enumeration<DataElement>)element.getValue();
                BluetoothServiceInfo[] infoForInner = new BluetoothServiceInfo[]{info};
                value.asIterator().forEachRemaining((e) -> {
                    infoForInner[0] = recurseElements(e, infoForInner[0], level + 1, log);
                });
                info = infoForInner[0];
                break;
            }
            case DataElement.STRING:{
                String value = (String)element.getValue();
                if(App.logging){
                    try {
                        log.write("Available String: " + value + "\n");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            }
            case DataElement.UUID:{
                UUID value = (UUID)element.getValue();
                if(App.logging){
                    try {
                        log.write("Available UUID: " + Utils.attemptMatchUUID(value.toString()) + "\n");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                info.addUuid(value.toString());
                break;
            }
            case DataElement.URL:{
                String value = (String)element.getValue();
                if(App.logging){
                    try {
                        log.write("Available URL: " + value + "\n");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            }
            case DataElement.BOOL:{
                boolean value = (boolean)element.getBoolean();
                if(App.logging){
                    try {
                        log.write("Available Boolean: " + value + "\n");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            }
            case DataElement.U_INT_1:{
                byte value = Byte.valueOf((byte)element.getLong());
                if(App.logging){
                    try {
                        log.write("Available unsigned byte: " + value + "\n");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            }
            case DataElement.U_INT_2:{
                short value = Short.reverseBytes((short)element.getLong());
                if(App.logging){
                    try {
                        log.write("Available unsigned short: " + value + "\n");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            }
            case DataElement.U_INT_4:
            case DataElement.INT_1:
            case DataElement.INT_2:
            case DataElement.INT_4:
            case DataElement.INT_8:{
                long value = Long.reverseBytes(element.getLong());
                if(App.logging){
                    try {
                        log.write("Available " + element.getDataType() + " long: " + value + "\n");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            }
            default:
                if(App.logging){
                    try {
                        log.write("Other: "  + element.getDataType() + " : " + element.getValue() + "\n");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
        }
        return info;
    }


    public void createRFCOMMConnection(String address, String uuid, ObjectOutputStream outToAndroid, ObjectInputStream inFromAndroid) throws IOException, InterruptedException{
        address = Utils.cleanAddress(address);
        uuid = Utils.cleanUUID(uuid);
        System.out.println("Creating rfcomm socket to " + address);

        // RFCOMMSDP base = (RFCOMMSDP) matchSDP(address, uuid);
        
        // String url = BluetoothConsts.PROTOCOL_SCHEME_RFCOMM + "://" + address + ":" + base.getChannel();
        String url = device.getDiscoveryAgent().selectService(new UUID(uuid, false), 0, false);
        System.out.println("Connecting to " + url);
        StreamConnection connection = (StreamConnection) Connector.open(url);

        outToAndroid.writeObject(new BluetoothDeviceConnected(address));
        System.out.println("successfully connected and informed android");

        OutputStream outToDevice = connection.openOutputStream();
        InputStream inFromDevice = connection.openInputStream();

        SocketForwarder forwarder1 = new SocketForwarder(outToDevice, inFromAndroid);
        SocketForwarder forwarder2 = new SocketForwarder(outToAndroid, inFromDevice);
        forwarder1.setSnooping(App.snooping);
        forwarder2.setSnooping(App.snooping);

        forwarder1.start();
        forwarder2.start();
        
        forwarder1.join();
        forwarder2.join();
    }

    public void createRFCOMMServerConnection(String uuid, String ip, int port) throws IOException, InterruptedException {
        uuid = Utils.cleanUUID(uuid);
        String url = BluetoothConsts.PROTOCOL_SCHEME_RFCOMM + "://localhost:" + uuid + ";name=WindowsSubsystemForAndroid";
        StreamConnectionNotifier acceptor = (StreamConnectionNotifier)Connector.open(url);

        while(true){
            StreamConnection connection = acceptor.acceptAndOpen();

            Thread overall = new Thread(() -> {
                try {
                    OutputStream outToDevice = connection.openOutputStream();
                
                    InputStream inFromDevice = connection.openInputStream();

                    Socket socket = new Socket(ip, port);
                    OutputStream outToAndroid = socket.getOutputStream();
                    InputStream inFromAndroid = socket.getInputStream(); 

                    Thread forwarder1 = new Thread(() -> {
                        while(true){
                            try {
                                inFromAndroid.transferTo(outToDevice);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                try {
                                    socket.close();
                                } catch (IOException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                                break;
                            }
                        }
                    });

                    Thread forwarder2 = new Thread(() -> {
                        while(true){
                            try {
                                inFromDevice.transferTo(outToAndroid);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                try {
                                    socket.close();
                                } catch (IOException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                                break;
                            }
                        }
                    });

                    forwarder1.start();
                    forwarder2.start();

                    forwarder1.join();
                    forwarder2.join();

                } catch (IOException | InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });

            overall.start();
        }
    }
}
