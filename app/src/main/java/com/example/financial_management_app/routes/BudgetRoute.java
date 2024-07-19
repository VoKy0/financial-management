package com.example.financial_management_app.routes;

import com.example.financial_management_app.models.Budgets;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BudgetRoute {
    @GET("/transaction/get")
    Call<List<Budgets>> getBudgets();

    @POST("/transaction/create")
    Call<Budgets> createBudget(@Body Budgets budget);

    @POST("/transaction/edit")
    Call<Budgets> updateBudget(@Body Budgets budget);
}
