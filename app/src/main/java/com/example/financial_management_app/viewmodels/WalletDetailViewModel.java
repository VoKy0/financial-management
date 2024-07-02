package com.example.financial_management_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financial_management_app.models.Wallets;

public class WalletDetailViewModel extends ViewModel {
    private MutableLiveData<Wallets> wallet;
    private Wallets model;

    public WalletDetailViewModel() {
        wallet = new MutableLiveData<>();
        model = new Wallets();
    }

    public LiveData<Wallets> getWallet() {
        return wallet;
    }

    public void loadWallet(int walletId) {
        new Thread(() -> {
            Wallets walletDetail = model.getWalletById(walletId);
            wallet.postValue(walletDetail);
        }).start();
    }

    public void updateWallet(int id, String name, String category, Double balance) {
        new Thread(() -> {
            Wallets wallet = model.getWalletById(id);
            if (wallet != null) {
                wallet.setName(name);
                wallet.setCategory(category);
                wallet.setBalance(balance);
                model.updateWallet(wallet);
            }
        }).start();
    }

}
