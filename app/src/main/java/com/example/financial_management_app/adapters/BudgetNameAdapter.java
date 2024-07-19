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

public class BudgetNameAdapter extends BaseAdapter {
    private Context context;
    private List<Budgets> budget;

    public BudgetNameAdapter(Context context, List<Budgets> budget) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.spn_budget_name, parent, false);
        }

        Budgets budget = (Budgets) getItem(position);
        TextView nameTextView = convertView.findViewById(R.id.budget_item_name);

        if (budget != null) {
            nameTextView.setText(budget.getName());
        } else {
            nameTextView.setText(""); // Nếu budgetItem null, hiển thị một chuỗi rỗng
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}

