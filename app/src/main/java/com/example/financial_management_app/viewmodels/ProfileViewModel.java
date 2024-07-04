package com.example.financial_management_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financial_management_app.models.Account;
import com.example.financial_management_app.models.Users;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<Users> user;
    private MutableLiveData<Account> account;
    private Users userModel;
    private Account accModel;

    public ProfileViewModel() {
        user = new MutableLiveData<>();
        account = new MutableLiveData<>();
        userModel = new Users();
        accModel = new Account();
    }

    public LiveData<Users> getUser() {
        return user;
    }

    public LiveData<Account> getAccount() {
        return account;
    }

    public void loadProfileData(int accountId) {
        // Giả sử bạn có các phương thức trong repository để lấy dữ liệu
        Users userData = userModel.getUserByAccountID(accountId);
        Account accountData = accModel.getAccountByID(accountId);

        user.setValue(userData);
        account.setValue(accountData);
    }
}
