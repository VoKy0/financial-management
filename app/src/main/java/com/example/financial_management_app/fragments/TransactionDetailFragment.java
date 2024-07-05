package com.example.financial_management_app.fragments;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.financial_management_app.R;
import com.example.financial_management_app.viewmodels.BudgetDetailViewModel;
import com.example.financial_management_app.viewmodels.TransactionDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class TransactionDetailFragment extends Fragment {

    private TransactionDetailViewModel mViewModel;
    private TextView tv_budget_name;
    private TextView tv_transaction_amount;
    private TextView tv_transaction_date;
    private TextView tv_wallet_name;
    private TextView tv_transaction_note;
    private Button btn_delete_transaction;
    private FloatingActionButton fab_edit_transaction;

    public static TransactionDetailFragment newInstance() {
        return new TransactionDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_budget_name = view.findViewById(R.id.tv_budget_name);
        tv_transaction_amount = view.findViewById(R.id.tv_transaction_amount);
        tv_transaction_date = view.findViewById(R.id.tv_transaction_date);
        tv_wallet_name = view.findViewById(R.id.tv_wallet_name);
        tv_transaction_note = view.findViewById(R.id.tv_transaction_note);
        btn_delete_transaction = (Button) view.findViewById(R.id.btn_delete_transaction);
        fab_edit_transaction = view.findViewById(R.id.fab_edit_transaction);

        mViewModel = new ViewModelProvider(this).get(TransactionDetailViewModel.class);

        int transaction_id = getArguments().getInt("transaction_id");
        mViewModel.loadTransaction(transaction_id);

        mViewModel.getTransaction().observe(getViewLifecycleOwner(), transaction -> {
            if (transaction != null) {
                tv_budget_name.setText(transaction.getBudgetName(transaction.getBudgetID()));

                tv_transaction_amount.setText(String.valueOf(transaction.getAmount()));
                tv_transaction_date.setText(String.valueOf(transaction.getTransactionDate()));
                tv_transaction_note.setText(transaction.getNote());

                tv_wallet_name.setText(transaction.getWalletName(transaction.getWalletID()));
            }
        });

        mViewModel.isTransactionUpdated().observe(getViewLifecycleOwner(), updated -> {
            if (updated) {
                mViewModel.loadTransaction(transaction_id); // Tải lại ngân sách sau khi cập nhật
                mViewModel.resetTransactionUpdated(); // Reset lại trạng thái cập nhật
            }
        });

        fab_edit_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("transaction_id", transaction_id);
                Navigation.findNavController(v).navigate(R.id.action_TransactionDetailFragment_to_EditTransactionFragment, bundle);
            }
        });

        mViewModel.loadTransaction(transaction_id);
    }

}