package com.example.financial_management_app.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financial_management_app.models.Transactions;
import com.example.financial_management_app.models.Wallets;
import com.example.financial_management_app.routes.TransactionRoute;
import com.example.financial_management_app.utils.ApiClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionViewModel extends ViewModel {
    private MutableLiveData<List<Transactions>> transactionItems;
    private MutableLiveData<Boolean> transactionAdded;
    private ExecutorService executorService;
    private TransactionRoute transactionRoute;


    public TransactionViewModel() {
        transactionItems = new MutableLiveData<>();
        transactionAdded = new MutableLiveData<>(false);
        executorService = Executors.newSingleThreadExecutor();
        transactionRoute = ApiClient.getTransactionRoute();
    }
    public LiveData<List<Transactions>> getTransactionItems() {
        return transactionItems;
    }
    public LiveData<Boolean> getTransactionAdded() {
        return transactionAdded;
    }

//    public void loadTransactionItems() {
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                long startTime = System.currentTimeMillis();
//
//                Call<List<Transactions>> call = transactionRoute.getTransactions();
//                call.enqueue(new Callback<List<Transactions>>() {
//                    @Override
//                    public void onResponse(Call<List<Transactions>> call, Response<List<Transactions>> response) {
//                        if (response.isSuccessful() && response.body() != null) {
//                            transactionItems.postValue(response.body());
//                        } else {
//                            Log.e("API Error", "Failed to fetch transactions");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Transactions>> call, Throwable t) {
//                        Log.e("API Error", "Failed to fetch transactions", t);
//                    }
//                });
//
//                long endTime = System.currentTimeMillis();
//                long executionTime = endTime - startTime;
//                Log.d("Execution Time Transaction - get", "Thời gian thực thi của truy vấn thông tin giao dịch: " + executionTime + "ms");
//            }
//        });
//    }
//
//    public void createTransaction(Transactions transaction) {
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                Call<Transactions> call = transactionRoute.createTransaction(transaction);
//                call.enqueue(new Callback<Transactions>() {
//                    @Override
//                    public void onResponse(Call<Transactions> call, Response<Transactions> response) {
//                        if (response.isSuccessful() && response.body() != null) {
//                            loadTransactionItems();
//                            transactionAdded.postValue(true);
//                        } else {
//                            Log.e("API Error", "Failed to create transaction");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Transactions> call, Throwable t) {
//                        Log.e("API Error", "Failed to create transaction", t);
//                    }
//                });
//            }
//        });
//    }

    public void loadTransactionItems() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();

                Transactions transactions = new Transactions();
                List<Transactions> transactionList = transactions.getTransactionsFromDB();
                transactionItems.postValue(transactionList);

                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                Log.d("Execution Time Transaction - get", "Thời gian thực thi của truy vấn thông tin giao dịch: " + executionTime + "ms");
            }
        });
    }

    public void createTransaction(Transactions transaction) {
        transaction.addTransaction();

        loadTransactionItems();

        transactionAdded.postValue(true);
    }
    public void resetTransactionAdded() {
        transactionAdded.postValue(false);
    }
}
