package com.android.server.zbt;

import java.util.List;

import android.bluetooth.BluetoothActivityEnergyInfo;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.IBluetooth;
import android.bluetooth.IBluetoothCallback;
import android.bluetooth.IBluetoothConnectionCallback;
import android.bluetooth.IBluetoothMetadataListener;
import android.bluetooth.IBluetoothOobDataCallback;
import android.bluetooth.IBluetoothSocketManager;
import android.bluetooth.OobData;
import android.content.AttributionSource;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;

public class BluetoothProxyService extends IBluetooth.Stub {

    @Override
    public boolean canBondWithoutDialog(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean cancelBondProcess(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean cancelDiscovery(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean connectAllEnabledProfiles(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean createBond(BluetoothDevice arg0, int arg1, OobData arg2, OobData arg3, AttributionSource arg4)
            throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean disable(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean disconnectAllEnabledProfiles(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean enable(boolean arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        Log.i("BLUETOOTHPROXYSERVICE", "failed to enable");
        return false;
    }

    @Override
    public boolean factoryReset(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean fetchRemoteUuids(BluetoothDevice arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean fetchRemoteUuidsWithAttribution(BluetoothDevice arg0, AttributionSource arg1)
            throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void generateLocalOobData(int arg0, IBluetoothOobDataCallback arg1, AttributionSource arg2)
            throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getAdapterConnectionState() throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getAddress() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAddressWithAttribution(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getBatteryLevel(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public BluetoothClass getBluetoothClass(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getBondState(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public BluetoothDevice[] getBondedDevices(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getConnectionState(BluetoothDevice arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getConnectionStateWithAttribution(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getDiscoverableTimeout(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getDiscoveryEndMillis(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getIoCapability(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getLeIoCapability(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getLeMaximumAdvertisingDataLength() throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMaxConnectedAudioDevices(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMessageAccessPermission(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public byte[] getMetadata(BluetoothDevice arg0, int arg1, AttributionSource arg2) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<BluetoothDevice> getMostRecentlyConnectedDevices(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getNameLengthForAdvertise(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getPhonebookAccessPermission(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getProfileConnectionState(int arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getRemoteAlias(BluetoothDevice arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRemoteAliasWithAttribution(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getRemoteClass(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getRemoteName(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getRemoteType(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ParcelUuid[] getRemoteUuids(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getScanMode(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean getSilenceMode(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getSimAccessPermission(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public IBluetoothSocketManager getSocketManager() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getState() throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getSupportedProfiles() throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ParcelUuid[] getUuids(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isActivityAndEnergyReportingSupported() throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isBondingInitiatedLocally(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDiscovering(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isLe2MPhySupported() throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isLeCodedPhySupported() throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isLeExtendedAdvertisingSupported() throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isLePeriodicAdvertisingSupported() throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isMultiAdvertisementSupported() throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isOffloadedFilteringSupported() throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isOffloadedScanBatchingSupported() throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onBrEdrDown(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onLeServiceUp(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean registerBluetoothConnectionCallback(IBluetoothConnectionCallback arg0, AttributionSource arg1)
            throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void registerCallback(IBluetoothCallback arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean registerMetadataListener(IBluetoothMetadataListener arg0, BluetoothDevice arg1,
            AttributionSource arg2) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeActiveDevice(int arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeBond(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public BluetoothActivityEnergyInfo reportActivityInfo(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void requestActivityInfo(ResultReceiver arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean sdpSearch(BluetoothDevice arg0, ParcelUuid arg1, AttributionSource arg2) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setActiveDevice(BluetoothDevice arg0, int arg1, AttributionSource arg2) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setBluetoothClass(BluetoothClass arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setDiscoverableTimeout(int arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setIoCapability(int arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setLeIoCapability(int arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setMessageAccessPermission(BluetoothDevice arg0, int arg1, AttributionSource arg2)
            throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setMetadata(BluetoothDevice arg0, int arg1, byte[] arg2, AttributionSource arg3)
            throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setName(String arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setPairingConfirmation(BluetoothDevice arg0, boolean arg1, AttributionSource arg2)
            throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setPasskey(BluetoothDevice arg0, boolean arg1, int arg2, byte[] arg3, AttributionSource arg4)
            throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setPhonebookAccessPermission(BluetoothDevice arg0, int arg1, AttributionSource arg2)
            throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setPin(BluetoothDevice arg0, boolean arg1, int arg2, byte[] arg3, AttributionSource arg4)
            throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int setRemoteAlias(BluetoothDevice arg0, String arg1, AttributionSource arg2) throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean setScanMode(int arg0, int arg1, AttributionSource arg2) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setSilenceMode(BluetoothDevice arg0, boolean arg1, AttributionSource arg2) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setSimAccessPermission(BluetoothDevice arg0, int arg1, AttributionSource arg2)
            throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean startDiscovery(AttributionSource arg0) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean unregisterBluetoothConnectionCallback(IBluetoothConnectionCallback arg0, AttributionSource arg1)
            throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void unregisterCallback(IBluetoothCallback arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean unregisterMetadataListener(BluetoothDevice arg0, AttributionSource arg1) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }
    
}
