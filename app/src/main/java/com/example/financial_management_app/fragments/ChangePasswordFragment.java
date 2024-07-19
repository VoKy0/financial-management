package com.example.financial_management_app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.financial_management_app.R;
import com.example.financial_management_app.viewmodels.ProfileViewModel;

public class ChangePasswordFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private EditText edt_old_password;
    private EditText edt_new_password;
    private EditText edt_re_new_password;
    private Button btn_change_password;

    private ImageView toggle_old_password;
    private ImageView toggle_new_password;
    private ImageView toggle_re_new_password;
    private boolean isOldPasswordVisible = false;
    private boolean isNewPasswordVisible = false;
    private boolean isReNewPasswordVisible = false;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_old_password = view.findViewById(R.id.edt_old_password);
        edt_new_password = view.findViewById(R.id.edt_new_password);
        edt_re_new_password = view.findViewById(R.id.edt_re_new_password);
        btn_change_password = (Button) view.findViewById(R.id.btn_change_password);

        toggle_old_password = view.findViewById(R.id.toggle_old_password);
        toggle_new_password = view.findViewById(R.id.toggle_new_password);
        toggle_re_new_password = view.findViewById(R.id.toggle_re_new_password);

        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        int account_id = getAccountIdFromSharedPreferences();

        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = edt_old_password.getText().toString();
                String newPassword = edt_new_password.getText().toString();
                String reNewPassword = edt_re_new_password.getText().toString();

                if (newPassword.isEmpty() || newPassword.length() < 7) {
                    Toast.makeText(getContext(), "Mật khẩu mới không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(reNewPassword)) {
                    Toast.makeText(getContext(), "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                mViewModel.changePassword(account_id, oldPassword, newPassword).observe(getViewLifecycleOwner(), success -> {
                    if (success) {
                        Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigateUp();
                    } else {
                        Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        toggle_old_password.setOnClickListener(v -> {
            isOldPasswordVisible = !isOldPasswordVisible;
            edt_old_password.setInputType(isOldPasswordVisible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edt_old_password.setSelection(edt_old_password.length());
            toggle_old_password.setImageResource(isOldPasswordVisible ? R.drawable.ic_eye_on : R.drawable.ic_eye_off);
        });

        toggle_new_password.setOnClickListener(v -> {
            isNewPasswordVisible = !isNewPasswordVisible;
            edt_new_password.setInputType(isNewPasswordVisible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edt_new_password.setSelection(edt_new_password.length());
            toggle_new_password.setImageResource(isNewPasswordVisible ? R.drawable.ic_eye_on : R.drawable.ic_eye_off);
        });

        toggle_re_new_password.setOnClickListener(v -> {
            isReNewPasswordVisible = !isReNewPasswordVisible;
            edt_re_new_password.setInputType(isReNewPasswordVisible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            edt_re_new_password.setSelection(edt_re_new_password.length());
            toggle_re_new_password.setImageResource(isReNewPasswordVisible ? R.drawable.ic_eye_on : R.drawable.ic_eye_off);
        });
    }

    private int getAccountIdFromSharedPreferences() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        return sharedPref.getInt("account_id", -1);
    }
}