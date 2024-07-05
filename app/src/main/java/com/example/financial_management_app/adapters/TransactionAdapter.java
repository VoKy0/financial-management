package com.example.financial_management_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.financial_management_app.R;
import com.example.financial_management_app.models.Transactions;

import java.util.List;

public class TransactionAdapter extends BaseAdapter {
    private Context context;
    private List<Transactions> transaction;

    public TransactionAdapter(Context context, List<Transactions> transaction) {
        this.context = context;
        this.transaction = transaction;
    }

    @Override
    public int getCount() {
        return transaction.size();
    }

    @Override
    public Object getItem(int position) {
        return transaction.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false);
        }

        Transactions transaction = (Transactions) getItem(position);

        TextView transactionDateTextView = convertView.findViewById(R.id.transaction_item_transaction_date);
        TextView amountTextView = convertView.findViewById(R.id.transaction_item_amount);

        transactionDateTextView.setText(String.valueOf(transaction.getTransactionDate()));
        amountTextView.setText(String.valueOf(transaction.getAmount()));

        return convertView;
    }
}

