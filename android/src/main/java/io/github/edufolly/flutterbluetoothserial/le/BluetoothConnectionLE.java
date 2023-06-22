package io.github.edufolly.flutterbluetoothserial.le;

import java.io.IOException;
import java.util.UUID;

import io.github.edufolly.flutterbluetoothserial.BluetoothConnection;

public class BluetoothConnectionLE implements BluetoothConnection {
    @Override
    public boolean isConnected() {
        asdf;
    }

    @Override
    public void connect(String address, UUID uuid) throws IOException {

    }

    @Override
    public void connect(String address) throws IOException {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void write(byte[] data) throws IOException {

    }

    @Override
    public void onRead(byte[] data) {

    }

    @Override
    public void onDisconnected(boolean byRemote) {

    }
}
