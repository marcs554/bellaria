package com.example.bellaria.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstancia {

    private static Retrofit retrofit = null;

    public static Retrofit retrofitllamada() {

        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://bellaria.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
