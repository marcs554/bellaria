package com.example.bellaria.network;

import com.example.bellaria.model.Clientes;
import com.example.bellaria.model.Pedidos;
import com.example.bellaria.model.Productos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ClienteService {

    @GET("login")
    Call<List<Clientes>> iniciarSesion(@Query("email") String email,
                                       @Query("password") String password);

    @GET("productos")
    Call<List<Productos>> listarProductos();

    @GET("pedidoscliente")
    Call<List<Pedidos>> listarPedidos(@Query("email") String email);

    @POST("nuevousuario")
    Call<List<Boolean>> nuevoUsuario(@Path("nombre") String nombreEmpresa,
                                     @Path("localizacion") String localizacion,
                                     @Path("email") String email,
                                     @Path("password") String password);

    @POST("nuevopedido")
    Call<List<Boolean>> nuevoPedido(@Path("cantidad") int cantidad,
                                    @Path("importe") float importe,
                                    @Path("idcliente") int idCliente,
                                    @Path("idproducto") int idProducto);

    @GET("anularpedido")
    Call<List<Boolean>> anularPedido(@Query("id") int idPedido);

}
