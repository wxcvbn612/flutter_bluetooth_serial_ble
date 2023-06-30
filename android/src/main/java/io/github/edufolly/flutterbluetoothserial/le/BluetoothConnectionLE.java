package io.github.edufolly.flutterbluetoothserial.le;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.UUID;

import io.github.edufolly.flutterbluetoothserial.BluetoothConnection;
import io.github.edufolly.flutterbluetoothserial.BluetoothConnectionBase;
import io.github.edufolly.flutterbluetoothserial.BluetoothConnectionBase.OnReadCallback;
import io.github.edufolly.flutterbluetoothserial.BluetoothConnectionBase.OnDisconnectedCallback;

public class BluetoothConnectionLE extends BluetoothConnectionBase {
    public enum Connected { False, Pending, True } //DUMMY IDE claiming non-accessible

    private Connected connected = Connected.False;
    private SerialSocket socket;
    private Context ctx;

    public BluetoothConnectionLE(OnReadCallback onReadCallback, OnDisconnectedCallback onDisconnectedCallback, Context appCtx) {
        super(onReadCallback, onDisconnectedCallback);
        this.ctx = appCtx;
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
            System.out.println("connecting...");
            connected = Connected.Pending;
            SerialSocket socket = new SerialSocket(ctx, device);

            socket.connect(new io.github.edufolly.flutterbluetoothserial.le.SerialListener() {
                @Override
                public void onSerialConnect() {
                    throw new RuntimeException("//DUMMY");
                }

                @Override
                public void onSerialConnectError(Exception e) {
                    throw new RuntimeException("//DUMMY");
                }

                @Override
                public void onSerialRead(byte[] data) {
                    throw new RuntimeException("//DUMMY");
                }

                @Override
                public void onSerialRead(ArrayDeque<byte[]> datas) {
                    throw new RuntimeException("//DUMMY");
                }

                @Override
                public void onSerialIoError(Exception e) {
                    throw new RuntimeException("//DUMMY");
                }
            });
            this.socket = socket;
            connected = Connected.True;
        } catch (Exception e) {
            System.err.println("connection failed: " + e.getMessage());
            disconnect();
        }
    }

    @Override
    public void disconnect() {
        if (isConnected()) {
            connected = Connected.False; // ignore data,errors while disconnecting
            //cancelNotification(); //DUMMY
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
