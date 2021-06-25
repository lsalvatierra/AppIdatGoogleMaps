package edu.pe.idat.appidatgooglemaps;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import edu.pe.idat.appidatgooglemaps.databinding.ActivityMapsBinding;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng idatlimacentro = new LatLng(-12.122294, -77.028323);
        Marker markerSidney = mMap.addMarker(new
                MarkerOptions()
                .position(idatlimacentro)
                .title("IDAT-Lima Centro")
                .snippet("Ahora mismo no estamos aqui")
                .draggable(true)
                .flat(true)
                //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location))
        );
        //mMap.setTrafficEnabled(true);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        try {
            mMap.setMyLocationEnabled(true);
        }catch (SecurityException ex){

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(idatlimacentro, 18F));
        Polygon polygon = mMap.addPolygon(new PolygonOptions()
                .add(new LatLng(-12.114093, -77.036811),
                        new LatLng(-12.113846, -77.029859),
                        new LatLng(-12.118865, -77.029087),
                        new LatLng(-12.118912, -77.036962)));
        polygon.setStrokeColor(-0xc77c4);
    }

    @Override
    public void onMapClick(@NonNull  LatLng latLng) {
        mMap.addMarker(
                new MarkerOptions().position(latLng)
                        .title("Nuevo marker")
                        .draggable(true)

        );
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18F));

    }

    @Override
    public void onMarkerDragStart(@NonNull  Marker marker) {
        marker.hideInfoWindow();
    }

    @Override
    public void onMarkerDrag(@NonNull  Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {
        LatLng posicion = marker.getPosition();
        marker.setSnippet(posicion.latitude+", "+posicion.longitude);
        marker.setTitle("Nueva ubicación");
        marker.showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicion,18F));
    }
}