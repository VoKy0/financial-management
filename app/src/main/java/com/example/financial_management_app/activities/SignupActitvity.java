package com.example.financial_management_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_management_app.MainActivity;
import com.example.financial_management_app.R;
import com.example.financial_management_app.models.Account;
import com.example.financial_management_app.models.Users;

import java.sql.Date;

public class SignupActitvity extends AppCompatActivity {
    private EditText edt_first_name;
    private EditText edt_last_name;
    private EditText edt_username;
    private EditText edt_dob;
    private EditText edt_email;
    private EditText edt_password;
    private EditText edt_re_password;
    private EditText edt_address;
    private Button btn_signup;
    private Button btn_login_fb;
    private Button btn_login_google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_username = findViewById(R.id.edt_username);
        edt_dob = findViewById(R.id.edt_dob);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_re_password = findViewById(R.id.edt_re_password);
        edt_address = findViewById(R.id.edt_address);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_login_fb = (Button) findViewById(R.id.btn_login_fb);
        btn_login_google = (Button) findViewById(R.id.btn_login_google);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                String dob_string = edt_dob.getText().toString();
//                Date dob = null;
//                try {
//                    dob = format.parse(dob_string);
//                }
//                catch (ParseException e) {
//                    e.printStackTrace();
//                }

                Date dob = Date.valueOf(edt_dob.getText().toString());

                String first_name = edt_first_name.getText().toString();
                String last_name = edt_last_name.getText().toString();
                String username = edt_username.getText().toString();
                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();
                String re_password = edt_re_password.getText().toString();
                String address = edt_address.getText().toString();

                Account acc = new Account(username, email, password);
                Users user = new Users(first_name, last_name, dob, address);

                if (user.isValidFirstName() &&
                    user.isValidLastName() &&
                    user.isValidDob() &&
                    user.isValidAddress() &&
                    acc.isValidUsername() &&
                    acc.isValidEmail() &&
                    acc.isValidPassword() &&
                    acc.isValidRePassword(re_password)) {

                    int account_id = acc.getMaxID() + 1;
                    user.setAccountID(account_id);

                    acc.addAccount();
                    user.addUser();

                    // Signup successful -> navigate to home
                    Intent intent = new Intent(SignupActitvity.this, LoginActivity.class);
                    startActivity(intent);
                }

//                if (!user.isValidFirstName()) {
//                    // Lỗi
//                }
//                if (!user.isValidLastName()) {
//                    // Lỗi
//                }
//                if (!user.isValidDob()) {
//                    // Lỗi
//                }
//                if (!user.isValidAddress()) {
//                    // Lỗi
//                }
//                if (!acc.isValidUsername()) {
//                    // Lỗi
//                }
//                if (!acc.isValidEmail()) {
//                    // Lỗi
//                }
//                if (!acc.isValidPassword()) {
//                    // Lỗi
//                }
//                if (!acc.isValidRePassword(re_password)) {
//                    // Lỗi
//                }
            }
        });
    }

    public void navigateToLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}