package com.example.hw1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MenuActivity extends AppCompatActivity {
    private Button menu_BTN_startSlow;
    private Button menu_BTN_startFast;
    private Button menu_BTN_startTilt;
    private Button menu_BTN_recordsTable;
    private static int speed;
    private static boolean tilt=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityResultLauncher<String[]> locationPermissionRequest =
                    registerForActivityResult(new ActivityResultContracts
                                    .RequestMultiplePermissions(), result -> {
                                Boolean fineLocationGranted = result.getOrDefault(
                                        Manifest.permission.ACCESS_FINE_LOCATION, false);
                                Boolean coarseLocationGranted = result.getOrDefault(
                                        Manifest.permission.ACCESS_COARSE_LOCATION,false);
                                if (fineLocationGranted != null && fineLocationGranted) {
                                    // Precise location access granted.
                                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                    // Only approximate location access granted.
                                } else {
                                    Log.d("Location", "No Location Permissions Granted");
                                }
                            }
                    );

            locationPermissionRequest.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
            Log.d("Location", "No Location Permissions");
        }
        findViews();
        initViews();
    }


    private void findViews() {
        menu_BTN_startSlow = findViewById(R.id.menu_BTN_startSlow);
        menu_BTN_startFast = findViewById(R.id.menu_BTN_startFast);
        menu_BTN_startTilt = findViewById(R.id.menu_BTN_startTilt);
        menu_BTN_recordsTable = findViewById(R.id.menu_BTN_recordsTable);
    }

    private void initViews() {
        menu_BTN_startSlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speed = 1200;
                tilt = false;
                openGameActivity();
            }
        });

        menu_BTN_startFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speed = 600;
                tilt = false;
                openGameActivity();
            }
        });

        menu_BTN_startTilt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speed = 900;
                tilt = true;
                openGameActivity();
            }
        });
        menu_BTN_recordsTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecordsActivity();
            }
        });

    }

    public void openGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void openRecordsActivity() {
        Intent intent = new Intent(this, RecordsActivity.class);
        startActivity(intent);
    }

    public static int getSpeed() {
        return speed;
    }

    public static boolean getTilt(){
        return tilt;
    }



}