package com.example.financial_management_app.models;

import android.text.TextUtils;
import android.util.Patterns;

public class Account {
    private String email;
    private String password;
    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isValidEmail() {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean isValidPassword() {
        return !TextUtils.isEmpty(password) && password.length() > 6;
    }
}
