package com.example.bellaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class Registro extends AppCompatActivity {

    private EditText nombreEmpresa;
    private EditText localizacion;
    private EditText email;
    private EditText password;
    private EditText pwdRepited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombreEmpresa = (EditText) findViewById(R.id.edt_nomEmpresa);
        localizacion = (EditText) findViewById(R.id.edt_localizacion);
        email = (EditText) findViewById(R.id.edt_email);
        password = (EditText) findViewById(R.id.edt_password);
        pwdRepited = (EditText) findViewById(R.id.edt_passwordAgain);
    }

    public void volverMainActivity(View view) {
        Intent volver = new Intent(this, Login.class);
        startActivity(volver);
    }

    public void registro(View view) {

        /*List<Clientes> clientes = new ArrayList<Clientes>();

        if(checkEmptyValue(nombreEmpresa,localizacion,email,password)) {
            Toast.makeText(this, "Error: No se permiten campos vacíos", Toast.LENGTH_LONG).show();
        } else if(passwordsEquals(password, pwdRepited)) {
            Toast.makeText(this, "Error: Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        } else {

        }*/

    }


    //Chequea si las contraseñas coinciden
    private boolean passwordsEquals(EditText password1, EditText password2) {
        if((password1.getText().toString()) == (password2.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    //Chequea si se cubrió el campo o no
    private boolean checkEmptyValue(EditText value1, EditText value2, EditText value3, EditText value4) {

        if((value1.getText().toString()).matches("") | value2.getText().toString().matches("") |
                value3.getText().toString().matches("") | value4.getText().toString().matches("")) {

            return true;
        } else {
            return false;
        }
    }
}


