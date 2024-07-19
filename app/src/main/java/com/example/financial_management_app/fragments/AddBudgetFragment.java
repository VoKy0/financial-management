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
import android.widget.Button;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.financial_management_app.R;
import com.example.financial_management_app.adapters.WalletAdapter;
import com.example.financial_management_app.adapters.WalletNameAdapter;
import com.example.financial_management_app.models.Wallets;
import com.example.financial_management_app.viewmodels.BudgetLedgerViewModel;
import com.example.financial_management_app.viewmodels.WalletViewModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import java.util.Calendar;

public class AddBudgetFragment extends Fragment {

    private BudgetLedgerViewModel mViewModel;
    private WalletViewModel walletViewModel;
    private EditText edt_budget_name;
    private EditText edt_budget_amount;
    private EditText edt_budget_note;
    private TextView res_budget_date;
    private Spinner spn_wallets;
    private Button btn_create_budget;
    private int selected_wallet_id = -1;
    private Date budget_date;

    public static AddBudgetFragment newInstance() {
        return new AddBudgetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_budget, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edt_budget_name = view.findViewById(R.id.edt_budget_name);
        edt_budget_amount = view.findViewById(R.id.edt_budget_amount);
        edt_budget_note = view.findViewById(R.id.edt_budget_note);
        res_budget_date = view.findViewById(R.id.res_budget_date);
        spn_wallets = view.findViewById(R.id.spn_wallets);
        btn_create_budget = (Button) view.findViewById(R.id.btn_create_budget);

        mViewModel = new ViewModelProvider(this).get(BudgetLedgerViewModel.class);
        walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);

        res_budget_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        SharedPreferences sharedPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int account_id = sharedPref.getInt("account_id", -1);
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

        btn_create_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budget_name = edt_budget_name.getText().toString();
                Double budget_amount = Double.parseDouble(edt_budget_amount.getText().toString());
                String budget_note = edt_budget_note.getText().toString();

                // Lấy account_id từ SharedPreferences
                SharedPreferences sharedPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                int account_id = sharedPref.getInt("account_id", -1);

//                res.setText(account_id + " - " + selected_wallet_id + " - " + budget_name + " - " + budget_amount + " - " + budget_note + " - " + budget_date.toString());

                mViewModel.createBudget(account_id, selected_wallet_id, budget_name, budget_amount, budget_note, budget_date);
            }
        });

        // Quan sát LiveData để điều hướng sau khi thêm ví thành công
        mViewModel.getBudgetAdded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean budgetAdded) {
                if (budgetAdded) {
                    // Điều hướng trở lại trang hiển thị thông tin ví
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_AddBudgetFragment_to_BudgetLedgerFragment);

                    // Đặt lại trạng thái của budgetAdded để tránh điều hướng không cần thiết
                    mViewModel.resetBudgetAdded();
                }
            }
        });

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