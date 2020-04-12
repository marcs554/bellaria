package com.example.bellaria.model;

public class Estado {

    private int id;
    private String nombreEstado;

    public Estado() {
    }

    public Estado(int id, String nombreEstado) {
        this.id = id;
        this.nombreEstado = nombreEstado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}
