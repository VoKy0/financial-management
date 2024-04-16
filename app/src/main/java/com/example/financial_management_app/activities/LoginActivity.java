package com.example.financial_management_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.financial_management_app.MainActivity;
import com.example.financial_management_app.R;
import com.example.financial_management_app.models.Account;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.ResultSet;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private TextView errorEmail;
    private EditText edtPassword;
    private TextView errorPassword;
    private TextView forgotPassword;
    private TextView loginNotification;
    private Button btnLogin;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edt_email);
        errorEmail = findViewById(R.id.error_email);
        edtPassword = findViewById(R.id.edt_password);
        errorPassword = findViewById(R.id.error_password);
        forgotPassword = findViewById(R.id.forgot_password);
        loginNotification = findViewById(R.id.login_notification);
        btnLogin = (Button) findViewById(R.id.btn_login);
        signup = findViewById(R.id.signup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                Account acc = new Account(email, password);

                if (!acc.isValidEmail()) {
                    errorEmail.setText("Email không đúng định dạng.");
                }
                else {
                    if (!acc.isValidPassword()) {
                        errorPassword.setText("Mật khẩu có độ dài tối thiếu là 7");
                    }
                    else {
                        switch (acc.checkAccount()) {
                            case -1:
                                loginNotification.setText("Tài khoản không tồn tại.");
                                Log.i("Login", "Tài khoản không tồn tại.");
                                break;
                            case 0:
                                loginNotification.setText("Tài khoản hoặc mật khẩu không đúng.");
                                Log.i("Login", "Tài khoản hoặc mật khẩu không đúng.");
                                break;
                            case 1:
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                Log.i("Login", "Đăng nhập thành công.");
                                break;
                        }
                    }
                }
            }
        });
    }
}