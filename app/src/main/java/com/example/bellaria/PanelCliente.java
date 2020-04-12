package com.example.bellaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.example.bellaria.model.Productos;
import com.example.bellaria.network.ClienteService;
import com.example.bellaria.network.RetrofitInstancia;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanelCliente extends AppCompatActivity {

    private TextView pantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_cliente);

        pantalla = (TextView) findViewById(R.id.pantalla);
        final Button imprimir = findViewById(R.id.btn_imprimir), volver = findViewById(R.id.btn_volver);

        //boton imprimir para imprimir por pantalla la información
        imprimir.setOnClickListener(v -> imprimir());

        //boton volver
        volver.setOnClickListener(v -> volver());

    }

    /**
     * Regresa al activity_main
     */
    private void volver() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void imprimir() {
        StringBuilder stringBuilder = new StringBuilder();

        //Instancia la interfaz pasandole por parametro el builderRetrofit
        ClienteService clienteService = RetrofitInstancia.retrofitllamada().create(ClienteService.class);

        //Crea una lista a partir del json
        Call<List<Productos>> call = clienteService.listarProductos();

        call.enqueue(new Callback<List<Productos>>() {
            @Override
            public void onResponse(Call<List<Productos>> call, Response<List<Productos>> response) {

                if(!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "No hay conexión al servidor", Toast.LENGTH_LONG).show();
                    return;
                }

                List<Productos> productos = response.body();

                for(Productos p : productos) {
                    stringBuilder.append(p);
                }

                pantalla.setText(stringBuilder.toString());
            }

            @Override
            public void onFailure(Call<List<Productos>> call, Throwable t) {
                pantalla.setText(t.getMessage());
            }
        });
    }

}
