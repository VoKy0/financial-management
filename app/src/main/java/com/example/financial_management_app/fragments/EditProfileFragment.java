package com.example.financial_management_app.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.financial_management_app.R;
import com.example.financial_management_app.models.Account;
import com.example.financial_management_app.models.Users;
import com.example.financial_management_app.viewmodels.ProfileViewModel;

import java.sql.Date;
import java.util.Calendar;

public class EditProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private EditText edt_username;
    private EditText edt_email;
    private EditText edt_phone;
    private EditText edt_firstname;
    private EditText edt_lastname;
    private EditText edt_dob;
    private EditText edt_address;
    private Button btn_save_profile;
    Date dob;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_username = view.findViewById(R.id.edt_username);
        edt_email = view.findViewById(R.id.edt_email);
        edt_phone = view.findViewById(R.id.edt_phone);
        edt_firstname = view.findViewById(R.id.edt_firstname);
        edt_lastname = view.findViewById(R.id.edt_lastname);
        edt_dob = view.findViewById(R.id.edt_dob);
        edt_address = view.findViewById(R.id.edt_address);
        btn_save_profile = (Button) view.findViewById(R.id.btn_save_profile);

        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        int account_id = getAccountIdFromSharedPreferences();

        mViewModel.loadProfile(account_id);

        mViewModel.getAccount().observe(getViewLifecycleOwner(), account -> {
            if (account != null) {
                edt_username.setText(account.getUsername());
                edt_email.setText(account.getEmail());
                edt_phone.setText(account.getPhone());
            }
        });

        mViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                dob = user.getDob();

                edt_firstname.setText(user.getFirstName());
                edt_lastname.setText(user.getLastName());
                edt_dob.setText(String.valueOf(dob));
                edt_address.setText(user.getAddress());
            }
        });

        edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btn_save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username.getText().toString();
                String email = edt_email.getText().toString();
                String phone = edt_phone.getText().toString();
                String firstname = edt_firstname.getText().toString();
                String lastname = edt_lastname.getText().toString();
                String address = edt_address.getText().toString();

                Account account = new Account(account_id, username, email, phone);
                Users user = new Users(account_id, firstname, lastname, dob, address);

                mViewModel.updateProfile(account, user);
                Navigation.findNavController(v).navigateUp();
            }
        });
    }

    private int getAccountIdFromSharedPreferences() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        return sharedPref.getInt("account_id", -1);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                dob = new Date(calendar.getTimeInMillis());
                edt_dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, year, month, day);

        datePickerDialog.show();
    }
}
