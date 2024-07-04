package com.example.financial_management_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_management_app.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button btn_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public void navigateToAuthenticationAccount(View v) {
        Intent intent = new Intent(this, AuthenticationAccountActivity.class);
        startActivity(intent);
    }
}