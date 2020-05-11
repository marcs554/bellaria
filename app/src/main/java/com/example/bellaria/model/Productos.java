package com.example.bellaria.model;

import java.io.Serializable;

public class Productos implements Serializable {

    private int id;
    private String nombre;
    private float precioActual;
    private String urlimagenProducto;

    public Productos() { }

    /**
     *
     * @param id
     * @param nombre
     * @param precioActual
     * @param urlimagenProducto
     */
    public Productos(int id, String nombre, float precioActual, String urlimagenProducto) {
        this.id = id;
        this.nombre = nombre;
        this.precioActual = precioActual;
        this.urlimagenProducto = urlimagenProducto;
    }

    /**
     *
     * @return
     */
    public int getId() { return id; }

    /**
     *
     * @param id
     */
    public void setId(int id) { this.id = id; }

    /**
     *
     * @return
     */
    public String getNombre() { return nombre; }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     *
     * @return
     */
    public float getPrecioActual() { return precioActual; }

    /**
     *
     * @param precioActual
     */
    public void setPrecioActual(float precioActual) { this.precioActual = precioActual; }

    /**
     *
     * @return
     */
    public String getUrlimagenProducto() { return urlimagenProducto; }

    /**
     *
     * @param urlimagenProducto
     */
    public void setUrlimagenProducto(String urlimagenProducto) { this.urlimagenProducto = urlimagenProducto; }

    /**
     *
     * @return
     */
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
