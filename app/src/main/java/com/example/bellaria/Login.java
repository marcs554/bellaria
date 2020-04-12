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
import com.example.bellaria.network.RetrofitInstancia;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText usuario;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.edt_user);
        password = (EditText) findViewById(R.id.edt_password);
        final Button login = findViewById(R.id.btn_login);
        final TextView signUp = (TextView) findViewById(R.id.txv_signup);

        login.setOnClickListener(v -> login(usuario.toString(), password.toString()));
        signUp.setOnClickListener( v -> signUp());
    }

    private void login(String usuario, String password) {
        ClienteService clienteService = RetrofitInstancia.retrofitllamada().create(ClienteService.class);

        Call<List<Clientes>> call = clienteService.iniciarSesion(usuario,password);
        call.enqueue(new Callback<List<Clientes>>() {
            @Override
            public void onResponse(Call<List<Clientes>> call, Response<List<Clientes>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "No hay conexión con el servidor\nCódigo: "
                            + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

               if(response.body().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "email y contraseña incorrectos", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(getApplicationContext(), "CORRECTO", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Clientes>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

        /*Intent intent = new Intent(this, PanelCliente.class);
        startActivity(intent);*/
    }

    private void signUp() {
        Intent registrar = new Intent(this, Registro.class);
        startActivity(registrar);
    }
}
