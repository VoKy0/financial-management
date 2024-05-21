package com.example.financial_management_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TransactionLedgerViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public TransactionLedgerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Transaction Ledger fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}