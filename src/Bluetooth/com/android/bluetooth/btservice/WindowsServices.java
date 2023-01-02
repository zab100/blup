/* This file was last modified by Zain B */

package com.android.bluetooth.btservice;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import com.android.bluetooth.Utils;
import com.android.bluetooth.windowsnativereplacements.WindowsCommunicator;
import com.zbt.NetworkingRequest;
import com.zbt.shared.networking.BluetoothDeviceConnected;
import com.zbt.shared.networking.BluetoothDeviceInfo;
import com.zbt.shared.networking.BluetoothDeviceMessage;
import com.zbt.shared.networking.BluetoothServerSocketHeader;
import com.zbt.shared.networking.BluetoothSocketHeader;
import com.zbt.shared.networking.SocketType;
import com.zbt.shared.utils.SocketForwarder;

import android.bluetooth.OobData;
import android.net.LocalSocket;
import android.os.ParcelFileDescriptor;
import android.os.ParcelUuid;
import android.util.Log;

public class WindowsServices {
    private WindowsCallbacks windowsCallbacks;

    public WindowsServices(WindowsCallbacks windowsCallbacks){
        this.windowsCallbacks = windowsCallbacks;
    }

    static void classInitWindows(){
        Log.e("MODIFYANDROIDSYSTEM", "class init windows");
    }

    boolean initWindows(boolean startRestricted, boolean isCommonCriteriaMode, int configCompareResult, String[] initFlags, boolean isAtvDevice){
        Log.e("MODIFYANDROIDSYSTEM", "init windows");
        return true;
    }

    void cleanupWindows(){
        Log.e("MODIFYANDROIDSYSTEM", "cleanup windows");
    }

    /*package*/
    boolean enableWindows(){
        Log.e("MODIFYANDROIDSYSTEM", "enable windows");
        windowsCallbacks.stateChangeCallback(AbstractionLayer.BT_STATE_ON);
        startDiscoveryWindows();
        return true;
    }

    /*package*/
    boolean disableWindows(){
        Log.e("MODIFYANDROIDSYSTEM", "disable windows");
        return true;
    }

    /*package*/
    boolean setAdapterPropertyWindows(int type, byte[] val){
        Log.e("MODIFYANDROIDSYSTEM", "set adapter property windows " + type + " " + Arrays.toString(val));
        return false;
    }

    /*package*/
    boolean getAdapterPropertiesWindows(){
        Log.e("MODIFYANDROIDSYSTEM", "get adapter properties windows");
        return false;
    }

    /*package*/
    boolean getAdapterPropertyWindows(int type){
        Log.e("MODIFYANDROIDSYSTEM", "get adapter property windows " + type);
        // switch(type){
        //     case AbstractionLayer.BT_PROPERTY_BDNAME:
        //         WindowsCommunicator.sendToWindows(new BluetoothDeviceMessage(BluetoothDeviceMessage.Request.REQUEST_BLUETOOTH_ADAPTER_NAME), (response) -> {
        //             Log.e("MODIFYANDROIDSYSTEM", "Got response: name " + response.getAdapterName());
        //             windowsCallbacks.adapterPropertyChangedCallback(new int[]{AbstractionLayer.BT_PROPERTY_BDNAME}, new byte[][]{response.getAdapterName().getBytes()});
        //         });
        //         break;
        //     case AbstractionLayer.BT_PROPERTY_BDADDR:
        //         WindowsCommunicator.sendToWindows(new BluetoothDeviceMessage(BluetoothDeviceMessage.Request.REQUEST_BLUETOOTH_ADAPTER_ADDRESS), (response) -> {
        //             Log.e("MODIFYANDROIDSYSTEM", "Got response: addr " + response.getAdapterAddress());
        //             windowsCallbacks.adapterPropertyChangedCallback(new int[]{AbstractionLayer.BT_PROPERTY_BDADDR}, new byte[][]{response.getAdapterAddress().getBytes()});
        //         });
        //         break;
        //     default:
        //         return false;
        // }
        // return WindowsCommunicator.isAddressSet();
        return false;
    }

    /*package*/
    boolean setAdapterPropertyWindows(int type){
        Log.e("MODIFYANDROIDSYSTEM", "set adapter property windows " + type);
        return false;
    }

