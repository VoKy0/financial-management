package com.example.financial_management_app.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.financial_management_app.R;
import com.example.financial_management_app.viewmodels.WalletViewModel;

public class AddWalletFragment extends Fragment {

    private WalletViewModel mViewModel;
    private EditText edt_wallet_name;
    private Spinner spn_wallet_type;
    private Button btn_create_wallet;
    private TextView res;


    public static AddWalletFragment newInstance() {
        return new AddWalletFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edt_wallet_name = view.findViewById(R.id.edt_wallet_name);
        spn_wallet_type = view.findViewById(R.id.spn_wallet_type);
        btn_create_wallet = (Button) view.findViewById(R.id.btn_create_wallet);

        mViewModel = new ViewModelProvider(this).get(WalletViewModel.class);

        res = view.findViewById(R.id.tv_res);

        btn_create_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wallet_name = edt_wallet_name.getText().toString();
                String wallet_type = spn_wallet_type.getSelectedItem().toString();

                res.setText("Tên ví: " + wallet_name + "\nLoại ví: " + wallet_type);
                res.setVisibility(View.VISIBLE);


            }
        });
    }

}