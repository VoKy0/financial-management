package com.example.financial_management_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.financial_management_app.R;
import com.example.financial_management_app.viewmodels.BudgetDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BudgetDetailFragment extends Fragment {

    private BudgetDetailViewModel mViewModel;
    private TextView txt_budget_name;
    private TextView txt_budget_amount;
    private TextView txt_budget_note;
    private TextView txt_budget_date;
    private TextView txt_wallet_name;

    public static BudgetDetailFragment newInstance() {
        return new BudgetDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_budget_name = view.findViewById(R.id.res_budget_name);
        txt_budget_amount = view.findViewById(R.id.res_budget_amount);
        txt_budget_note = view.findViewById(R.id.res_budget_note);
        txt_budget_date = view.findViewById(R.id.res_budget_date);
        txt_wallet_name = view.findViewById(R.id.res_wallet_name);

        FloatingActionButton fab_edit_budget = view.findViewById(R.id.fab_edit_budget);

        mViewModel = new ViewModelProvider(this).get(BudgetDetailViewModel.class);

        int budget_id = getArguments().getInt("budget_id");
        mViewModel.loadBudget(budget_id);

        mViewModel.getBudget().observe(getViewLifecycleOwner(), budget -> {
            if (budget != null) {
                txt_budget_name.setText(budget.getName());
                txt_budget_amount.setText(String.valueOf(budget.getAmount()));
                txt_budget_note.setText(budget.getNote());
                txt_budget_date.setText(String.valueOf(budget.getBudgetDate()));
                txt_wallet_name.setText(budget.getWalletName(budget.getWalletID()));
            }
        });

        mViewModel.isBudgetUpdated().observe(getViewLifecycleOwner(), updated -> {
            if (updated) {
                mViewModel.loadBudget(budget_id); // Tải lại ngân sách sau khi cập nhật
                mViewModel.resetBudgetUpdated(); // Reset lại trạng thái cập nhật
            }
        });

        fab_edit_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("budget_id", budget_id);
                bundle.putString("wallet_name", txt_wallet_name.getText().toString());
                Navigation.findNavController(v).navigate(R.id.action_BudgetDetailFragment_to_EditBudgetFragment, bundle);
            }
        });

        mViewModel.loadBudget(budget_id);
    }

}