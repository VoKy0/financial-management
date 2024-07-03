package com.example.financial_management_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financial_management_app.models.Wallets;

public class WalletDetailViewModel extends ViewModel {
    private MutableLiveData<Wallets> wallet;
    private MutableLiveData<Boolean> walletUpdated;
    private Wallets model;

    public WalletDetailViewModel() {
        wallet = new MutableLiveData<>();
        walletUpdated = new MutableLiveData<>(false);
        model = new Wallets();
    }

    public LiveData<Wallets> getWallet() {
        return wallet;
    }
    public LiveData<Boolean> isWalletUpdated() {
        return walletUpdated;
    }

    public void loadWallet(int walletId) {
        new Thread(() -> {
            Wallets walletDetail = model.getWalletById(walletId);
            wallet.postValue(walletDetail);
        }).start();
    }

    public void updateWallet(int id, String name, String category, Double balance) {
        new Thread(() -> {
            try {
                Wallets wallet = model.getWalletById(id);
                if (wallet != null) {
                    // Kiểm tra các giá trị đầu vào trước khi cập nhật
                    if (name == null || name.isEmpty() || balance == null || balance < 0 || category == null || category.isEmpty()) {
                        throw new IllegalArgumentException("Invalid input values");
                    }

                    wallet.setName(name);
                    wallet.setCategory(category);
                    wallet.setBalance(balance);
                    model.updateWallet(wallet);
                    walletUpdated.postValue(true);
                }
            } catch (Exception e) {
                e.printStackTrace();

                walletUpdated.postValue(false);
            }

        }).start();
    }

    public void resetWalletUpdated() {
        walletUpdated.postValue(false);
    }
}
