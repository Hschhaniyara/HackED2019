package ca.ualberta.batbot;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class remote extends AppCompatActivity {
    public static final String SERVICE_ID = "00001101-0000-1000-8000-00805f9b34fb"; //SPP UUID
    public BluetoothAdapter btAdapter;
    public BluetoothDevice btDevice;
    public BluetoothSocket btSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals("HC-05"))
                {
                    btDevice = device;
                    break;
                }
            }
        }
        //btAdapter = BluetoothAdapter.getDefaultAdapter();
        //btDevice = btAdapter.getRemoteDevice(SERVICE_ADDRESS);
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID

        //UUID uuid = UUID.fromString(SERVICE_ID);

        try {
            btSocket = btDevice.createInsecureRfcommSocketToServiceRecord(uuid);//create a RFCOMM (SPP) connection
        } catch (Exception e) {
        }
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        try{
            btSocket.connect();

        } catch (Exception e) {
        }

        if(btAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth not available", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth available", Toast.LENGTH_LONG).show();
            if(!btAdapter.isEnabled()) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, 3);
            } else {
                remote.ConnectThread connectThread = new remote.ConnectThread(btDevice);
                connectThread.start();
            }
        }

        DataHolder.setData(btSocket);
        JoystickView joystick = (JoystickView) findViewById(R.id.joystickView);
        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                if(btSocket != null) {
                    try{
                        OutputStream out = btSocket.getOutputStream();
                        out.write(angle);
                        out.write(strength);
                    }catch(IOException e) {
                    }
                }
//                if (strength < 25) {
//                    // motor 2 speed set to 0
//                }
//                if (strength >= 25 && strength < 50) {
//                    // motor 2 speed set 1
//                }
//                if (strength >= 50 && strength < 75) {
//                    // motor 2 speed set 2
//                }
//                if (strength >= 75 && strength < 101) {
//                    // motor 2 speed set 3
//                }
//
//                if (angle >= 60 && angle <= 120) {
//                    // motor wheel straight
//                    // motor 2 forward
//                }
//                if (angle >= 240 && angle <= 300) {
//                    // motor wheel straight
//                    // motor 2 back
//                }
//                if (angle >= 150 && angle <= 210) {
//                    // motor wheel full left
//                    // motor 2 front
//                }
//                if ((angle >= 0 && angle <= 30) || (angle >= 330 && angle <= 360)) {
//                    // motor wheel full right
//                    // motor 2 front
//                }
//                if (angle > 30 && angle < 60) {
//                    // motor wheel half right
//                    // motor 2 forward
//                }
//                if (angle > 120 && angle < 150) {
//                    // motor wheel half left
//                    // motor 2 forward
//                }
//                if (angle > 210 && angle < 240) {
//                    // motor wheel half right
//                    // motor 2 back
//                }
//                if (angle > 300 && angle < 330) {
//                    // motor wheel half left
//                    // motor 2 back
//                }
            }
        });
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket thisSocket;
        private final BluetoothDevice thisDevice;

        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            thisDevice = device;

            try {
                tmp = thisDevice.createRfcommSocketToServiceRecord(UUID.fromString(SERVICE_ID));
            } catch (IOException e) {
            }
            thisSocket = tmp;
        }

        public void run() {

            btAdapter.cancelDiscovery();

            try {
                thisSocket.connect();
            } catch (IOException connectException) {
                try {
                    thisSocket.close();
                } catch (IOException closeException) {
                }
                return;
            }

            btSocket = thisSocket;
        }

        public void cancel() {
            try {
                thisSocket.close();
            } catch (IOException e) {
            }
        }
    }
}
