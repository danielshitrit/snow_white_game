package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hw1.Data.DataManager;
import com.example.hw1.Fragments.MapsFragment;
import com.example.hw1.Fragments.listFragment;
import com.example.hw1.Interfaces.TableCallBack;

public class RecordsActivity extends AppCompatActivity {

    private listFragment listFragment;
    private MapsFragment mapFragment;
    private Button records_BTN_backToMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        DataManager.loadTopPlayers(this);
        findViews();
        initViews();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataManager.saveTopPlayers(this);
    }

    private TableCallBack tableCallBack= new TableCallBack() {
        @Override
        public void showLocation(double lat, double lon, String name) {
            mapFragment.setLatLng(lat,lon);
        }
    };


    private void initViews() {
        listFragment = new listFragment();
        mapFragment = new MapsFragment();

        listFragment.setTableCallBack(tableCallBack);
        getSupportFragmentManager().beginTransaction().add(R.id.records_FRAMELAY_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.records_FRAMELAY_map, mapFragment).commit();

        records_BTN_backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuActivity();
                finish();
            }
        });
    }

    private void findViews() {
        records_BTN_backToMenu = findViewById(R.id.records_BTN_backToMenu);
    }


    public void openMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}