    /*package*/
    boolean setDevicePropertyWindows(byte[] address, int type, byte[] val){
        Log.e("MODIFYANDROIDSYSTEM", "set device property windows " + Utils.byteArrayToString(address) + " " + type + " " + Arrays.toString(val));
        return false;
    }

    /*package*/
    boolean getDevicePropertyWindows(byte[] address, int type){
        Log.e("MODIFYANDROIDSYSTEM", "getting device property " + Utils.byteArrayToString(address) + " " + type);
        return false;
    }

    /*package*/
    public boolean createBondWindows(byte[] address, int transport){
        Log.e("MODIFYANDROIDSYSTEM", "create bond windows " + Utils.byteArrayToString(address) + " " + transport);
        windowsCallbacks.bondStateChangeCallback(AbstractionLayer.BT_STATUS_SUCCESS, address, AbstractionLayer.BT_BOND_STATE_BONDING);
        windowsCallbacks.bondStateChangeCallback(AbstractionLayer.BT_STATUS_SUCCESS, address, AbstractionLayer.BT_BOND_STATE_BONDED);
        windowsCallbacks.adapterPropertyChangedCallback(new int[]{AbstractionLayer.BT_PROPERTY_ADAPTER_BONDED_DEVICES}, new byte[][]{address}); 
        // windowsCallbacks.sspRequestCallback(address, new byte[]{65}, 0, AbstractionLayer.BT_SSP_VARIANT_PASSKEY_CONFIRMATION, 1598);
        return true;
    }

    /*package*/
    boolean createBondOutOfBandWindows(byte[] address, int transport, OobData p192Data, OobData p256Data){
        Log.e("MODIFYANDROIDSYSTEM", "create bond out of band windows " + Utils.byteArrayToString(address) + " " + transport);
        // windowsCallbacks.bondStateChangeCallback(AbstractionLayer.BT_STATUS_SUCCESS, address, AbstractionLayer.BT_BOND_STATE_BONDING);
        // // windowsCallbacks.sspRequestCallback(address, new byte[]{65}, 0, AbstractionLayer.BT_SSP_VARIANT_PASSKEY_CONFIRMATION, 1598);
        // windowsCallbacks.bondStateChangeCallback(AbstractionLayer.BT_STATUS_SUCCESS, address, AbstractionLayer.BT_BOND_STATE_BONDED);
        // windowsCallbacks.adapterPropertyChangedCallback(new int[]{AbstractionLayer.BT_PROPERTY_ADAPTER_BONDED_DEVICES}, new byte[][]{address});

        return createBondWindows(address, transport);
    }

    /*package*/
    public boolean removeBondWindows(byte[] address){
        Log.e("MODIFYANDROIDSYSTEM", "remove bond windows " + Utils.byteArrayToString(address));
        windowsCallbacks.bondStateChangeCallback(AbstractionLayer.BT_STATUS_SUCCESS, address, AbstractionLayer.BT_BOND_STATE_NONE);
        return true;
    }

    /*package*/
    boolean cancelBondWindows(byte[] address){
        Log.e("MODIFYANDROIDSYSTEM", "cancelling bond");
        return false;
    }

    /*package*/
    void generateLocalOobDataWindows(int transport){
        Log.e("MODIFYANDROIDSYSTEM", "generate local oob data");
    }

    /*package*/
    boolean sdpSearchWindows(byte[] address, byte[] uuid){
        Log.e("MODIFYANDROIDSYSTEM", "sdp search " + Utils.byteArrayToString(address) + " " + Arrays.toString(uuid));
        return false;
    }

    private Set<byte[]> connectedDevices = new HashSet<>();

    /*package*/
    int getConnectionStateWindows(byte[] address){
        Log.e("MODIFYANDROIDSYSTEM", "get connection state windows " + Utils.byteArrayToString(address));
        return connectedDevices.contains(address) ? AbstractionLayer.BT_ACL_STATE_CONNECTED : AbstractionLayer.BT_ACL_STATE_DISCONNECTED;
    }

