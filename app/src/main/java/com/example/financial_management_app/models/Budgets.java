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

public class Budgets {
    private int id;
    private int account_id;
    private int wallet_id;
    private String name;
    private String note;
    private Double amount;
    private Date budget_date;
    private ConnectDB connectDB;
    private Connection conn;

    public Budgets() {
        id = -1;
        account_id = -1;
        wallet_id = -1;
        name = "";
        note = "";
        amount = 0.0;
        budget_date = new Date(2024,1,1);
        connectDB = new ConnectDB();
        conn = null;
    }

    public Budgets(String name, Double amount, String note, Date budget_date) {
        this();
        this.name = name;
        this.note = note;
        this.amount = amount;
        this.budget_date = budget_date;
    }
    public Budgets(int account_id, int wallet_id, String name, Double amount, String note, Date budget_date) {
        this(name, amount, note, budget_date);
        this.account_id = account_id;
        this.wallet_id = wallet_id;
    }
    public Budgets(int id, int account_id, int wallet_id, String name, Double amount, String note, Date budget_date) {
        this(account_id, wallet_id, name, amount, note, budget_date);
        this.id = id;
    }

    public int getID() {
        return id;
    }
    public int getWalletID() {
        return wallet_id;
    }
    public String getName() {
        return name;
    }
    public String getNote() {
        return note;
    }
    public Double getAmount() {
        return amount;
    }
    public Date getBudgetDate() {
        return budget_date;
    }

    public void setWalletID(int wallet_id) {
        this.wallet_id = wallet_id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public void setBudgetDate(Date budget_date) {
        this.budget_date = budget_date;
    }

    public String getWalletName(int wallet_id) {
        PreparedStatement preparedStatement = null;
        ResultSet res = null;
        String wallet_name = "";

        try {
            conn = connectDB.getConnection();
            String query = "SELECT name FROM wallets WHERE id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, wallet_id);
            res = preparedStatement.executeQuery();

            if (res.next()) {
                wallet_name = res.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wallet_name;
    }
    public List<Budgets> getBudgetsFromDB() {
        List<Budgets> budgetItems = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT id, account_id, wallet_id, name, amount, note, budget_date FROM budget_ledgers";
            preparedStatement = conn.prepareStatement(query);
            res = preparedStatement.executeQuery();

            while (res.next()) {
                int id = res.getInt("id");
                int account_id = res.getInt("account_id");
                int wallet_id = res.getInt("wallet_id");
                String name = res.getString("name");
                Double amount = res.getDouble("amount");
                String note = res.getString("note");
                Date budget_date = res.getDate("budget_date");
                budgetItems.add(new Budgets(id, account_id, wallet_id, name, amount, note, budget_date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return budgetItems;
    }
    public List<Budgets> getBudgetsByAccountID(int account_id_input) {
        List<Budgets> budgetItems = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT id, account_id, wallet_id, name, amount, note, budget_date FROM budget_ledgers WHERE account_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, account_id_input);
            res = preparedStatement.executeQuery();

            while (res.next()) {
                int id = res.getInt("id");
                int account_id = res.getInt("account_id");
                int wallet_id = res.getInt("wallet_id");
                String name = res.getString("name");
                Double amount = res.getDouble("amount");
                String note = res.getString("note");
                Date budget_date = res.getDate("budget_date");
                budgetItems.add(new Budgets(id, account_id, wallet_id, name, amount, note, budget_date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return budgetItems;
    }

    public Budgets getBudgetByID(int budget_id) {
        Budgets budget = null;
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT id, account_id, wallet_id, name, amount, note, budget_date FROM budget_ledgers WHERE id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, budget_id);
            res = preparedStatement.executeQuery();

            while (res.next()) {
                int id = res.getInt("id");
                int account_id = res.getInt("account_id");
                int wallet_id = res.getInt("wallet_id");
                String name = res.getString("name");
                Double amount = res.getDouble("amount");
                String note = res.getString("note");
                Date budget_date = res.getDate("budget_date");
                budget = new Budgets(id, account_id, wallet_id, name, amount, note, budget_date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return budget;
    }

    public void addBudget() {
        PreparedStatement preparedStatement = null;

        try {
            conn = connectDB.getConnection();
            String query = "INSERT INTO budget_ledgers(account_id, wallet_id, name, amount, note, budget_date) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, account_id);
            preparedStatement.setInt(2, wallet_id);
            preparedStatement.setString(3, name);
            preparedStatement.setDouble(4, amount);
            preparedStatement.setString(5, note);
            preparedStatement.setDate(6, budget_date);

            preparedStatement.executeUpdate();
            Log.i("Add Budget","Add Budget successful.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBudget(Budgets budget) {
        PreparedStatement preparedStatement = null;

        try {
            conn = connectDB.getConnection();
            String query =  "UPDATE budget_ledgers " +
                            "SET wallet_id = ?, name = ?, amount = ?, note = ?, budget_date = ? " +
                            "WHERE id = ?";
           preparedStatement = conn.prepareStatement(query);

           preparedStatement.setInt(1, budget.wallet_id);
           preparedStatement.setString(2, budget.name);
           preparedStatement.setDouble(3, budget.amount);
           preparedStatement.setString(4, budget.note);
           preparedStatement.setDate(5, budget.budget_date);
           preparedStatement.setInt(6, budget.id);

           preparedStatement.executeUpdate();
           Log.i("Update Budget", "Update budget successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
