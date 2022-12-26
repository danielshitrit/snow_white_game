package com.example.hw1.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hw1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private LatLng latLng;
    private double lat;
    private double lon;
    private Marker oldMark;
    private boolean newLocation=false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        findViews(view);
        initViews();

        return view;
    }

    private void initViews() {
    }

    private void findViews(View view) {
        mapFragment =(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.fragmentMap_FRG_map);
        if(mapFragment != null)
            mapFragment.getMapAsync(this);
    }

    private void addMarkOnMap(GoogleMap googleMap){
        latLng = new LatLng(lat, lon);
        if(oldMark != null){
            oldMark.remove();
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        oldMark = googleMap.addMarker(new MarkerOptions().position(latLng).title("I'm there!"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    public void setLatLng(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        newLocation = true;
        if(newLocation){
            addMarkOnMap(googleMap);
            newLocation = false;
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        addMarkOnMap(googleMap);
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}