package com.example.financial_management_app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.financial_management_app.viewmodels.BudgetLedgerViewModel;

public class AddBudgetFragment extends Fragment {

    private BudgetLedgerViewModel mViewModel;
    private EditText edt_budget_name;
    private EditText edt_budget_amount;
    private Button btn_create_budget;
    private TextView res;


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
        btn_create_budget = (Button) view.findViewById(R.id.btn_create_budget);

        mViewModel = new ViewModelProvider(this).get(BudgetLedgerViewModel.class);

        res = view.findViewById(R.id.tv_res);

        btn_create_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budget_name = edt_budget_name.getText().toString();
                Double budget_amount = Double.parseDouble(edt_budget_amount.getText().toString());

                // Lấy account_id từ SharedPreferences
                SharedPreferences sharedPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                int account_id = sharedPref.getInt("account_id", -1);

//                res.setText(account_id + " - " + wallet_name + " - " + wallet_category + " - " + wallet_balance);

//                mViewModel.createBudget(account_id, wallet_name, wallet_category, wallet_balance);
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

}