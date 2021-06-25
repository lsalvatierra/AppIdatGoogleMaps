package edu.pe.idat.appidatgooglemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import edu.pe.idat.appidatgooglemaps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnpermisogps.setOnClickListener(view ->{
            if(validarPermisoGPS()){
                solicitarPermisoGPS();
            }else{
                startActivity(new Intent(getApplicationContext(),
                        MapsActivity.class));
            }
        });
    }



    private boolean validarPermisoGPS(){
        /**Obtenemos el estado actual del Permiso*/
        int permiso = ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
        );
        return permiso == PackageManager.PERMISSION_GRANTED;
    }


    private void solicitarPermisoGPS(){
        String[] permiso = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(
                this,
                permiso,
                0
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(getApplicationContext(),
                    MapsActivity.class));

        } else {
            /**Mostramos un mensaje al usuario*/
            Toast.makeText(getApplicationContext(),
                    "No podrá hacer uso de la su GPS",
                    Toast.LENGTH_LONG).show();

        }

    }
}