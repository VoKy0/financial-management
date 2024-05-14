package com.example.financial_management_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.financial_management_app.R;
import com.example.financial_management_app.models.Wallets;

import java.util.List;

public class WalletAdapter extends BaseAdapter {
    private Context context;
    private List<Wallets> wallet;

    public WalletAdapter(Context context, List<Wallets> wallet) {
        this.context = context;
        this.wallet = wallet;
    }

    @Override
    public int getCount() {
        return wallet.size();
    }

    @Override
    public Object getItem(int position) {
        return wallet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.wallet_item, parent, false);
        }

        Wallets wallet = (Wallets) getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.wallet_item_name);
        TextView balanceTextView = convertView.findViewById(R.id.wallet_item_balance);

        nameTextView.setText(wallet.getName());
        balanceTextView.setText(String.valueOf(wallet.getBalance()));

        return convertView;
    }
}

