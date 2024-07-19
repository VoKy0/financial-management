package com.example.financial_management_app.routes;

import com.example.financial_management_app.models.Wallets;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WalletRoute {
    @GET("/transaction/get")
    Call<List<Wallets>> getWallets();

    @POST("/transaction/create")
    Call<Wallets> createWallet(@Body Wallets wallet);

    @POST("/transaction/edit")
    Call<Wallets> updateWallet(@Body Wallets wallet);
}
