package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.hw1.Data.DataManager;
import com.example.hw1.Data.Player;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class WinnerActivity extends AppCompatActivity {

    private TextView winnerActivity_LBL_score;
    private EditText winnerActivity_EDIT_nameTxt;
    private Button winnerActivity_BTN_enter;


    private GoogleApi googleApiClient;
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        setContentView(R.layout.activity_winner);

        findViews();
        initViews();

    }

    private void initViews() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Location", "No Permissions");
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            currentLocation = location;
                        } else {
                            Log.d("Location", "Failed getting location");
                        }
                    }
                });
        winnerActivity_LBL_score.setText(" " + GameActivity.getScore());
        winnerActivity_BTN_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLocation != null) {
                    double lat = currentLocation.getLatitude();
                    double lon = currentLocation.getLongitude();
                    int score = GameActivity.getScore();
                    String name = winnerActivity_EDIT_nameTxt.getText().toString();
                    Player p = new Player(name, score, lat, lon);
                    Log.d("winner", "adding player ");
                    DataManager.addPlayer(p);

                    // add player
                }
                Log.d("winner", "no location ");
                openRecordsActivity();
            }
        });

    }

    private void findViews() {
        winnerActivity_LBL_score = findViewById(R.id.winnerActivity_LBL_score);
        winnerActivity_EDIT_nameTxt = findViewById(R.id.winnerActivity_EDIT_nameTxt);
        winnerActivity_BTN_enter = findViewById(R.id.winnerActivity_BTN_enter);
    }

    public void openRecordsActivity() {
        Intent intent = new Intent(this, RecordsActivity.class);
        startActivity(intent);
        finish();
        }
    }

