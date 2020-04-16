package com.example.bellaria.model;

import java.io.Serializable;

public class Pedidos implements Serializable {

    private int id;
    private String fechaPedido;
    private int cantidad;
    private float importe;
    private Clientes cliente;
    private Productos productos;
    private Estado estado;

    public Pedidos() {
    }

    public Pedidos(int id, String fechaPedido, int cantidad, float importe, Clientes cliente, Productos productos, Estado estado) {
        this.id = id;
        this.fechaPedido = fechaPedido;
        this.cantidad = cantidad;
        this.importe = importe;
        this.cliente = cliente;
        this.productos = productos;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Productos getProductos() {
        return productos;
    }

    public void setProductos(Productos productos) {
        this.productos = productos;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
