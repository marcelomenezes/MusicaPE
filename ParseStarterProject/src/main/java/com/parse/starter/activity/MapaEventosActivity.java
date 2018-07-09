package com.parse.starter.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.starter.R;


import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;

public class MapaEventosActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Toolbar toolbar;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    public static final String TAG = MapaEventosActivity.class.getSimpleName();

    private ParseQuery<ParseObject> query;


    static final CameraPosition RECIFE = CameraPosition.builder()
            .target(new LatLng(-8.0966547,-34.9152525))
            .zoom(15)
            .bearing(0)
            .tilt(45)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_eventos);

        //configurar toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_perfil_config_evento);
        toolbar.setTitle("Mapa de Eventos");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

/*
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                CharSequence salva = place.getAddress();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

*/

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        flyTo(RECIFE);

        query = ParseQuery.getQuery("Evento");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null){//sucesso
                    //verifica se já existem eventos listados
                    if(objects.size()>0){
                        for (ParseObject parseObject : objects){
                            double latitude = Double.parseDouble(parseObject.getString("latitude"));
                            double longitude = Double.parseDouble(parseObject.getString("longitude"));
                            LatLng location = new LatLng(latitude, longitude);
                            mMap.addMarker( new MarkerOptions().position(location).title(parseObject.getString("nomeEvento").toString()));
                        }
                    }

                }else {//erro

                    e.printStackTrace();
                }
            }
        });

        // Add a marker in Sydney and move the camera
       // LatLng recife = new LatLng(0, 0);
        //mMap.addMarker(new MarkerOptions().position(recife).title("Marker in Sydney"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(recife));
    }

    private void flyTo(CameraPosition target)
    {
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(target), 10000, null);



    }

}
