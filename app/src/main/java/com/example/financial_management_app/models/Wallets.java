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
    private Double balance;
    private ConnectDB connectDB;
    private Connection conn;

    public Wallets() {
        id = -1;
        account_id = -1;
        name = "";
        balance = 0.0;
        connectDB = new ConnectDB();
        conn = null;
    }

    public Wallets(String name, Double balance) {
        super();
        this.name = name;
        this.balance = balance;
    }
    public Wallets(int id, int account_id, String name, Double balance) {
        super();
        this.id = id;
        this.account_id = id;
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }
    public Double getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
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
            String query = "SELECT id, account_id, name, balance FROM wallets";
            preparedStatement = conn.prepareStatement(query);
            res = preparedStatement.executeQuery();

            while (res.next()) {
                int id = res.getInt("id");
                int account_id = res.getInt("account_id");
                String name = res.getString("name");
                Double balance = res.getDouble("balance");
                walletItems.add(new Wallets(id, account_id, name, balance));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return walletItems;
    }
}
