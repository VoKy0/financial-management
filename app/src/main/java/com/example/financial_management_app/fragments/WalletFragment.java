package com.example.financial_management_app.fragments;

import androidx.lifecycle.Observer;
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
import android.widget.ListView;

import com.example.financial_management_app.R;
import com.example.financial_management_app.adapters.WalletAdapter;
import com.example.financial_management_app.models.Wallets;
import com.example.financial_management_app.viewmodels.WalletViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class WalletFragment extends Fragment {

    private WalletViewModel mViewModel;
    private ListView listView;
    private WalletAdapter walletAdapter;

    public static WalletFragment newInstance() {
        return new WalletFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.wallet_list_view);
        FloatingActionButton fab = view.findViewById(R.id.fab_wallet);

        mViewModel = new ViewModelProvider(this).get(WalletViewModel.class);

        mViewModel.getWalletItems().observe(getViewLifecycleOwner(), new Observer<List<Wallets>>() {
            @Override
            public void onChanged(List<Wallets> walletItems) {
                walletAdapter = new WalletAdapter(getActivity(), walletItems);
                listView.setAdapter(walletAdapter);
            }
        });

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Wallets selectedWallet = (Wallets) walletAdapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putInt("wallet_id", selectedWallet.getID());
            Navigation.findNavController(view).navigate(R.id.action_WalletFragment_to_WalletDetailFragment, bundle);
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_add_wallet);
            }
        });
    }
}
