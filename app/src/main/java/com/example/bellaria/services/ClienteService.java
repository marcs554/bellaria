package com.example.bellaria.services;

import com.example.bellaria.model.Clientes;
import com.example.bellaria.model.Pedidos;
import com.example.bellaria.model.Productos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ClienteService {

    /**
     * Envia el email y la contraseña del usuario al servidor para validación y se recibirá de vuelta
     * un JSON con todos los datos del usuario.
     * @param email
     * @param password
     * @return
     */
    @GET("login")
    Call<List<Clientes>> iniciarSesion(@Query("email") String email,
                                       @Query("password") String password);

    /**
     * Retornará un JSON de todos los productos.
     * @return
     */
    @GET("productos")
    Call<List<Productos>> listarProductos();

    /**
     * Retornará un JSON de los pedidos del cliente pasando por parametro su id.
     * @param id
     * @return
     */
    @GET("pedidoscliente")
    Call<List<Pedidos>> listarPedidos(@Query("idcliente") int id);

    /**
     * Envía al servidor pasando por parametro el: nombre, localización, email, password y retornará
     * un JSON con la información del usuario (Se usa cuando el usuario quiere registrar una cuenta
     * nueva).
     * @param nombreEmpresa
     * @param localizacion
     * @param email
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("nuevousuario")
    Call<List<Clientes>> nuevoUsuario(@Field("nombre") String nombreEmpresa,
                                     @Field("localizacion") String localizacion,
                                     @Field("email") String email,
                                     @Field("password") String password);

    /**
     * Envía al servidor pasando por parametro el: importe, cantidad, idcliente, idproducto y
     * retornará un JSON de todos los pedidos del cliente.
     * @param cantidad
     * @param importe
     * @param idCliente
     * @param idProducto
     * @return
     */
    @FormUrlEncoded
    @POST("nuevopedido")
    Call<List<Pedidos>> nuevoPedido(@Field("cantidad") float cantidad,
                                    @Field("importe") float importe,
                                    @Field("idcliente") int idCliente,
                                    @Field("idproducto") int idProducto);

    /**
     * Envía al servidor pasando por parametro el idpedido y retornará un JSON de todos los pedidos
     * del cliente.
     * @param idPedido
     * @return
     */
    @PATCH("anularpedido")
    Call<List<Pedidos>> anularPedido(@Query("idpedido") int idPedido);

}
