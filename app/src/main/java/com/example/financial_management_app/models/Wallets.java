package com.example.financial_management_app.models;

import android.util.Log;

import com.example.financial_management_app.utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Wallets {
    private int id;
    private int account_id;
    private String name;
    private String category;
    private Double balance;
    private ConnectDB connectDB;
    private Connection conn;

    public Wallets() {
        id = -1;
        account_id = -1;
        name = "";
        category = "";
        balance = 0.0;
        connectDB = new ConnectDB();
        conn = null;
    }

    public Wallets(String name, String category, Double balance) {
        this();
        this.name = name;
        this.category = category;
        this.balance = balance;
    }
    public Wallets(int account_id, String name, String category, Double balance) {
        this();
        this.account_id = account_id;
        this.name = name;
        this.category = category;
        this.balance = balance;
    }
    public Wallets(int id, int account_id, String name, String category, Double balance) {
        this();
        this.id = id;
        this.account_id = id;
        this.name = name;
        this.category = category;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public Double getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Wallets> getWalletsFromDB() {
        List<Wallets> walletItems = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT id, account_id, name, category, balance FROM wallets";
            preparedStatement = conn.prepareStatement(query);
            res = preparedStatement.executeQuery();

            while (res.next()) {
                int id = res.getInt("id");
                int account_id = res.getInt("account_id");
                String name = res.getString("name");
                String category = res.getString("category");
                Double balance = res.getDouble("balance");
                walletItems.add(new Wallets(id, account_id, name, category, balance));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return walletItems;
    }

    public void addWallet() {
        PreparedStatement preparedStatement = null;

        try {
            conn = connectDB.getConnection();
            String query = "INSERT INTO wallets(account_id, name, category, balance) VALUES (?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, account_id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, category);
            preparedStatement.setDouble(4, balance);

            preparedStatement.executeUpdate();
            Log.i("Add Wallet","Add wallet successful.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
