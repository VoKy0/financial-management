package com.example.financial_management_app.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.financial_management_app.R;
import com.example.financial_management_app.activities.ResetPasswordActivity;
import com.example.financial_management_app.viewmodels.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private TextView tv_username;
    private TextView tv_email;
    private TextView tv_phone;
    private TextView tv_fullname;
    private TextView tv_dob;
    private TextView tv_address;
    private Button btn_edit_profile;
    private TextView btn_change_password;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_username = view.findViewById(R.id.value_username);
        tv_email = view.findViewById(R.id.value_email);
        tv_phone = view.findViewById(R.id.value_phone);
        tv_fullname = view.findViewById(R.id.value_fullname);
        tv_dob = view.findViewById(R.id.value_dob);
        tv_address = view.findViewById(R.id.value_address);
        btn_edit_profile = (Button) view.findViewById(R.id.btn_edit_profile);
        btn_change_password = view.findViewById(R.id.btn_change_password);

        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        int account_id = getAccountIdFromSharedPreferences();

        mViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                tv_fullname.setText(user.getFullName());
                tv_dob.setText(String.valueOf(user.getDob()));
                tv_address.setText(user.getAddress());
            }
        });

        mViewModel.getAccount().observe(getViewLifecycleOwner(), account -> {
            if (account != null) {
                tv_username.setText(account.getUsername());
                tv_email.setText(account.getEmail());
                tv_phone.setText(account.getPhone());
            }
        });

        // Xử lý sự kiện nút "Chỉnh sửa"
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_ProfileFragment_to_EditProfileFragment);
            }
        });

        // Xử lý sự kiện nút "Thay đổi mật khẩu"
        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_ProfileFragment_to_ChangePasswordFragment);
            }
        });

        mViewModel.loadProfile(account_id);
    }

    private int getAccountIdFromSharedPreferences() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        return sharedPref.getInt("account_id", -1);
    }
}
