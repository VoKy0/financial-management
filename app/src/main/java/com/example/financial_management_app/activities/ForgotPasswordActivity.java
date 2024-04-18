package com.example.financial_management_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_management_app.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    protected void navigateToAuthenticationAccount(View v) {
        Intent intent = new Intent(this, AuthenticationAccountActivity.class);
        startActivity(intent);
    }
}