    boolean startDiscoveryWindows(){
        windowsCallbacks.discoveryStateChangeCallback(AbstractionLayer.BT_DISCOVERY_STARTED);
        BluetoothDeviceMessage message = new BluetoothDeviceMessage(NetworkingRequest.REQUEST_PAIRED_DEVICES);
        WindowsCommunicator.sendToWindows(message, (response) -> {
            BluetoothDeviceInfo device = response.getPairedDevice();
            System.out.println("Discovered device " + device);
            String address = device.getAddress();
            String name = device.getName();
            Integer majorClassOfDevice = device.getMajorClassOfDevice();
            Integer minorClassOfDevice = device.getMinorClassOfDevice();

            byte[] addressBytes = Utils.addressToBytes(address);

            windowsCallbacks.devicePropertyChangedCallback(addressBytes, 
                new int[]{AbstractionLayer.BT_PROPERTY_BDNAME, AbstractionLayer.BT_PROPERTY_CLASS_OF_DEVICE, AbstractionLayer.BT_PROPERTY_TYPE_OF_DEVICE}, 
                new byte[][]{name.getBytes(), Utils.intToByteArray(majorClassOfDevice), Utils.intToByteArray(minorClassOfDevice)});
            
            windowsCallbacks.deviceFoundCallback(addressBytes);

            windowsCallbacks.bondStateChangeCallback(AbstractionLayer.BT_STATUS_SUCCESS, addressBytes, AbstractionLayer.BT_BOND_STATE_BONDING);
            windowsCallbacks.bondStateChangeCallback(AbstractionLayer.BT_STATUS_SUCCESS, addressBytes, AbstractionLayer.BT_BOND_STATE_BONDED);
            windowsCallbacks.adapterPropertyChangedCallback(new int[]{AbstractionLayer.BT_PROPERTY_ADAPTER_BONDED_DEVICES}, new byte[][]{addressBytes}); 
        }, (success) -> {
            windowsCallbacks.discoveryStateChangeCallback(AbstractionLayer.BT_DISCOVERY_STOPPED);
        });

        return true;
    }

    boolean cancelDiscoveryWindows(){
        WindowsCommunicator.sendToWindows(new BluetoothDeviceMessage(NetworkingRequest.CANCEL_PAIRED_DEVICE_SEARCH));
        windowsCallbacks.discoveryStateChangeCallback(AbstractionLayer.BT_DISCOVERY_STOPPED);
        return true;
    }

    boolean pinReplyWindows(byte[] address, boolean accept, int len, byte[] pin){
        Log.e("MODIFYANDROIDSYSTEM", "pin reply");
        return false;
    }

    boolean sspReplyWindows(byte[] address, int type, boolean accept, int passkey){
        Log.e("MODIFYANDROIDSYSTEM", "ssp reply");
        return false;
    }

    /*package*/
    boolean getRemoteServicesWindows(byte[] address){
        Log.e("MODIFYANDROIDSYSTEM", "get remote services");

        WindowsCommunicator.sendToWindows(new BluetoothDeviceMessage(NetworkingRequest.REQUEST_BLUETOOTH_DEVICE_ALL_BASIC_UUIDS, Utils.byteArrayToString(address)), (response) ->{
            Log.e("MODIFYANDROIDSYSTEM", "Got bonding services result");
            ParcelUuid[] parcelUuids = new ParcelUuid[response.getDeviceServices().getUuids().size()];
            int[] index = new int[]{0};
            response.getDeviceServices().getUuids().forEach((uuid) -> {
                parcelUuids[index[0]] = ParcelUuid.fromString(uuid);
                index[0]++;
            });

            windowsCallbacks.devicePropertyChangedCallback(address, 
                        new int[]{AbstractionLayer.BT_PROPERTY_UUIDS}, 
                        new byte[][]{Utils.uuidsToByteArray(parcelUuids)});
        });
        return true;
    }

    /*package*/
    boolean getRemoteMasInstancesWindows(byte[] address){
        Log.e("MODIFYANDROIDSYSTEM", "get remote mas instances");
        return false;
    }

    int readEnergyInfo(){
        Log.e("MODIFYANDROIDSYSTEM", "read energy info");
        return -1;
    }

    /*package*/
    boolean factoryResetWindows(){
        Log.e("MODIFYANDROIDSYSTEM", "factory reset windows");
        return false;
    }

    void alarmFiredWindows(){
        Log.e("MODIFYANDROIDSYSTEM", "alarm fired windows ");
    }

    void dumpWindows(FileDescriptor fd, String[] arguments){
        Log.e("MODIFYANDROIDSYSTEM", "dump windows ");
    }

    byte[] dumpMetricsWindows(){
        Log.e("MODIFYANDROIDSYSTEM", "dump metrics windows ");
        return null;
    }

