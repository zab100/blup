/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* This file was last modified by Zain B */

/*
 * Defines the native inteface that is used by state machine/service to
 * send or receive messages from the native stack. This file is registered
 * for the native methods in the corresponding JNI C++ file.
 */
package com.android.bluetooth.a2dp;

import android.annotation.RequiresPermission;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothCodecConfig;
import android.bluetooth.BluetoothCodecStatus;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.android.bluetooth.Utils;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;

/**
 * A2DP Native Interface to/from JNI.
 */
public class A2dpNativeInterface {
    private static final String TAG = "A2dpNativeInterface";
    private static final boolean DBG = true;
    private BluetoothAdapter mAdapter;

    @GuardedBy("INSTANCE_LOCK")
    private static A2dpNativeInterface sInstance;
    private static final Object INSTANCE_LOCK = new Object();

    static {
        classInitNative();
    }

    @VisibleForTesting
    private A2dpNativeInterface() {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter == null) {
            Log.wtf(TAG, "No Bluetooth Adapter Available");
        }
    }

    /**
     * Get singleton instance.
     */
    public static A2dpNativeInterface getInstance() {
        synchronized (INSTANCE_LOCK) {
            if (sInstance == null) {
                sInstance = new A2dpNativeInterface();
            }
            return sInstance;
        }
    }

    /**
     * Initializes the native interface.
     *
     * @param maxConnectedAudioDevices maximum number of A2DP Sink devices that can be connected
     * simultaneously
     * @param codecConfigPriorities an array with the codec configuration
     * priorities to configure.
     */
    public void init(int maxConnectedAudioDevices, BluetoothCodecConfig[] codecConfigPriorities,
            BluetoothCodecConfig[] codecConfigOffloading) {
        initNative(maxConnectedAudioDevices, codecConfigPriorities, codecConfigOffloading);
    }

    /**
     * Cleanup the native interface.
     */
    public void cleanup() {
        cleanupNative();
    }

    /**
     * Initiates A2DP connection to a remote device.
     *
     * @param device the remote device
     * @return true on success, otherwise false.
     */
    public boolean connectA2dp(BluetoothDevice device) {
        return connectA2dpWindows(getByteAddress(device));
    }

    /**
     * Disconnects A2DP from a remote device.
     *
     * @param device the remote device
     * @return true on success, otherwise false.
     */
    public boolean disconnectA2dp(BluetoothDevice device) {
        return disconnectA2dpWindows(getByteAddress(device));
    }

    /**
     * Sets a connected A2DP remote device to silence mode.
     *
     * @param device the remote device
     * @return true on success, otherwise false.
     */
    public boolean setSilenceDevice(BluetoothDevice device, boolean silence) {
        return setSilenceDeviceWindows(getByteAddress(device), silence);
    }

    /**
     * Sets a connected A2DP remote device as active.
     *
     * @param device the remote device
     * @return true on success, otherwise false.
     */
    public boolean setActiveDevice(BluetoothDevice device) {
        return setActiveDeviceWindows(getByteAddress(device));
    }

    /**
     * Sets the codec configuration preferences.
     *
     * @param device the remote Bluetooth device
     * @param codecConfigArray an array with the codec configurations to
     * configure.
     * @return true on success, otherwise false.
     */
    public boolean setCodecConfigPreference(BluetoothDevice device,
                                            BluetoothCodecConfig[] codecConfigArray) {
        return setCodecConfigPreferenceWindows(getByteAddress(device),
                                              codecConfigArray);
    }

    private BluetoothDevice getDevice(byte[] address) {
        return mAdapter.getRemoteDevice(address);
    }

    private byte[] getByteAddress(BluetoothDevice device) {
        if (device == null) {
            return Utils.getBytesFromAddress("00:00:00:00:00:00");
        }
        return Utils.getBytesFromAddress(device.getAddress());
    }

    private void sendMessageToService(A2dpStackEvent event) {
        A2dpService service = A2dpService.getA2dpService();
        if (service != null) {
            service.messageFromNative(event);
        } else {
            Log.w(TAG, "Event ignored, service not available: " + event);
        }
    }

    // Callbacks from the native stack back into the Java framework.
    // All callbacks are routed via the Service which will disambiguate which
    // state machine the message should be routed to.

    private void onConnectionStateChanged(byte[] address, int state) {
        A2dpStackEvent event =
                new A2dpStackEvent(A2dpStackEvent.EVENT_TYPE_CONNECTION_STATE_CHANGED);
        event.device = getDevice(address);
        event.valueInt = state;

        if (DBG) {
            Log.d(TAG, "onConnectionStateChanged: " + event);
        }
        sendMessageToService(event);
    }

    private void onAudioStateChanged(byte[] address, int state) {
        A2dpStackEvent event = new A2dpStackEvent(A2dpStackEvent.EVENT_TYPE_AUDIO_STATE_CHANGED);
        event.device = getDevice(address);
        event.valueInt = state;

        if (DBG) {
            Log.d(TAG, "onAudioStateChanged: " + event);
        }
        sendMessageToService(event);
    }

    private void onCodecConfigChanged(byte[] address,
            BluetoothCodecConfig newCodecConfig,
            BluetoothCodecConfig[] codecsLocalCapabilities,
            BluetoothCodecConfig[] codecsSelectableCapabilities) {
        A2dpStackEvent event = new A2dpStackEvent(A2dpStackEvent.EVENT_TYPE_CODEC_CONFIG_CHANGED);
        event.device = getDevice(address);
        event.codecStatus = new BluetoothCodecStatus(newCodecConfig,
                                                     codecsLocalCapabilities,
                                                     codecsSelectableCapabilities);
        if (DBG) {
            Log.d(TAG, "onCodecConfigChanged: " + event);
        }
        sendMessageToService(event);
    }

    private boolean isMandatoryCodecPreferred(byte[] address) {
        A2dpService service = A2dpService.getA2dpService();
        if (service != null) {
            int enabled = service.getOptionalCodecsEnabled(getDevice(address));
            if (DBG) {
                Log.d(TAG, "isMandatoryCodecPreferred: optional preference " + enabled);
            }
            // Optional codecs are more preferred if possible
            return enabled == BluetoothA2dp.OPTIONAL_CODECS_PREF_DISABLED;
        } else {
            Log.w(TAG, "isMandatoryCodecPreferred: service not available");
            return false;
        }
    }

    // Native methods that call into the JNI interface
    private static native void classInitNative();
    private native void initNative(int maxConnectedAudioDevices, BluetoothCodecConfig[] codecConfigPriorities, BluetoothCodecConfig[] codecConfigOffloading);
    private native void cleanupNative();
    private native boolean connectA2dpNative(byte[] address);
    private native boolean disconnectA2dpNative(byte[] address);
    private native boolean setSilenceDeviceNative(byte[] address, boolean silence);
    private native boolean setActiveDeviceNative(byte[] address);
    private native boolean setCodecConfigPreferenceNative(byte[] address, BluetoothCodecConfig[] codecConfigArray);


    private boolean connectA2dpWindows(byte[] address){
        Log.e("MODIFYANDROIDA2DP", "connect a2dp windows " + Utils.byteArrayToString(address));
        
        return false;
    }

    private boolean disconnectA2dpWindows(byte[] address){
        Log.e("MODIFYANDROIDA2DP", "disconnect a2dp windows " + Utils.byteArrayToString(address));
        return false;
    }

    private boolean setSilenceDeviceWindows(byte[] address, boolean silence){
        Log.e("MODIFYANDROIDA2DP", "silence a2dp windows " + Utils.byteArrayToString(address));
        return false;
    }

    private boolean setActiveDeviceWindows(byte[] address){
        Log.e("MODIFYANDROIDA2DP", "active device a2dp windows " + Utils.byteArrayToString(address));
        return false;
    }
    
    private boolean setCodecConfigPreferenceWindows(byte[] address, BluetoothCodecConfig[] codecConfigArray){
        Log.e("MODIFYANDROIDA2DP", "codec config a2dp windows " + Utils.byteArrayToString(address));
        return false;
    }
}
