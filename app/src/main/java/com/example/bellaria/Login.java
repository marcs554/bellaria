package com.example.bellaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Login extends AppCompatActivity {

    private EditText usuario;
    private EditText password;

    private static final String url = "https://bellaria.herokuapp.com/";

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Definición de componentes.
        usuario = (EditText) findViewById(R.id.edt_login_user);
        password = (EditText) findViewById(R.id.edt_registro_password);
        Button login = findViewById(R.id.bttn_login_signin);
        TextView signUp = (TextView) findViewById(R.id.txv_login_signup);

        //Definición de las acciones que haran los botones.
        login.setOnClickListener(v -> login(usuario.getText().toString(),
                password.getText().toString()));
        signUp.setOnClickListener(v -> signUp());
    }

    /**
     * Cuando el usuario accione el boton "iniciar sesión" se cargará este metodo para
     * comprobar si el usuario y contraseña son validos.
     *
     * @param usuario
     * @param password
     */
    private void login(String usuario, String password) {
        /**
         * Se comprobará antes si hay conexión con el servidor, si no la hay se lanzará un dialog
         * avisando al usuario de que no hay conexión al servidor.
         */
        if (!EstadoConexion.checkConnectionServer(url))
            DialogInforme.dialogResultado(this, "Servidor no disponible.\n" +
                    "Compruebe su conexión a internet o inténtelo más tarde.");
        else {
            ClienteService clienteService = RetrofitInstancia.retrofitllamada(url).create(ClienteService.class);

            Call<List<Clientes>> call = clienteService.iniciarSesion(usuario, password);
            call.enqueue(new Callback<List<Clientes>>() {

                /**
                 * Si el login es correcto la app recibirá un JSON con los datos del usuario que
                 * serán almacenados en un array para ser posteriormente enviados al activity
                 * "ClienteDashboard" y al mismo tiempo si pasará a dicho activity.
                 * @param call
                 * @param response
                 */
                @Override
                public void onResponse(Call<List<Clientes>> call, Response<List<Clientes>> response) {

                    List<Clientes> listDataUser = response.body();

                    Intent panelCliente = new Intent(getApplicationContext(), ClienteDashboard.class);
                    panelCliente.putExtra("DataUser", (Serializable) listDataUser);
                    startActivityForResult(panelCliente, 0);
                }

                /**
                 * En caso de que el login sea incorrecto se lanzará un toast informando al usuario
                 * de que el usuario y contraseña son incorrectos.
                 * @param call
                 * @param t
                 */
                @Override
                public void onFailure(Call<List<Clientes>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Usuario y contraseña incorrectos",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    /**
     * Si el usuario acciona el TextView "Registrarse" se pasará al activity "activity_registro".
     */
    private void signUp() {
        Intent registrar = new Intent(this, Registro.class);
        startActivity(registrar);
    }
}
