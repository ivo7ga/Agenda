package com.example.agenda;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Main extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemClickListener {

    MiAdapter adapter;
    ListView list;
    Button crearevento;
    String titulo;
    String lugar;
    String hora;
    String fecha;
    private GoogleMap mapa;
    SharedPreferences sharedPreferences;
    ListaEventos listaEventos;
    Boolean bool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        list = findViewById(R.id.list);
        crearevento = findViewById(R.id.crear);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        sharedPreferences = getSharedPreferences("Nombres", MODE_PRIVATE);
        String json = sharedPreferences.getString("eventos", "");


        Intent intent = getIntent();
        titulo = intent.getStringExtra("titulo a enviar");
        lugar = intent.getStringExtra("lugar a enviar");
        hora = intent.getStringExtra("hora a enviar");
        fecha = intent.getStringExtra("fecha a enviar");


        if (!json.isEmpty()) {
            listaEventos = new ListaEventos();
            listaEventos = listaEventos.fromJson(json);
            adapter = new MiAdapter(this, R.layout.item, listaEventos.eventos);
            list.setAdapter(adapter);
        } else {
            listaEventos = new ListaEventos();
        }


        if (titulo != null && hora != null && fecha != null && lugar != null) {

            listaEventos.eventos.add(new Evento(titulo, fecha, hora, lugar));
            adapter = new MiAdapter(this, R.layout.item, listaEventos.eventos);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("eventos", listaEventos.toJson());
            editor.apply();

            bool = false;
            list.setOnItemClickListener(this);

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        LatLng empiezaMapa = new LatLng(40, -3);
        mapa.moveCamera(CameraUpdateFactory.newLatLng(empiezaMapa));
        mapa.moveCamera(CameraUpdateFactory.zoomTo(8));
        localizacionActivada();
        if (lugar != null) {
            geocode(lugar);
        }
    }


    public void localizacionActivada() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permiso = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permiso, 123);
            return;
        }
        mapa.setMyLocationEnabled(true);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            localizacionActivada();
        }
    }

    public void geocode(String lugar) {
        Geocoder geo = new Geocoder(this);
        try {
            List<Address> direcciones = geo.getFromLocationName(lugar, 1);
            if (direcciones.size() != 0) {
                Address direccion = direcciones.get(0);
                LatLng latLng = new LatLng(direccion.getLatitude(), direccion.getLongitude());
                mapa.addMarker(new MarkerOptions().position(latLng).title(direccion.getLocality()));
                mapa.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void cambiarFecha(View view) {
        Intent intent = new Intent(this, VerFecha.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (bool == false) {
            geocode(lugar);
            bool = true;
            adapter.notifyDataSetChanged();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("eventos", listaEventos.toJson());
            editor.apply();

        } else {
            listaEventos.eventos.remove(position);
            bool = false;
            adapter.notifyDataSetChanged();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("eventos", listaEventos.toJson());
            editor.apply();

        }
    }
}