package com.example.bellaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bellaria.model.Clientes;
import com.example.bellaria.model.Estado;
import com.example.bellaria.services.ClienteService;
import com.example.bellaria.services.EstadoConexion;
import com.example.bellaria.services.RetrofitInstancia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Registro extends AppCompatActivity {

    private static final String url = "https://bellaria.herokuapp.com/";

    private EditText nombreEmpresa;
    private EditText localizacion;
    private EditText email;
    private EditText password;
    private EditText pwdRepited;

    private Button registrarse;
    private Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombreEmpresa = (EditText) findViewById(R.id.edt_nomEmpresa);
        localizacion = (EditText) findViewById(R.id.edt_localizacion);
        email = (EditText) findViewById(R.id.edt_email);
        password = (EditText) findViewById(R.id.edt_password);
        pwdRepited = (EditText) findViewById(R.id.edt_passwordAgain);

        registrarse = findViewById(R.id.btn_signUp);
        volver = findViewById(R.id.btn_return);

        registrarse.setOnClickListener(v -> registro());
        volver.setOnClickListener(v -> volverMainActivity());
    }

    private void volverMainActivity() {
        Intent volver = new Intent(this, Login.class);
        startActivity(volver);
    }

    private void registro() {
        if(checkEmptyValue(nombreEmpresa,localizacion,email,password)) {
            Toast.makeText(this, "Error: No se permiten campos vacíos", Toast.LENGTH_LONG).show();
        } else if(passwordsEquals(password, pwdRepited)) {
            Toast.makeText(this, "Error: Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        } else {
            enviarDatos(nombreEmpresa.getText().toString(), localizacion.getText().toString(),
                    email.getText().toString(), password.getText().toString());
        }

    }

    private void enviarDatos(String nombreEmp, String localiz, String mail, String passwd) {
        if(!EstadoConexion.checkConnectionServer(url)) {
            Toast.makeText(Registro.this, "Servidor no disponible", Toast.LENGTH_LONG).show();
        } else {
            ClienteService clienteService = RetrofitInstancia.retrofitllamada(url).create(ClienteService.class);
            Call<List<Clientes>> call = clienteService.nuevoUsuario(nombreEmp, localiz, mail, passwd);

            call.enqueue(new Callback<List<Clientes>>() {
                @Override
                public void onResponse(Call<List<Clientes>> call, Response<List<Clientes>> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(Registro.this, "Se añadió correctamente", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Registro.this, "No se añadió correctamente", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Clientes>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }


    //Chequea si las contraseñas coinciden
    private boolean passwordsEquals(EditText password1, EditText password2) {
        return (password1.getText().toString()) == (password2.getText().toString());
    }

    //Chequea si se cubrió el campo o no
    private boolean checkEmptyValue(EditText value1, EditText value2, EditText value3, EditText value4) {
        return value1.getText().toString().matches("") | value2.getText().toString().matches("") |
                value3.getText().toString().matches("") | value4.getText().toString().matches("");
    }
}


