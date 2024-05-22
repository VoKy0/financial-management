package com.example.financial_management_app.models;

import com.example.financial_management_app.utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Transactions {
    private int id;
    private int account_id;
    private String transaction_date;
    private float amount;
    private String note;
    private ConnectDB connectDB;
    private Connection conn;

    public Transactions() {
        id = -1;
        account_id = -1;
        transaction_date = "";
        amount = 0;
        note = "";
        connectDB = new ConnectDB();
        conn = null;
    }
    public Transactions(int account_id, String transaction_date, float amount, String note) {
        super();
        this.account_id = account_id;
        this.transaction_date = transaction_date;
        this.amount = amount;
        this.note = note;
    }
    public Transactions(int id, int account_id, String transaction_date, float amount, String note) {
        super();
        this.id = id;
        this.account_id = account_id;
        this.transaction_date = transaction_date;
        this.amount = amount;
        this.note = note;
    }

    public String getTransactionDate() {
        return transaction_date;
    }
    public float getAmount() {
        return amount;
    }

    public List<Transactions> getTransactionsFromDB() {
        List<Transactions> transactionItems = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT id, account_id, transaction_date, amount, note FROM transactions";
            preparedStatement = conn.prepareStatement(query);
            res = preparedStatement.executeQuery();

            while (res.next()) {
                int id = res.getInt("id");
                int account_id = res.getInt("account_id");
                String transaction_date = res.getString("transaction_date");
                float amount = res.getFloat("amount");
                String note = res.getString("note");
                transactionItems.add(new Transactions(id, account_id, transaction_date, amount, note));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactionItems;
    }
}
