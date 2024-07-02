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

    public int getID() {
        return id;
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
    public List<Wallets> getWalletsByAccountID(int input_account_id) {
        List<Wallets> walletItems = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT id, account_id, name, category, balance FROM wallets WHERE account_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, input_account_id);
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

    public Wallets getWalletById(int id) {
        Wallets wallet = null;
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT id, account_id, name, category, balance FROM wallets WHERE id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();

            if (res.next()) {
                int walletId = res.getInt("id");
                int accountId = res.getInt("account_id");
                String name = res.getString("name");
                String category = res.getString("category");
                Double balance = res.getDouble("balance");
                wallet = new Wallets(walletId, accountId, name, category, balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wallet;
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

    public void updateWallet(Wallets wallet) {
        PreparedStatement preparedStatement = null;

        try {
            conn = connectDB.getConnection();
            String query = "UPDATE wallets SET name = ?, category = ?, balance = ? WHERE id = ?";
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, wallet.getName());
            preparedStatement.setString(2, wallet.getCategory());
            preparedStatement.setDouble(3, wallet.getBalance());
            preparedStatement.setInt(4, wallet.getID());

            preparedStatement.executeUpdate();
            Log.i("Update Wallet", "Update wallet successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
