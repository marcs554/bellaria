package com.example.bellaria.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bellaria.R;
import com.example.bellaria.adapters.ProductoAdapter;
import com.example.bellaria.model.Clientes;
import com.example.bellaria.model.Productos;
import com.example.bellaria.reutilizacion.DialogInforme;
import com.example.bellaria.services.ClienteService;
import com.example.bellaria.services.EstadoConexion;
import com.example.bellaria.services.RetrofitInstancia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    private static final String url = "https://bellaria.herokuapp.com/";

    private RecyclerView recyclerView;
    private View view;
    private List<Productos> listaProductos = new ArrayList<Productos>();
    private Clientes cliente;

    public Home(){}

    /**
     *
     * @param cliente
     */
    public Home(Clientes cliente) { this.cliente = cliente; }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Definición de componentes.
        view = inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView = view.findViewById(R.id.reciclerView_productos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getProductos();

        return view;
    }

    /**
     * Obtiene del servidor una lista de todos los productos almacenados.
     */
    private void getProductos() {
        if(!EstadoConexion.checkConnectionServer(url)) {
            DialogInforme.dialogResultado(getContext(), "Conexión perdida");
        } else {
            ClienteService clienteService = RetrofitInstancia.retrofitllamada(url).create(ClienteService.class);

            Call<List<Productos>> callProductos = clienteService.listarProductos();
            callProductos.enqueue(new Callback<List<Productos>>() {
                /**
                 * Se almacena los productos obtenidos de un JSON en un arraylist para
                 * mostrarlos en el ReciclerView
                 * @param call
                 * @param response
                 */
                @Override
                public void onResponse(Call<List<Productos>> call, Response<List<Productos>> response) {
                    listaProductos = response.body();
                    ProductoAdapter adapter = new ProductoAdapter((ArrayList<Productos>) listaProductos, view.getContext(), cliente);
                    recyclerView.setAdapter(adapter);

                }

                @Override
                public void onFailure(Call<List<Productos>> call, Throwable t) {

                }
            });
        }
    }


}
