package com.example.financial_management_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.financial_management_app.R;
import com.example.financial_management_app.models.Budgets;

import java.util.List;

public class BudgetLedgerAdapter extends BaseAdapter {
    private Context context;
    private List<Budgets> budget;

    public BudgetLedgerAdapter(Context context, List<Budgets> budget) {
        this.context = context;
        this.budget = budget;
    }

    @Override
    public int getCount() {
        return budget.size();
    }

    @Override
    public Object getItem(int position) {
        return budget.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.budget_item, parent, false);
        }

        Budgets budget = (Budgets) getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.budget_item_name);
        TextView balanceTextView = convertView.findViewById(R.id.budget_item_amount);

        nameTextView.setText(budget.getName());
        balanceTextView.setText(String.valueOf(budget.getAmount()));

        return convertView;
    }
}

