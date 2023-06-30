package io.github.edufolly.flutterbluetoothserial.le;

import io.github.edufolly.flutterbluetoothserial.BuildConfig;

public class Constants {

    // values have to be globally unique
    public static final String INTENT_ACTION_DISCONNECT = BuildConfig.LIBRARY_PACKAGE_NAME + ".Disconnect";
    public static final String NOTIFICATION_CHANNEL = BuildConfig.LIBRARY_PACKAGE_NAME + ".Channel";
    public static final String INTENT_CLASS_MAIN_ACTIVITY = BuildConfig.LIBRARY_PACKAGE_NAME + ".MainActivity";

    // values have to be unique within each app
    public static final int NOTIFY_MANAGER_START_FOREGROUND_SERVICE = 1001;

    private Constants() {}
}
