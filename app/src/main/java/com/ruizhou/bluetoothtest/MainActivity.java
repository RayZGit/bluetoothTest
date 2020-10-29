package com.ruizhou.bluetoothtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//ECE496 Sensation Group
public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter ba;
    private AlertDialog.Builder alert;

    private Button turnOn;
    private Button turnOff;
    private Button getVisible;
    private Button listDevices;

    private Set<BluetoothDevice> pairDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bluetooth backend adapter
        ba = BluetoothAdapter.getDefaultAdapter();
        alert = new AlertDialog.Builder(MainActivity.this);
        //Bluetooth UI
        turnOff = (Button) findViewById(R.id.turnoffButton);
        turnOn = (Button) findViewById(R.id.turnonButton);
        getVisible = (Button) findViewById(R.id.visibleButton);
        listDevices = (Button) findViewById(R.id.deviceButton);

        alert.setTitle("List of Items");
        alert.setNeutralButton(getResources().getString(R.string.close),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });


        turnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ba.enable()){
                    Toast.makeText(getApplicationContext(), "Already On", Toast.LENGTH_SHORT).show();
                } else{
                    Intent turnOnApp = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOnApp,0);
                    Toast.makeText(getApplicationContext(), "Turn On", Toast.LENGTH_SHORT).show();
                }
            }
        });
        turnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ba.disable();
                Toast.makeText(getApplicationContext(), "Turn off", Toast.LENGTH_SHORT).show();
            }
        });

        getVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent visible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(visible, 0);
            }
        });

        listDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pairDevices = ba.getBondedDevices();

                List<String> list = new ArrayList<>();
                for(BluetoothDevice bt: pairDevices) list.add(bt.getName());
                String[] arr = new String[list.size()];
                //arr = list.toArray(arr);
                alert.setItems((CharSequence[]) list.toArray(arr), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });


    }
}