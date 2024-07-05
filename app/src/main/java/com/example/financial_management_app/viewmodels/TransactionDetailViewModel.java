package com.example.financial_management_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financial_management_app.models.Transactions;

import java.sql.Date;

public class TransactionDetailViewModel extends ViewModel {
    private MutableLiveData<Transactions> transaction;
    private MutableLiveData<Boolean> transactionUpdated;
    private Transactions model;

    public TransactionDetailViewModel() {
        transaction = new MutableLiveData<>();
        transactionUpdated = new MutableLiveData<>(false);
        model = new Transactions();
    }

    public LiveData<Transactions> getTransaction() {
        return transaction;
    }
    public LiveData<Boolean> isTransactionUpdated() {
        return transactionUpdated;
    }

    public void loadTransaction(int transaction_id) {
        new Thread(() -> {
            Transactions transactionDetail = model.getTransactionByID(transaction_id);
            transaction.postValue(transactionDetail);
        }).start();
    }

    public void updateTransaction(int id, int budget_id, int wallet_id, Date transaction_date, Double amount, String note) {
        new Thread(() -> {
            try {
                Transactions transaction = model.getTransactionByID(id);
                if (transaction != null) {
                    // Kiểm tra các giá trị đầu vào trước khi cập nhật
                    if (budget_id < 0 || wallet_id < 0 || transaction_date == null || amount == null) {
                        throw new IllegalArgumentException("Invalid input values");
                    }

                    transaction.setBudgetID(budget_id);
                    transaction.setWalletID(wallet_id);
                    transaction.setTransactionDate(transaction_date);
                    transaction.setAmount(amount);
                    transaction.setNote(note);

                    model.updateTransaction(transaction);
                    transactionUpdated.postValue(true);
                }
            } catch (Exception e) {
                e.printStackTrace();

                transactionUpdated.postValue(false);
            }

        }).start();
    }

    public void resetTransactionUpdated() {
        transactionUpdated.postValue(false);
    }
}
