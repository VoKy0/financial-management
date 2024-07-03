package com.example.financial_management_app.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.financial_management_app.R;
import com.example.financial_management_app.viewmodels.WalletDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WalletDetailFragment extends Fragment {

    private WalletDetailViewModel mViewModel;
    private TextView txt_wallet_name;
    private TextView txt_wallet_category;
    private TextView txt_wallet_balance;

    public static WalletDetailFragment newInstance() {
        return new WalletDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_wallet_name = view.findViewById(R.id.res_wallet_name);
        txt_wallet_category = view.findViewById(R.id.res_wallet_category);
        txt_wallet_balance = view.findViewById(R.id.res_wallet_balance);

        FloatingActionButton fab_edit_wallet = view.findViewById(R.id.fab_edit_wallet);

        mViewModel = new ViewModelProvider(this).get(WalletDetailViewModel.class);

        int wallet_id = getArguments().getInt("wallet_id");
        mViewModel.loadWallet(wallet_id);

        mViewModel.getWallet().observe(getViewLifecycleOwner(), wallet -> {
            if (wallet != null) {
                txt_wallet_name.setText(wallet.getName());
                txt_wallet_category.setText(wallet.getCategory());
                txt_wallet_balance.setText(String.valueOf(wallet.getBalance()));
            }
        });

        mViewModel.isWalletUpdated().observe(getViewLifecycleOwner(), updated -> {
            if (updated) {
                mViewModel.loadWallet(wallet_id); // Tải lại ngân sách sau khi cập nhật
                mViewModel.resetWalletUpdated(); // Reset lại trạng thái cập nhật
            }
        });

        fab_edit_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("wallet_id", wallet_id);
                bundle.putString("wallet_category", txt_wallet_category.getText().toString());
                Navigation.findNavController(v).navigate(R.id.action_WalletDetailFragment_to_EditWalletFragment, bundle);
            }
        });

        mViewModel.loadWallet(wallet_id);
    }

}