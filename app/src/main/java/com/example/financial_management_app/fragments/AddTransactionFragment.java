package com.example.financial_management_app.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financial_management_app.R;
import com.example.financial_management_app.adapters.BudgetNameAdapter;
import com.example.financial_management_app.adapters.WalletNameAdapter;
import com.example.financial_management_app.models.Budgets;
import com.example.financial_management_app.models.Transactions;
import com.example.financial_management_app.models.Wallets;
import com.example.financial_management_app.viewmodels.BudgetLedgerViewModel;
import com.example.financial_management_app.viewmodels.TransactionViewModel;
import com.example.financial_management_app.viewmodels.WalletViewModel;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class AddTransactionFragment extends Fragment {

    private TransactionViewModel mViewModel;
    private WalletViewModel walletViewModel;
    private BudgetLedgerViewModel budgetViewModel;
    private EditText edt_transaction_amount;
    private Spinner spn_budgets;
    private EditText edt_transaction_note;
    private TextView tv_transaction_date;
    private Spinner spn_wallets;
    private Button btn_create_transaction;
    private Date transaction_date;
    private int selected_wallet_id = -1;
    private int selected_budget_id = -1;

    public static AddTransactionFragment newInstance() {
        return new AddTransactionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_transaction_amount = view.findViewById(R.id.edt_transaction_amount);
        spn_budgets = view.findViewById(R.id.spn_budgets);
        edt_transaction_note = view.findViewById(R.id.edt_transaction_note);
        tv_transaction_date = view.findViewById(R.id.tv_transaction_date);
        spn_wallets = view.findViewById(R.id.spn_wallets);
        btn_create_transaction = view.findViewById(R.id.btn_create_transaction);

        mViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        budgetViewModel = new ViewModelProvider(this).get(BudgetLedgerViewModel.class);

        tv_transaction_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        int account_id = getAccountIdFromSharedPreferences();

        walletViewModel.loadWalletItems(account_id);
        walletViewModel.getWalletItems().observe(getViewLifecycleOwner(), new Observer<List<Wallets>>() {
            @Override
            public void onChanged(List<Wallets> walletItems) {
                WalletNameAdapter walletNameAdapter = new WalletNameAdapter(getActivity(), walletItems);
                spn_wallets.setAdapter(walletNameAdapter);
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
                selected_wallet_id = -1;
            }
        });

        budgetViewModel.loadBudgetItems(account_id);
        budgetViewModel.getBudgetItems().observe(getViewLifecycleOwner(), new Observer<List<Budgets>>() {
            @Override
            public void onChanged(List<Budgets> budgetItems) {
                BudgetNameAdapter budgetNameAdapter = new BudgetNameAdapter(getActivity(), budgetItems);
                spn_budgets.setAdapter(budgetNameAdapter);
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
                selected_budget_id = -1;
            }
        });

        btn_create_transaction.setOnClickListener(v -> {
            Double transaction_amount = Double.parseDouble(edt_transaction_amount.getText().toString());
            String transaction_note = edt_transaction_note.getText().toString();

            if (transaction_amount == null || transaction_note.isEmpty() || transaction_date == null || selected_wallet_id == -1 || selected_budget_id == -1) {
                Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            Transactions transaction = new Transactions(account_id, selected_budget_id, selected_wallet_id, transaction_date, transaction_amount, transaction_note);

            mViewModel.createTransaction(transaction);
            Toast.makeText(getActivity(), "Giao dịch đã được thêm", Toast.LENGTH_SHORT).show();


        });

        mViewModel.getTransactionAdded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean walletAdded) {
                if (walletAdded) {
                    // Điều hướng trở lại trang hiển thị thông tin ví
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_AddTransactionFragment_to_TransactionFragment);

                    // Đặt lại trạng thái của walletAdded để tránh điều hướng không cần thiết
                    mViewModel.resetTransactionAdded();
                }
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
                transaction_date = new Date(calendar.getTimeInMillis());
                tv_transaction_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, year, month, day);

        datePickerDialog.show();
    }
}