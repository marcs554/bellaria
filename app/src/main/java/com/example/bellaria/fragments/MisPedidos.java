package com.example.bellaria.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bellaria.R;
import com.example.bellaria.adapters.PedidoAdapter;
import com.example.bellaria.model.Clientes;
import com.example.bellaria.model.Pedidos;
import com.example.bellaria.services.ClienteService;
import com.example.bellaria.services.EstadoConexion;
import com.example.bellaria.services.RetrofitInstancia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisPedidos extends Fragment {

    private static final String url = "https://bellaria.herokuapp.com/";

    private List<Pedidos> listaPedidos = new ArrayList<>();
    private Clientes cliente;
    private View view;
    private RecyclerView recyclerView;

    /**
     *
     * @param cliente
     */
    public MisPedidos(Clientes cliente) { this.cliente = cliente; }

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
        view = inflater.inflate(R.layout.fragment_mis_pedidos, container, false);
        recyclerView = view.findViewById(R.id.reciclerView_pedidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getPedidos();

        return view;
    }

    /**
     * Obtiene la lista de todos los pedidos del cliente que está loggeado en la aplicación.
     */
    private void getPedidos() {
        if(!EstadoConexion.checkConnectionServer(url)) {
            Toast.makeText(getContext(), "Conexión perdida", Toast.LENGTH_LONG).show();
        } else {
            ClienteService clienteService = RetrofitInstancia.retrofitllamada(url).create(ClienteService.class);

            Call<List<Pedidos>> callPedidos = clienteService.listarPedidos(cliente.getId());
            callPedidos.enqueue(new Callback<List<Pedidos>>() {
                /**
                 * Se almacena los pedidos obtenidos de un JSON en un arraylist para mostrarlos
                 * en el ReciclerView
                 * @param call
                 * @param response
                 */
                @Override
                public void onResponse(Call<List<Pedidos>> call, Response<List<Pedidos>> response) {
                    listaPedidos = response.body();

                    PedidoAdapter adapter = new PedidoAdapter(getContext(), (ArrayList<Pedidos>) listaPedidos);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<Pedidos>> call, Throwable t) {

                }
            });
        }
    }
}
