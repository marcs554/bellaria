package com.example.bellaria.model;

import java.io.Serializable;

public class Pedidos implements Serializable {

    private int id;
    private String fechaPedido;
    private float cantidad;
    private float importe;
    private Clientes cliente;
    private Productos productos;
    private Estado estado;

    public Pedidos() { }

    /**
     *
     * @param id
     * @param fechaPedido
     * @param cantidad
     * @param importe
     * @param cliente
     * @param productos
     * @param estado
     */
    public Pedidos(int id, String fechaPedido, float cantidad, float importe, Clientes cliente,
                   Productos productos, Estado estado) {
        this.id = id;
        this.fechaPedido = fechaPedido;
        this.cantidad = cantidad;
        this.importe = importe;
        this.cliente = cliente;
        this.productos = productos;
        this.estado = estado;
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
    public String getFechaPedido() { return fechaPedido; }

    /**
     *
     * @param fechaPedido
     */
    public void setFechaPedido(String fechaPedido) { this.fechaPedido = fechaPedido; }

    /**
     *
     * @return
     */
    public float getCantidad() { return cantidad; }

    /**
     *
     * @param cantidad
     */
    public void setCantidad(float cantidad) { this.cantidad = cantidad; }

    /**
     *
     * @return
     */
    public float getImporte() { return importe; }

    /**
     *
     * @param importe
     */
    public void setImporte(float importe) { this.importe = importe; }

    /**
     *
     * @return
     */
    public Clientes getCliente() { return cliente; }

    /**
     *
     * @param cliente
     */
    public void setCliente(Clientes cliente) { this.cliente = cliente; }

    /**
     *
     * @return
     */
    public Productos getProductos() { return productos; }

    /**
     *
     * @param productos
     */
    public void setProductos(Productos productos) { this.productos = productos; }

    /**
     *
     * @return
     */
    public Estado getEstado() { return estado; }

    /**
     *
     * @param estado
     */
    public void setEstado(Estado estado) { this.estado = estado; }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Pedidos{" +
                "id=" + id +
                ", fechaPedido='" + fechaPedido + '\'' +
                ", cantidad=" + cantidad +
                ", importe=" + importe +
                ", cliente=" + cliente +
                ", productos=" + productos +
                ", estado=" + estado +
                '}';
    }
}
