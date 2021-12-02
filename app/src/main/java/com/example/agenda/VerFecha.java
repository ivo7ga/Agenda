package com.example.agenda;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class VerFecha extends AppCompatActivity implements TimePicker.OnTimeChangedListener, DatePicker.OnDateChangedListener {

    TimePicker hora;
    DatePicker fecha;
    Button elegir;
    String horaString;
    String fechaString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_fecha);

        hora = findViewById(R.id.hora);
        fecha = findViewById(R.id.fecha);
        elegir = findViewById(R.id.elegir);
        hora.setOnTimeChangedListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fecha.setOnDateChangedListener(this);
        }
    }

    public void crearEvento(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        if ((horaString != null) && (fechaString != null)) {
            intent.putExtra("hora a enviar", horaString);
            intent.putExtra("fecha a enviar", fechaString);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Debes de clickar una hora y una fecha", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(this, hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
        horaString = hourOfDay + ":" + minute;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(this, dayOfMonth + "/" + monthOfYear + "/" + year, Toast.LENGTH_SHORT).show();
        fechaString = dayOfMonth + "/" + monthOfYear + "/" + year;
    }


}
