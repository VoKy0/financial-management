package com.example.financial_management_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financial_management_app.models.Users;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<Users> user = new MutableLiveData<>();

    public void setUser(Users user) {
        this.user.setValue(user);
    }

    public LiveData<Users> getUser() {
        return user;
    }

    public void updateUserInfo(String first_name, String last_name, String dob, String address) {
        Users currentUser = user.getValue();
        if (currentUser != null) {
            currentUser.setFirstName(first_name);
            currentUser.setLastName(last_name);
            currentUser.setDob(dob);
            currentUser.setAddress(address);
            currentUser.updateUser(currentUser.getID());
            user.setValue(currentUser);
        }
    }
}
