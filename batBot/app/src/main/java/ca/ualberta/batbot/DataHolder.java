package ca.ualberta.batbot;

import android.bluetooth.BluetoothSocket;

public class DataHolder {
    private static BluetoothSocket data;
    public static BluetoothSocket getData() {return data;}
    public static void setData(BluetoothSocket btSocket) {DataHolder.data = btSocket;}
}
