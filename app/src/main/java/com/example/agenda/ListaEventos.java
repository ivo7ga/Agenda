package com.example.agenda;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ListaEventos {
    public ArrayList<Evento> eventos;

    public ListaEventos() {
        eventos = new ArrayList<>();
    }

    public String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public ListaEventos fromJson(String json) {
        Gson gson = new Gson();
        ListaEventos listaEventos = gson.fromJson(json, ListaEventos.class);
        return listaEventos;
    }
}
