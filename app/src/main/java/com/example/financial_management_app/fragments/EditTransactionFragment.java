package com.example.financial_management_app.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.financial_management_app.R;
import com.example.financial_management_app.adapters.BudgetNameAdapter;
import com.example.financial_management_app.adapters.WalletNameAdapter;
import com.example.financial_management_app.models.Budgets;
import com.example.financial_management_app.models.Wallets;
import com.example.financial_management_app.viewmodels.BudgetLedgerViewModel;
import com.example.financial_management_app.viewmodels.TransactionDetailViewModel;
import com.example.financial_management_app.viewmodels.WalletViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class EditTransactionFragment extends Fragment {

    private TransactionDetailViewModel mViewModel;
    private WalletViewModel walletViewModel;
    private BudgetLedgerViewModel budgetViewModel;

    private Spinner spn_budgets;
    private EditText edt_transaction_amount;
    private TextView tv_transaction_date;
    private Spinner spn_wallets;
    private EditText edt_transaction_note;
    private FloatingActionButton fab_save_transaction;
    private Date transaction_date;

    private int selected_wallet_id = -1;
    private int selected_budget_id = -1;

    public static EditTransactionFragment newInstance() {
        return new EditTransactionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spn_budgets = view.findViewById(R.id.spn_budgets);
        edt_transaction_amount = view.findViewById(R.id.edt_transaction_amount);
        tv_transaction_date = view.findViewById(R.id.tv_transaction_date);
        spn_wallets = view.findViewById(R.id.spn_wallets);
        edt_transaction_note = view.findViewById(R.id.edt_transaction_note);
        fab_save_transaction = view.findViewById(R.id.fab_save_transaction);

        mViewModel = new ViewModelProvider(this).get(TransactionDetailViewModel.class);
        walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        budgetViewModel = new ViewModelProvider(this).get(BudgetLedgerViewModel.class);

        int account_id = getAccountIdFromSharedPreferences();
        int transaction_id = getArguments().getInt("transaction_id", -1);
        mViewModel.loadTransaction(transaction_id);
        budgetViewModel.loadBudgetItems(account_id);
        walletViewModel.loadWalletItems(account_id);

        mViewModel.getTransaction().observe(getViewLifecycleOwner(), transaction -> {
            if (transaction != null) {
                selected_wallet_id = transaction.getWalletID();
                selected_budget_id = transaction.getBudgetID();
                transaction_date = transaction.getTransactionDate();

                edt_transaction_amount.setText(String.valueOf(transaction.getAmount()));
                tv_transaction_date.setText(String.valueOf(transaction.getTransactionDate()));
                edt_transaction_note.setText(transaction.getNote());
            }
        });

        tv_transaction_date.setOnClickListener(v -> showDatePickerDialog());

        budgetViewModel.getBudgetItems().observe(getViewLifecycleOwner(), budgetItems -> {
            BudgetNameAdapter budgetNameAdapter = new BudgetNameAdapter(getActivity(), budgetItems);
            spn_budgets.setAdapter(budgetNameAdapter);

            for (int i = 0; i < budgetItems.size(); i++) {
                if (budgetItems.get(i).getID() == selected_budget_id) {
                    spn_budgets.setSelection(i);
                    break;
                }
            }
        });

        walletViewModel.getWalletItems().observe(getViewLifecycleOwner(), walletItems -> {
            WalletNameAdapter walletNameAdapter = new WalletNameAdapter(getActivity(), walletItems);
            spn_wallets.setAdapter(walletNameAdapter);

            for (int i = 0; i < walletItems.size(); i++) {
                if (walletItems.get(i).getID() == selected_wallet_id) {
                    spn_wallets.setSelection(i);
                    break;
                }
            }
        });

        spn_budgets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Budgets selectedBudget = (Budgets) parent.getItemAtPosition(position);
                selected_budget_id = selectedBudget.getID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spn_wallets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Wallets selectedWallet = (Wallets) parent.getItemAtPosition(position);
                selected_wallet_id = selectedWallet.getID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fab_save_transaction.setOnClickListener(v -> {
            Double amount = Double.parseDouble(edt_transaction_amount.getText().toString());
            String note = edt_transaction_note.getText().toString();

            mViewModel.updateTransaction(transaction_id, selected_budget_id, selected_wallet_id, transaction_date, amount, note);
            Navigation.findNavController(v).navigateUp();
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
                transaction_date = new Date(calendar.getTimeInMillis());
                tv_transaction_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, year, month, day);

        datePickerDialog.show();
    }
}