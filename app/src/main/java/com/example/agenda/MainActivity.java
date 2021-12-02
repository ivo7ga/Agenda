package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class MainActivity extends AppCompatActivity {

    EditText titulo;
    EditText lugar;
    Button creado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titulo = findViewById(R.id.titulo);
        lugar = findViewById(R.id.lugar);
        creado = findViewById(R.id.creado);


    }

    public void creadoDelTodo(View view) {

        Intent intentFecha = getIntent();
        Intent intent = new Intent(this, Main.class);

        String hora = intentFecha.getStringExtra("hora a enviar");
        String fecha = intentFecha.getStringExtra("fecha a enviar");

        String titulillo = titulo.getText().toString();
        String lugarcillo = lugar.getText().toString();
        if ((titulillo.isEmpty()) || (lugarcillo.isEmpty())) {
            Toast.makeText(this, "Debes de introducir un título y un Lugar válido", Toast.LENGTH_SHORT).show();
        } else {
            intent.putExtra("titulo a enviar", titulillo);
            intent.putExtra("lugar a enviar", lugarcillo);
            intent.putExtra("hora a enviar", hora);
            intent.putExtra("fecha a enviar", fecha);
            startActivity(intent);
            finish();

        }
    }

}