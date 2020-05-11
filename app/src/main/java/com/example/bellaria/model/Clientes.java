package com.example.bellaria.model;

import java.io.Serializable;

public class Clientes implements Serializable {
    private int id;
    private String nombreEmpresa;
    private String localizacion;
    private String email;
    private String password;

    public Clientes() {}

    /**
     *
     * @param id
     * @param nombre
     * @param localizacion
     * @param email
     * @param password
     */
    public Clientes(int id, String nombre, String localizacion, String email, String password) {
        this.id = id;
        this.nombreEmpresa = nombre;
        this.localizacion = localizacion;
        this.email = email;
        this.password = password;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    /**
     *
     * @param nombreEmpresa
     */
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     *
     * @return
     */
    public String getLocalizacion() { return localizacion; }

    /**
     *
     * @param localizacion
     */
    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Clientes{" +
                "id=" + id +
                ", nombre='" + nombreEmpresa + '\'' +
                ", localizacion='" + localizacion + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
