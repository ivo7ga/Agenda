package com.example.agenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MiAdapter extends ArrayAdapter {

    Context context;
    int itemlayout;
    ArrayList<Evento> eventos;
    GoogleMap mapa;


    public MiAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Evento> objects) {
        super(context, resource, objects);
        this.context = context;
        this.itemlayout = resource;
        this.eventos = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(itemlayout, parent, false);
        }
        TextView titulo = convertView.findViewById(R.id.titulo);
        titulo.setText(eventos.get(position).getTitulo());
        TextView fechayhora = convertView.findViewById(R.id.fechayhora);
        fechayhora.setText(eventos.get(position).getFecha() + " " + eventos.get(position).getHora());
        return convertView;
    }


}
