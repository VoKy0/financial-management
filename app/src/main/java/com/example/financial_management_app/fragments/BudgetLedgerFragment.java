package com.example.financial_management_app.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.financial_management_app.adapters.BudgetLedgerAdapter;
import com.example.financial_management_app.models.Budgets;
import com.example.financial_management_app.models.Wallets;
import com.example.financial_management_app.viewmodels.BudgetLedgerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BudgetLedgerFragment extends Fragment {

    private BudgetLedgerViewModel mViewModel;
    private ListView listView;
    private BudgetLedgerAdapter budgetLedgerAdapter;

    public static BudgetLedgerFragment newInstance() {
        return new BudgetLedgerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget_ledger, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.budget_list_view);
        FloatingActionButton fab = view.findViewById(R.id.fab_budget);

        mViewModel = new ViewModelProvider(this).get(BudgetLedgerViewModel.class);

        // Lấy account_id từ SharedPreferences
        SharedPreferences sharedPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int account_id = sharedPref.getInt("account_id", -1);

        // Kiểm tra nếu account_id hợp lệ, tải các mục ví
        if (account_id != -1) {
            mViewModel.loadBudgetItems(account_id);
        }

        mViewModel.getBudgetItems().observe(getViewLifecycleOwner(), new Observer<List<Budgets>>() {
            @Override
            public void onChanged(List<Budgets> budgetItems) {
                budgetLedgerAdapter = new BudgetLedgerAdapter(getActivity(), budgetItems);
                listView.setAdapter(budgetLedgerAdapter);
            }
        });

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Budgets selectedBudget = (Budgets) budgetLedgerAdapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putInt("budget_id", selectedBudget.getID());
            Navigation.findNavController(view).navigate(R.id.action_BudgetLedgerFragment_to_BudgetDetailFragment, bundle);
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_add_budget);
            }
        });
    }

}