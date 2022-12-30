package android.bluetooth.zbt;

import java.io.FileDescriptor;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;

public class ForwarderReceiver implements IBinder {

    public ForwarderReceiver() {
    }

    public boolean isWorking(boolean bool) {
        // TODO Auto-generated method stub
        return bool;
    }

    @Override
    public void dump(FileDescriptor arg0, String[] arg1) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dumpAsync(FileDescriptor arg0, String[] arg1) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getInterfaceDescriptor() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isBinderAlive() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void linkToDeath(DeathRecipient arg0, int arg1) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean pingBinder() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IInterface queryLocalInterface(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void shellCommand(FileDescriptor arg0, FileDescriptor arg1, FileDescriptor arg2, String[] arg3,
            ShellCallback arg4, ResultReceiver arg5) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean transact(int arg0, Parcel arg1, Parcel arg2, int arg3) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean unlinkToDeath(DeathRecipient arg0, int arg1) {
        // TODO Auto-generated method stub
        return false;
    }

}
