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
    private MutableLiveData<Boolean> walletAdded;


    public WalletViewModel() {
        walletItems = new MutableLiveData<>();
        executorService = Executors.newSingleThreadExecutor();
        walletAdded = new MutableLiveData<>(false);
    }

    public LiveData<List<Wallets>> getWalletItems() {
        return walletItems;
    }

    public LiveData<Boolean> getWalletAdded() {
        return walletAdded;
    }

    public void loadWalletItems() {
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

    public void loadWalletItems(int account_id) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();

                Wallets wallets = new Wallets();
                List<Wallets> walletList = wallets.getWalletsByAccountID(account_id);
                walletItems.postValue(walletList);

                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                Log.d("Execution Time Wallet - get", "Thời gian thực thi của truy vấn thông tin ví: " + executionTime + "ms");
            }
        });
    }

    public void createWallet(int account_id, String name, String category, Double balance) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Wallets wallets = new Wallets(account_id, name, category, balance);
                wallets.addWallet();

                // Cập nhật danh sách ví sau khi thêm ví mới
                loadWalletItems();

                // Đánh dấu rằng ví đã được thêm
                walletAdded.postValue(true);
            }
        });
    }
    public void resetWalletAdded() {
        walletAdded.setValue(false);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}

