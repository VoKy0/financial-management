package com.example.financial_management_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.financial_management_app.R;
import com.example.financial_management_app.adapters.TransactionAdapter;
import com.example.financial_management_app.adapters.WalletAdapter;
import com.example.financial_management_app.databinding.FragmentTransactionBinding;
import com.example.financial_management_app.models.Transactions;
import com.example.financial_management_app.models.Wallets;
import com.example.financial_management_app.viewmodels.TransactionViewModel;
import com.example.financial_management_app.viewmodels.WalletViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TransactionFragment extends Fragment {
    private TransactionViewModel mViewModel;
    private ListView listView;
    private TransactionAdapter transactionAdapter;

    public static TransactionFragment newInstance() {
        return new TransactionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.fab_transaction);


        listView = view.findViewById(R.id.transaction_list_view);
        mViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        mViewModel.loadTransactionItems();

        mViewModel.getTransactionItems().observe(getViewLifecycleOwner(), new Observer<List<Transactions>>() {
            @Override
            public void onChanged(List<Transactions> transactionItems) {
                transactionAdapter = new TransactionAdapter(getActivity(), transactionItems);
                listView.setAdapter(transactionAdapter);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_add_transaction);
            }
        });
    }
}