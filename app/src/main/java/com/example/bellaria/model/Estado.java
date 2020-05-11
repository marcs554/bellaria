package com.example.bellaria.model;

import java.io.Serializable;

public class Estado implements Serializable {

    private int id;
    private String nombreEstado;

    public Estado() { }

    /**
     *
     * @param id
     * @param nombreEstado
     */
    public Estado(int id, String nombreEstado) {
        this.id = id;
        this.nombreEstado = nombreEstado;
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
    public String getNombreEstado() { return nombreEstado; }

    /**
     *
     * @param nombreEstado
     */
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Estado{" +
                "id=" + id +
                ", nombreEstado='" + nombreEstado + '\'' +
                '}';
    }
}
