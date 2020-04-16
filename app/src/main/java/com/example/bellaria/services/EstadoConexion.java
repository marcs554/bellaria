package com.example.bellaria.services;

import android.os.StrictMode;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class EstadoConexion {

    /**
     * Comprueba si el dispositivo puede conectarse al servidor web.
     * @param url
     * @return
     */
    public static boolean checkConnectionServer(String url) {
        int code = 0;
        boolean respuesta = false;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            code = httpURLConnection.getResponseCode();
            if(code == 200)
                respuesta = true;

            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respuesta;
    }
}
