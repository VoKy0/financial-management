package com.example.financial_management_app.routes;

import com.example.financial_management_app.models.Transactions;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TransactionRoute {
    @GET("/get")
    Call<List<Transactions>> getTransactions();

    @POST("/create")
    Call<Transactions> createTransaction(@Body Transactions transaction);
}
