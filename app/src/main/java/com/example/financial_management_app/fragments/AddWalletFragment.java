package com.example.financial_management_app.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.financial_management_app.R;
import com.example.financial_management_app.adapters.WalletAdapter;
import com.example.financial_management_app.models.Wallets;
import com.example.financial_management_app.viewmodels.AddWalletViewModel;
import com.example.financial_management_app.viewmodels.WalletViewModel;

import java.util.List;

public class AddWalletFragment extends Fragment {

    private AddWalletViewModel mViewModel;

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
    }

}