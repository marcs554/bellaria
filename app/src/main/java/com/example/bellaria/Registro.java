package com.example.bellaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bellaria.model.Clientes;
import com.example.bellaria.reutilizacion.DialogInforme;
import com.example.bellaria.services.ClienteService;
import com.example.bellaria.services.EstadoConexion;
import com.example.bellaria.services.RetrofitInstancia;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Registro extends AppCompatActivity {

    private static final String url = "https://bellaria.herokuapp.com/";

    private EditText nombreEmpresa, localizacion, email, password, pwdRepited;

    private Button registrarse, volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Definición de componentes.
        nombreEmpresa = (EditText) findViewById(R.id.edt_registro_nomEmpresa);
        localizacion = (EditText) findViewById(R.id.edt_registro_localizacion);
        email = (EditText) findViewById(R.id.edt_registro_email);
        password = (EditText) findViewById(R.id.edt_registro_password);
        pwdRepited = (EditText) findViewById(R.id.edt_registro_passwordAgain);

        registrarse = findViewById(R.id.bttn_registro_signUp);
        volver = findViewById(R.id.bttn_registro_return);

        //Definición de las acciones que haran los botones.
        registrarse.setOnClickListener(v -> checkData());
        volver.setOnClickListener(v -> volverMainActivity());
    }

    /**
     * Vuelve al activity Login.
     */
    private void volverMainActivity() {
        Intent volver = new Intent(this, Login.class);
        startActivity(volver);
    }

    /**
     * Comprueba si los campos están vacíos y si las contraseñas coinciden.
     */
    private void checkData() {
        if (nombreEmpresa.getText().toString().isEmpty() | localizacion.getText().toString().isEmpty() |
                email.getText().toString().isEmpty() | password.getText().toString().isEmpty()) {

            Toast.makeText(this, "Error: No se permiten campos vacíos", Toast.LENGTH_LONG).show();
        } else if (!password.getText().toString().equals(pwdRepited.getText().toString())) {

            Toast.makeText(this, "Error: Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        } else {
            enviarDatos(nombreEmpresa.getText().toString(), localizacion.getText().toString(),
                    email.getText().toString(), password.getText().toString());
        }

    }

    /**
     * En esta función se envian los datos de la nueva cuenta que se va a registrar al servidor.
     * @param nombreEmp
     * @param localiz
     * @param mail
     * @param passwd
     */
    private void enviarDatos(String nombreEmp, String localiz, String mail, String passwd) {
        if (!EstadoConexion.checkConnectionServer(url)) {
            DialogInforme.dialogResultado(this, "Servidor no disponible.\n" +
                    "Compruebe su conexión a internet o inténtelo más tarde.");
        } else {
            ClienteService clienteService = RetrofitInstancia.retrofitllamada(url).create(ClienteService.class);
            Call<List<Clientes>> call = clienteService.nuevoUsuario(nombreEmp, localiz, mail, passwd);
            Context context = this;

            call.enqueue(new Callback<List<Clientes>>() {

                @Override
                public void onResponse(Call<List<Clientes>> call, Response<List<Clientes>> response) {
                    if (response.isSuccessful()) {
                        List<Clientes> dataUser = response.body();

                        Intent intentDashBoard = new Intent(Registro.this, ClienteDashboard.class);
                        intentDashBoard.putExtra("DataUser", (Serializable) dataUser);
                        startActivity(intentDashBoard);

                    } else {
                        /**
                         * En caso de que la conexión con el servidor se interrumpiera durante el
                         * proceso mostrará por pantalla un dialog informando de que no se creó la
                         * cuenta.
                         */
                        DialogInforme.dialogResultado(context, "No se añadió correctamente, " +
                                "vuelva a intentarlo");
                    }
                }

                /**
                 * Si el nombreEmpresa y el email ya existen en la base de datos el servidor devolverá
                 * un objeto null que desencadenará el (onFailure) mostrando un dialog por pantalla que el
                 * nombre y el email ya existen.
                 * @param call
                 * @param t
                 */
                @Override
                public void onFailure(Call<List<Clientes>> call, Throwable t) {
                    DialogInforme.dialogResultado(context, "El nombre de empresa y email ya existen");
                }
            });
        }
    }

}


