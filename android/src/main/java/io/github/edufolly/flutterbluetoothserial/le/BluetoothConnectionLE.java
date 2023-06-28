package io.github.edufolly.flutterbluetoothserial.le;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.io.IOException;
import java.util.UUID;

import io.github.edufolly.flutterbluetoothserial.BluetoothConnection;
import io.github.edufolly.flutterbluetoothserial.BluetoothConnectionBase;

public class BluetoothConnectionLE extends BluetoothConnectionBase {
    private enum Connected { False, Pending, True }

    private Connected connected = Connected.False;
    private SerialSocket socket;

    public BluetoothConnectionLE(OnReadCallback onReadCallback, OnDisconnectedCallback onDisconnectedCallback) {
        super(onReadCallback, onDisconnectedCallback);
    }

    @Override
    public boolean isConnected() {
        return connected == Connected.True;
    }

    @Override
    public void connect(String address, UUID uuid) throws IOException {
        connect(address); // Ignore the uuid, not used
    }

    @Override
    public void connect(String address) throws IOException {
        if (isConnected()) {
            throw new IOException("already connected");
        }
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
            status("connecting...");
            connected = Connected.Pending;
            SerialSocket socket = new SerialSocket(getActivity().getApplicationContext(), device);

            socket.connect(this);
            this.socket = socket;
            connected = Connected.True;
        } catch (Exception e) {
            onSerialConnectError(e);
        }
    }

    @Override
    public void disconnect() {
        if (isConnected()) {
            connected = Connected.False; // ignore data,errors while disconnecting
            cancelNotification();
            if (socket != null) {
                socket.disconnect();
                socket = null;
            }
        }
    }

    @Override
    public void write(byte[] data) throws IOException {
        if (!isConnected()) {
            throw new IOException("not connected");
        }
        socket.write(data);
    }
}
