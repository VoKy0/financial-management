package com.example.financial_management_app.viewmodels;

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
                Wallets wallets = new Wallets();
                List<Wallets> walletList = wallets.getWalletsFromDB();
                walletItems.postValue(walletList);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}

