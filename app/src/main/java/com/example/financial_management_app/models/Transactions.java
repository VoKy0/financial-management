package com.example.financial_management_app.models;

import android.util.Log;

import com.example.financial_management_app.utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Transactions {
    private int id;
    private int account_id;
    private int budget_id;
    private int wallet_id;
    private Date transaction_date;
    private Double amount;
    private String note;
    private ConnectDB connectDB;
    private Connection conn;

    public Transactions() {
        id = -1;
        account_id = -1;
        transaction_date = null;
        amount = 0.0;
        note = "";
        connectDB = new ConnectDB();
        conn = null;
    }
    public Transactions(int account_id, Date transaction_date, Double amount, String note) {
        this();
        this.account_id = account_id;
        this.transaction_date = transaction_date;
        this.amount = amount;
        this.note = note;
    }
    public Transactions(int account_id, int budget_id, int wallet_id, Date transaction_date, Double amount, String note) {
        this(account_id, transaction_date, amount, note);
        this.budget_id = budget_id;
        this.wallet_id = wallet_id;
    }
    public Transactions(int id, int account_id, int budget_id, int wallet_id, Date transaction_date, Double amount, String note) {
        this(account_id, budget_id, wallet_id, transaction_date, amount, note);
        this.id = id;
    }

    public Date getTransactionDate() {
        return transaction_date;
    }
    public Double getAmount() {
        return amount;
    }

    public List<Transactions> getTransactionsFromDB() {
        List<Transactions> transactionItems = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT id, account_id, budget_id, wallet_id, transaction_date, amount, note FROM transactions";
            preparedStatement = conn.prepareStatement(query);
            res = preparedStatement.executeQuery();

            while (res.next()) {
                int id = res.getInt("id");
                int account_id = res.getInt("account_id");
                int budget_id = res.getInt("budget_id");
                int wallet_id = res.getInt("wallet_id");
                Date transaction_date = res.getDate("transaction_date");
                Double amount = res.getDouble("amount");
                String note = res.getString("note");
                transactionItems.add(new Transactions(id, account_id, budget_id, wallet_id, transaction_date, amount, note));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactionItems;
    }

    public void addTransaction() {
        PreparedStatement preparedStatement = null;

        try {
            conn = connectDB.getConnection();
            String query =  "INSERT INTO transactions(account_id, budget_id, wallet_id, transaction_date, amount, note)" +
                            "VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, account_id);
            preparedStatement.setInt(2, budget_id);
            preparedStatement.setInt(3, wallet_id);
            preparedStatement.setDate(4, transaction_date);
            preparedStatement.setDouble(5, amount);
            preparedStatement.setString(6, note);

            preparedStatement.executeUpdate();
            Log.i("Add transaction","Add transaction successful.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
