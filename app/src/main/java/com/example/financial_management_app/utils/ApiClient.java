package com.example.financial_management_app.utils;

import com.example.financial_management_app.routes.TransactionRoute;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://fmapp.mobile/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static TransactionRoute getTransactionRoute() {
        return getClient().create(TransactionRoute.class);
    }
}