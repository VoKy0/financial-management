package com.example.financial_management_app.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financial_management_app.models.Wallets;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WalletViewModel extends ViewModel {
    private MutableLiveData<List<Wallets>> walletItems;
    private ExecutorService executorService;

    public WalletViewModel() {
        walletItems = new MutableLiveData<>();
        executorService = Executors.newSingleThreadExecutor();
        loadWalletItems();
    }

    public LiveData<List<Wallets>> getWalletItems() {
        return walletItems;
    }

    private void loadWalletItems() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();

                Wallets wallets = new Wallets();
                List<Wallets> walletList = wallets.getWalletsFromDB();
                walletItems.postValue(walletList);

                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                Log.d("Execution Time Wallet - get", "Thời gian thực thi của truy vấn thông tin ví: " + executionTime + "ms");
            }
        });
    }

    private void CreateWallet() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}