    void interopDatabaseClearWindows(){
        Log.e("MODIFYANDROIDSYSTEM", "interop database clear windows ");
    }

    void interopDatabaseAddWindows(int feature, byte[] address, int length){
        Log.e("MODIFYANDROIDSYSTEM", "interop database add windows " + Utils.byteArrayToString(address) + " " + feature + " " + length);
    }

    byte[] obfuscateAddressWindows(byte[] address){
        Log.e("MODIFYANDROIDSYSTEM", "obfuscate address windows " + Utils.byteArrayToString(address));
        return address;
    }

    boolean setBufferLengthMillisWindows(int codec, int value){
        Log.e("MODIFYANDROIDSYSTEM", "set buffer length millis windows " + codec + " " + value);
        return false;
    }

    int getMetricIdWindows(byte[] address){
        Log.e("MODIFYANDROIDSYSTEM", "get metric id windows " + Utils.byteArrayToString(address));
        return 0;
    }

    /*package*/ int connectSocketWindows(byte[] address, int type, byte[] uuid, int port, int flag, int callingUid){
        Log.i("MODIFYANDROIDWINDOWSSERVICES", "creating socket " + Utils.byteArrayToString(address) + " " + type + " " + Utils.byteArrayToString(uuid) + " " + port + " " + flag);
        String stringAddress = Utils.getAddressStringFromByte(address);
        class SpecialThread extends Thread {
            ParcelFileDescriptor[] descriptors;

            public SpecialThread() throws IOException {
                descriptors = ParcelFileDescriptor.createReliableSocketPair();
            }

            @Override
            public void run(){
                try {
                    LocalSocket mySide = LocalSocket.createConnectedLocalSocket(descriptors[1].getFileDescriptor());
                    OutputStream toBluetoothSocket = mySide.getOutputStream();
                    InputStream fromBluetoothSocket = mySide.getInputStream();

                    Socket toWindowsSocket = new Socket("127.0.0.1", 5057);
                    ObjectOutputStream toWindows = new ObjectOutputStream(toWindowsSocket.getOutputStream());
                    ObjectInputStream fromWindows = new ObjectInputStream(toWindowsSocket.getInputStream());

                    toWindows.writeObject(new BluetoothDeviceMessage(NetworkingRequest.REQUEST_BLUETOOTH_SOCKET_CONNECTION));
                    toWindows.writeObject(new BluetoothSocketHeader(SocketType.RFCOMM, stringAddress, Utils.byteArrayToString(uuid), port));

                    Log.i("MODIFYANDROIDCONNECTSOCKET", "Waiting for response from windows side");
                    
                    BluetoothDeviceConnected connectedMessage = (BluetoothDeviceConnected) fromWindows.readObject();
                    connectedDevices.add(address);
                    windowsCallbacks.aclStateChangeCallback(AbstractionLayer.BT_STATUS_SUCCESS, address, AbstractionLayer.BT_ACL_STATE_CONNECTED, 1);
                    Log.i("MODIFYANDROIDCONNECTSOCKET", "device connected " + connectedMessage.getAddress());

                    toBluetoothSocket.write(Utils.intToByteArray(1)); //TODO: set channel number properly on this and windows side
                    

                    /*
                        typedef struct {
                            short size;
                            RawAddress bd_addr;
                            int channel;
                            int status;
                        
                            // The writer must make writes using a buffer of this maximum size
                            // to avoid loosing data. (L2CAP only)
                            unsigned short max_tx_packet_size;
                        
                            // The reader must read using a buffer of at least this size to avoid
                            // loosing data. (L2CAP only)
                            unsigned short max_rx_packet_size;
                        } __attribute__((packed)) sock_connect_signal_t;
                    */

                    ByteBuffer bb = ByteBuffer.allocate(20);//from BluetoothSocket.SOCK_SIGNAL_SIZE
                    bb.order(ByteOrder.LITTLE_ENDIAN);
                    bb.putShort((short)20);
                    bb.put(address);
                    bb.putInt(1);
                    bb.putInt(0);
                    bb.putShort((short)-1);
                    bb.putShort((short)-1);
                    bb.rewind();
                    
                    byte[] socketSignal = new byte[bb.remaining()];
                    bb.get(socketSignal);
                    toBluetoothSocket.write(socketSignal);

                    toBluetoothSocket.flush();
                    Log.i("MODIFYANDROIDCONNECTSOCKET", "informed channel number " + 1);

                    SocketForwarder sf1 = new SocketForwarder(toBluetoothSocket, fromWindows);
                    SocketForwarder sf2 = new SocketForwarder(toWindows, fromBluetoothSocket);

                    sf1.start();
                    sf2.start();

                    sf1.join();
                    sf2.join();

                    toWindowsSocket.close();
                    connectedDevices.remove(address);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } 

            public int getFD(){
                return descriptors[0].detachFd();
            }
        }

        try {
            SpecialThread thread = new SpecialThread();
            thread.start();
            return thread.getFD();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }
    }


    /*package*/ int createSocketChannelWindows(int type, String serviceName, byte[] uuid, int port, int flag, int callingUid){
        Log.i("MODIFYANDROIDWINDOWSSERVICES", "creating socket channel " + type + " " + serviceName + " " + Arrays.toString(uuid) + " " + port + " " + flag);
        // String stringAddress = Utils.getAddressStringFromByte(address);
        class SpecialThread extends Thread {
            ParcelFileDescriptor[] descriptors;

            public SpecialThread() throws IOException{
                descriptors = ParcelFileDescriptor.createReliableSocketPair();
            }

            @Override
            public void run(){
                try {
                    LocalSocket mySide = LocalSocket.createConnectedLocalSocket(descriptors[1].getFileDescriptor());
                    OutputStream myOutputStream = mySide.getOutputStream();
                    InputStream myInputStream = mySide.getInputStream();

                    try (ServerSocket serverSocket = new ServerSocket(0)) {
                        myOutputStream.write(Utils.intToByteArray(serverSocket.getLocalPort()));
                        myOutputStream.flush();

                        Socket toWindowsSocket = new Socket("127.0.0.1", 5057);
                        ObjectOutputStream toWindows = new ObjectOutputStream(toWindowsSocket.getOutputStream());
                        ObjectInputStream fromWindows = new ObjectInputStream(toWindowsSocket.getInputStream());

                        toWindows.writeObject(new BluetoothDeviceMessage(NetworkingRequest.REQUEST_BLUETOOTH_SERVER_SOCKET_CONNECTION));
                        toWindows.writeObject(new BluetoothServerSocketHeader(SocketType.RFCOMM, serverSocket.getInetAddress().getHostAddress(), serverSocket.getLocalPort(), Utils.byteArrayToString(uuid), port));

                        toWindowsSocket.close();

                        while(true){
                            Socket client = serverSocket.accept();

                            class SpecialThread2 extends Thread {
                                private ParcelFileDescriptor pfd;
                                private Socket client;
            
                                public SpecialThread2(Socket client) {
                                    this.client = client;
                                }

                                @Override
                                public void run(){
                                    try {
                                        ParcelFileDescriptor[] descriptors = ParcelFileDescriptor.createReliableSocketPair();
                                        pfd = descriptors[0];
            
                                        LocalSocket withWindows = LocalSocket.createConnectedLocalSocket(descriptors[1].getFileDescriptor());
            
                                        SocketForwarder sf1 = new SocketForwarder(withWindows.getOutputStream(), client.getInputStream());
                                        SocketForwarder sf2 = new SocketForwarder(client.getOutputStream(), withWindows.getInputStream());
            
                                        sf1.start();
                                        sf2.start();
            
                                        sf1.join();
                                        sf2.join();

                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                } 
            
                                public ParcelFileDescriptor getPFD(){
                                    return pfd;
                                }
                            }


                            SpecialThread2 acceptedClientThread = new SpecialThread2(client);
                            acceptedClientThread.start();
                            mySide.setFileDescriptorsForSend(new FileDescriptor[]{acceptedClientThread.getPFD().getFileDescriptor()});
                            myOutputStream.write(Utils.intToByteArray(serverSocket.getLocalPort()));
                            myOutputStream.flush();
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
            }

            public int getFD(){
                return descriptors[0].detachFd();
            }
        }

        try {
            SpecialThread thread = new SpecialThread();
            thread.start();
            return thread.getFD();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }
    }

    /*package*/ void requestMaximumTxDataLengthWindows(byte[] address){
        Log.e("MODIFYANDROIDSYSTEM", "request max tx data length");
    }
}
