package com.gnm.myapplication.api;

import com.gnm.myapplication.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static Retrofit retrofit = null;

    public static Retrofit getApi() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
