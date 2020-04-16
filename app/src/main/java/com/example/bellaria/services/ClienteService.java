package com.example.bellaria.services;

import com.example.bellaria.model.Clientes;
import com.example.bellaria.model.Pedidos;
import com.example.bellaria.model.Productos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ClienteService {

    /**
     *
     * @param email
     * @param password
     * @return
     */
    @GET("login")
    Call<List<Clientes>> iniciarSesion(@Query("email") String email,
                                       @Query("password") String password);

    @GET("productos")
    Call<List<Productos>> listarProductos();

    @GET("pedidoscliente")
    Call<List<Pedidos>> listarPedidos(@Query("email") String email);

    @FormUrlEncoded
    @POST("nuevousuario")
    Call<List<Clientes>> nuevoUsuario(@Field("nombre") String nombreEmpresa,
                                     @Field("localizacion") String localizacion,
                                     @Field("email") String email,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("nuevopedido")
    Call<List<Boolean>> nuevoPedido(@Field("cantidad") int cantidad,
                                    @Field("importe") float importe,
                                    @Field("idcliente") int idCliente,
                                    @Field("idproducto") int idProducto);

    @GET("anularpedido")
    Call<List<Boolean>> anularPedido(@Query("id") int idPedido);

}
