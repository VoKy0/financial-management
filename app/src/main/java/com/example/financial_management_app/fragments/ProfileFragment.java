package com.example.financial_management_app.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.financial_management_app.R;
import com.example.financial_management_app.models.Account;
import com.example.financial_management_app.models.Users;
import com.example.financial_management_app.viewmodels.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private EditText firstNameEditText, lastNameEditText, dobEditText, addressEditText;
    private Button updateButton;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firstNameEditText = view.findViewById(R.id.first_name);
        lastNameEditText = view.findViewById(R.id.last_name);
        dobEditText = view.findViewById(R.id.dob);
        addressEditText = view.findViewById(R.id.address);
        updateButton = view.findViewById(R.id.update_button);

        updateButton.setOnClickListener(v -> updateUserInfo());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        Account account = new Account("user@example.com", "password"); // Thay thế bằng thông tin đăng nhập thật
        Users user = account.getUserInfo();
        if (user != null) {
            mViewModel.setUser(user);
        }

        mViewModel.getUser().observe(getViewLifecycleOwner(), this::populateUserInfo);
    }

    private void populateUserInfo(Users user) {
        if (user != null) {
            firstNameEditText.setText(user.getFirstName());
            lastNameEditText.setText(user.getLastName());
            dobEditText.setText(user.getDob());
            addressEditText.setText(user.getAddress());
        }
    }

    private void updateUserInfo() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String dob = dobEditText.getText().toString();
        String address = addressEditText.getText().toString();

        mViewModel.updateUserInfo(firstName, lastName, dob, address);
    }
}
