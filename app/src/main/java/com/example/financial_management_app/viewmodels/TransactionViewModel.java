package com.example.financial_management_app.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financial_management_app.models.Transactions;
import com.example.financial_management_app.models.Wallets;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionViewModel extends ViewModel {
    private MutableLiveData<List<Transactions>> transactionItems;
    private ExecutorService executorService;

    public TransactionViewModel() {

        transactionItems = new MutableLiveData<>();
        executorService = Executors.newSingleThreadExecutor();
        loadTransactionItems();
    }
    public LiveData<List<Transactions>> getTransactionItems() {
        return transactionItems;
    }

    private void loadTransactionItems() {
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
}
