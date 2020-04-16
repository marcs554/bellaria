package com.example.bellaria.model;

import java.io.Serializable;

public class Productos implements Serializable {

    private int id;
    private String nombre;
    private float precioActual;
    private String urlimagenProducto;

    public Productos() {

    }

    public Productos(int id, String nombre, float precioActual, String urlimagenProducto) {
        this.id = id;
        this.nombre = nombre;
        this.precioActual = precioActual;
        //this.URLImagenProducto = urlimagenProducto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(float precioActual) {
        this.precioActual = precioActual;
    }

   public String getUrlimagenProducto() {
        return urlimagenProducto;
    }

    public void setUrlimagenProducto(String urlimagenProducto) {
        this.urlimagenProducto = urlimagenProducto;
    }

    @Override
    public String toString() {
        return "Productos{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precioActual=" + precioActual +
                ", URLImagenProducto='" + urlimagenProducto + '\'' +
                '}';
    }
}
