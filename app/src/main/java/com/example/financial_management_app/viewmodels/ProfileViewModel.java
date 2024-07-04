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

    public void loadProfile(int account_id) {
        // Giả sử bạn có các phương thức trong repository để lấy dữ liệu
        Users userData = userModel.getUserByAccountID(account_id);
        Account accountData = accModel.getAccountByID(account_id);

        if (userData != null) {
            user.setValue(userData);
        }
        if (accountData != null) {
            account.setValue(accountData);
        }
    }

    public void updateProfile(Account account, Users user) {
        if (account != null && user != null) {
            user.updateUserByAccountID(user.getAccountID());
            account.updateAccount(account.getID());
        }
    }
    public LiveData<Boolean> changePassword(int account_id, String oldPassword, String newPassword) {
        MutableLiveData<Boolean> success = new MutableLiveData<>();
        Account account = accModel.getAccountByID(account_id);

        if (oldPassword.equals(account.getPassword())) {
            account.setPassword(newPassword);
            account.updatePasswordByID(account_id);
            success.setValue(true);
        } else {
            success.setValue(false);
        }

        return success;
    }
}
