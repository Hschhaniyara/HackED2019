/**
 * main page
 * allows user to navigate to different modes
 *
 * @author: Harshal Chhaniyara
 * @since: 1.0
 *
 * Copyright 2018 HSC
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package ca.ualberta.batbot;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout myLayout;
    AnimationDrawable animationDrawable;
    public Button button;
    public EditText editText;
//    public BluetoothAdapter btAdapter;
//    public BluetoothDevice btDevice;
     public BluetoothSocket btSocket;

//    public static final String SERVICE_ID = "00001101-0000-1000-8000-00805f9b34fb"; //SPP UUID
//    public static final String SERVICE_ADDRESS = "98:D3:61:FD:6E:CE"; // HC-05 BT ADDRESS


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        myLayout = (ConstraintLayout) findViewById(R.id.myLayout);

        animationDrawable = (AnimationDrawable) myLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();

        Button mapping = findViewById(R.id.map_button);
        Button remote = findViewById(R.id.remote_button);
        Button followMe = findViewById(R.id.follow_button);
        mapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent newprofile_intent = new Intent(MainActivity.this, newprofile.class);
//                startActivityForResult(newprofile_intent, 0);
            }
        });
        remote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, remote.class);
                startActivityForResult(intent, 0);

            }
        });
        followMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, transfer.class);
//                startActivity(intent);
                btSocket = DataHolder.getData();
                if (btSocket != null) {
                    try {
                        OutputStream out = btSocket.getOutputStream();
                        out.write(101);
                    } catch (IOException e) {

                    }
                }
            }
            });

//        button = (Button) findViewById(R.id.button);
//        editText = (EditText) findViewById(R.id.editText);
//        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//        if(pairedDevices.size() > 0)
//        {
//            for(BluetoothDevice device : pairedDevices)
//            {
//                if(device.getName().equals("HC-05"))
//                {
//                    btDevice = device;
//                    break;
//                }
//            }
//        }
//        //btAdapter = BluetoothAdapter.getDefaultAdapter();
//        //btDevice = btAdapter.getRemoteDevice(SERVICE_ADDRESS);
//        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
//
//        //UUID uuid = UUID.fromString(SERVICE_ID);
//
//        try {
//            btSocket = btDevice.createInsecureRfcommSocketToServiceRecord(uuid);//create a RFCOMM (SPP) connection
//        } catch (Exception e) {
//
//        }
//        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
//        try{
//            btSocket.connect();
//
//        } catch (Exception e) {
//            Log.e("OOOPS", "Could not connect");
//        }
//
//        if(btAdapter == null) {
//            Toast.makeText(getApplicationContext(), "Bluetooth not available", Toast.LENGTH_LONG).show();
//        } else {
//            if(!btAdapter.isEnabled()) {
//                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableIntent, 3);
//            } else {
//                ConnectThread connectThread = new ConnectThread(btDevice);
//                connectThread.start();
//            }
//        }
//
//        DataHolder.setData(btSocket);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(btSocket != null) {
//
//                    try{
//
//                        OutputStream out = btSocket.getOutputStream();
//                        out.write(editText.getText().toString().getBytes());
//                        Log.e("hello",(editText.getText().toString() + "\r\n"));
////                        btSocket.getOutputStream().write("0".toString().getBytes());
//                    }catch(IOException e) {
//
//                    }
//
//                }
//            }
//        });

    }

//    private class ConnectThread extends Thread {
//        private final BluetoothSocket thisSocket;
//        private final BluetoothDevice thisDevice;
//
//        public ConnectThread(BluetoothDevice device) {
//            BluetoothSocket tmp = null;
//            thisDevice = device;
//
//            try {
//                tmp = thisDevice.createRfcommSocketToServiceRecord(UUID.fromString(SERVICE_ID));
//            } catch (IOException e) {
//                Log.e("TEST", "Can't connect to service");
//            }
//            thisSocket = tmp;
//        }
//
//        public void run() {
//            // Cancel discovery because it otherwise slows down the connection.
//            btAdapter.cancelDiscovery();
//
//            try {
//                thisSocket.connect();
//                Log.d("TESTING", "Connected to shit");
//            } catch (IOException connectException) {
//                try {
//                    thisSocket.close();
//                } catch (IOException closeException) {
//                    Log.e("TEST", "Can't close socket");
//                }
//                return;
//            }
//
//            btSocket = thisSocket;
//
//        }
//        public void cancel() {
//            try {
//                thisSocket.close();
//            } catch (IOException e) {
//                Log.e("TEST", "Can't close socket");
//            }
//        }
//    }
}