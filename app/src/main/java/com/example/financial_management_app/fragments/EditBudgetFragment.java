package com.example.financial_management_app.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.financial_management_app.R;
import com.example.financial_management_app.adapters.WalletNameAdapter;
import com.example.financial_management_app.models.Wallets;
import com.example.financial_management_app.viewmodels.BudgetDetailViewModel;
import com.example.financial_management_app.viewmodels.WalletViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class EditBudgetFragment extends Fragment {
    private BudgetDetailViewModel mViewModel;
    private WalletViewModel walletViewModel;

    private EditText edt_budget_name;
    private EditText edt_budget_amount;
    private EditText edt_budget_note;
    private TextView res_budget_date;
    private Spinner spn_wallet_name;
    private LinearLayout date_layout;
    private Date budget_date;
    private List<Wallets> wallets;
    private int selected_wallet_id;


    public static EditBudgetFragment newInstance() {
        return new EditBudgetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_budget, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_budget_name = view.findViewById(R.id.edt_budget_name);
        edt_budget_amount = view.findViewById(R.id.edt_budget_amount);
        edt_budget_note = view.findViewById(R.id.edt_budget_note);
        res_budget_date = view.findViewById(R.id.res_budget_date);
        spn_wallet_name = view.findViewById(R.id.spn_wallet_name);
        date_layout = view.findViewById(R.id.date_layout);

        FloatingActionButton fab_save_budget = view.findViewById(R.id.fab_save_budget);

        mViewModel = new ViewModelProvider(this).get(BudgetDetailViewModel.class);
        walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);

        int budget_id = getArguments().getInt("budget_id");
        String wallet_name =  getArguments().getString("wallet_name");
        mViewModel.loadBudget(budget_id);

        walletViewModel.loadWalletItems(getAccountIdFromSharedPreferences());

        // Thiết lập ví đã chọn ban đầu
        mViewModel.getBudget().observe(getViewLifecycleOwner(), budget -> {
            if (budget != null) {
                selected_wallet_id = budget.getWalletID();
                budget_date = budget.getBudgetDate();

                edt_budget_name.setText(budget.getName());
                edt_budget_amount.setText(String.valueOf(budget.getAmount()));
                edt_budget_note.setText(budget.getNote());
                res_budget_date.setText(String.valueOf(budget.getBudgetDate()));
            }
        });

        walletViewModel.getWalletItems().observe(getViewLifecycleOwner(), new Observer<List<Wallets>>() {
            @Override
            public void onChanged(List<Wallets> walletItems) {
                WalletNameAdapter walletNameAdapter = new WalletNameAdapter(getActivity(), walletItems);
                spn_wallet_name.setAdapter(walletNameAdapter);

                // Thiết lập vị trí đã chọn ban đầu cho spinner
                for (int i = 0; i < walletItems.size(); i++) {
                    if (walletItems.get(i).getID() == selected_wallet_id) {
                        spn_wallet_name.setSelection(i);
                        break;
                    }
                }
            }
        });

        spn_wallet_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Wallets selectedWallet = (Wallets) parent.getItemAtPosition(position);
                selected_wallet_id = selectedWallet.getID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_wallet_id = -1;
            }
        });

        date_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        fab_save_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_budget_name.getText().toString();
                Double amount = Double.parseDouble(edt_budget_amount.getText().toString());
                String note = edt_budget_note.getText().toString();

                mViewModel.updateBudget(budget_id, selected_wallet_id, name, amount, note, budget_date);
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
                budget_date = new Date(calendar.getTimeInMillis());
                res_budget_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, year, month, day);

        datePickerDialog.show();
    }
}
