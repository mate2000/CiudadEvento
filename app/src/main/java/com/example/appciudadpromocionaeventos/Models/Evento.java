package com.example.appciudadpromocionaeventos.Models;

public class Evento {
    private String nombre;

    public Evento() {}

    public Evento(String nombreEvento) {
        this.nombre = nombreEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombreEvento(String nombre) {
        this.nombre = nombre;
    }
}
