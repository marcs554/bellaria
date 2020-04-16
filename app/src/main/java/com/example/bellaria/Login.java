package com.example.bellaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bellaria.model.Clientes;
import com.example.bellaria.network.ClienteService;
import com.example.bellaria.network.EstadoConexion;
import com.example.bellaria.network.RetrofitInstancia;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText usuario;
    private EditText password;

    private static final String url = "https://bellaria.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.edt_user);
        password = (EditText) findViewById(R.id.edt_password);
        final Button login = findViewById(R.id.btn_login);
        final TextView signUp = (TextView) findViewById(R.id.txv_signup);

        login.setOnClickListener(v -> login(usuario.getText().toString(),
                password.getText().toString()));
        signUp.setOnClickListener(v -> signUp());
    }

    /**
     *
     * @param usuario
     * @param password
     */
    private void login(String usuario, String password) {
        if(!EstadoConexion.checkConnectionServer(url))
            Toast.makeText(Login.this, "Servidor no disponible", Toast.LENGTH_LONG).show();
        else {

        ClienteService clienteService = RetrofitInstancia.retrofitllamada(url).create(ClienteService.class);

        Call<List<Clientes>> call = clienteService.iniciarSesion(usuario, password);
        call.enqueue(new Callback<List<Clientes>>() {
            @Override
            public void onResponse(Call<List<Clientes>> call, Response<List<Clientes>> response) {

                List<Clientes> listDataUser = response.body();

                Intent panelCliente = new Intent(getApplicationContext(), ClienteDashboard.class);
                panelCliente.putExtra("DataUser", (Serializable) listDataUser);
                startActivityForResult(panelCliente, 0);
            }

            @Override
            public void onFailure(Call<List<Clientes>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Usuario y contraseña incorrectos", Toast.LENGTH_LONG).show();
            }
        });
        }
    }

    /**
     *
     */
    private void signUp() {
        Intent registrar = new Intent(this, Registro.class);
        startActivity(registrar);
    }
}
