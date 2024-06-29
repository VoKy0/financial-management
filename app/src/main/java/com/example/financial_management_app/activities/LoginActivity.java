package com.example.financial_management_app.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
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


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;


public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private TextView errorEmail;
    private EditText edtPassword;
    private TextView errorPassword;
    private TextView forgotPassword;
    private TextView loginNotification;
    private Button btnLogin;
    private TextView signup;


    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long startTime = System.currentTimeMillis();


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


        // Configure sign-in to request the user's ID, email address, and basic profile.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        Button signInButton = findViewById(R.id.login_google);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                                int account_id = acc.getIDFromDBByEmail(acc.getEmail());

                                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putInt("account_id", account_id);
                                editor.putString("name", acc.getUsername());
                                editor.putString("email", acc.getEmail());
                                editor.apply();

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

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        Log.d("Execution Time Login", "Thời gian thực thi của chức năng đăng nhập: " + executionTime + "ms");
    }

    public void navigateToSignup(View v) {
        Intent intent = new Intent(this, SignupActitvity.class);
        startActivity(intent);
    }
    public void navigateToForgotPassword(View v) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            Log.w("MainActivity", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            String welcome = "Welcome, " + account.getDisplayName();
            Toast.makeText(this, welcome, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_LONG).show();
        }
    }


}