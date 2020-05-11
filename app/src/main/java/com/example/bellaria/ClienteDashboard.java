package com.example.bellaria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.bellaria.fragments.Home;
import com.example.bellaria.fragments.MisPedidos;
import com.example.bellaria.model.Clientes;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ClienteDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String url = "https://bellaria.herokuapp.com/";

    private List<Clientes> userData = new ArrayList<Clientes>();

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Clientes cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_dashboard);

        /**
         * Obtiene los datos del usuario que inició sesión o se registró y se almacenan en una
         * variable de la clase Clientes
         */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userData = (List<Clientes>) extras.getSerializable("DataUser");
            cliente = userData.get(0);
        } else {
            userData = null;
        }


        //Declaración de elementos de la parte grafica
        drawerLayout = findViewById(R.id.drawer_dashboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Establece los parametros nombreEmpresa y email en los TextViews de la cabecera del menú
        //lateral
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);
        TextView userNameHeader = (TextView) headerView.findViewById(R.id.txv_headerDrawer_nombreEmpresa);
        userNameHeader.setText(cliente.getNombreEmpresa());
        TextView emailHeader = (TextView) headerView.findViewById(R.id.txv_headerDrawer_email);
        emailHeader.setText(cliente.getEmail());

        navigationView.setNavigationItemSelectedListener(this);


        //menu lateral
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //Carga el fragment por defecto(home)
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new Home(cliente));
        fragmentTransaction.commit();

    }

    /**
     * En esta función se llevarán a acabo las diferentes acciones dependiendo de la opción que
     * se seleccione en el menú lateral de la aplicación.
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            //Inicia el fragmento "Inicio"
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new Home(cliente));
            fragmentTransaction.commit();

        } else if (item.getItemId() == R.id.misPedidos) {
            //Inicia el fragmento "Mis pedidos"
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new MisPedidos(cliente));
            fragmentTransaction.commit();

        } else if (item.getItemId() == R.id.logOut) {
            //Cierra sesión
            logOut();
        }

        return true;
    }

    /**
     * Cierra sesión y regresa a la pantalla del login.
     */
    private void logOut() {
        Intent intentLogOut = new Intent(ClienteDashboard.this, Login.class);
        startActivity(intentLogOut);
    }


}
