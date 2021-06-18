package edu.pe.idat.appidatgooglemaps;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng idatlimacentro = new LatLng(-12.066877,-77.035721);
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
        mMap.setTrafficEnabled(true);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(idatlimacentro, 18F));
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