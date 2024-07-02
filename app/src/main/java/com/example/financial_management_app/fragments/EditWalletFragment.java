package com.example.financial_management_app.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.financial_management_app.R;
import com.example.financial_management_app.adapters.WalletAdapter;
import com.example.financial_management_app.models.Wallets;
import com.example.financial_management_app.viewmodels.WalletDetailViewModel;
import com.example.financial_management_app.viewmodels.WalletViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class EditWalletFragment extends Fragment {
    private WalletDetailViewModel mViewModel;
    private EditText edt_wallet_name;
    private Spinner spn_wallet_category;
    private EditText edt_wallet_balance;

    public static EditWalletFragment newInstance() {
        return new EditWalletFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_wallet_name = view.findViewById(R.id.edt_wallet_name);
        spn_wallet_category = view.findViewById(R.id.spn_wallet_category);
        edt_wallet_balance = view.findViewById(R.id.edt_wallet_balance);

        FloatingActionButton fab_save_wallet = view.findViewById(R.id.fab_save_wallet);

        mViewModel = new ViewModelProvider(this).get(WalletDetailViewModel.class);

        // Initialize the Spinner with category options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.wallet_categories, // Assuming you have this array in strings.xml
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_wallet_category.setAdapter(adapter);

        int wallet_id = getArguments().getInt("wallet_id");
        String wallet_category = getArguments().getString("wallet_category");
        mViewModel.loadWallet(wallet_id);

        mViewModel.getWallet().observe(getViewLifecycleOwner(), wallet -> {
            if (wallet != null) {
                edt_wallet_name.setText(wallet.getName());
                edt_wallet_balance.setText(String.valueOf(wallet.getBalance()));

                // Set the Spinner selection based on the category
                if (wallet.getCategory() != null) {
                    int spinnerPosition = adapter.getPosition(wallet_category);
                    spn_wallet_category.setSelection(spinnerPosition);
                }
            }
        });

        fab_save_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_wallet_name.getText().toString();
                String category = spn_wallet_category.getSelectedItem().toString();
                Double balance = Double.valueOf(edt_wallet_balance.getText().toString());

                mViewModel.updateWallet(wallet_id, name, category, balance);
                Navigation.findNavController(v).navigateUp();
            }
        });
    }
}
