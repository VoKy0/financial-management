package com.example.financial_management_app.models;

import android.util.Log;

import com.example.financial_management_app.utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Users {
    private int id;
    private int account_id;
    private String first_name;
    private String last_name;
    private Date dob;
    private String address;
    private ConnectDB connectDB;
    private Connection conn;

    public Users() {
        id = -1;
        account_id = -1;
        first_name = "";
        last_name = "";
        dob = null;
        address = "";
        connectDB = new ConnectDB();
        conn = null;
    }

    public Users(String first_name, String last_name, Date dob, String address) {
        this();
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.address = address;
    }

    public Users(int id, int account_id, String first_name, String last_name, Date dob, String address) {
        this(first_name, last_name, dob, address);
        this.id = id;
        this.account_id = account_id;
    }

    public int getID() {
        return id;
    }
    public String getFirstName() {
        return first_name;
    }
    public String getLastName() {
        return last_name;
    }
    public String getFullName() {
        return last_name + " " + first_name;
    }
    public Date getDob() {
        return dob;
    }
    public String getAddress() {
        return address;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }
    public void setLastName(String last_name) {
        this.last_name = last_name;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isValidFirstName() {
        return !first_name.isEmpty();
    }
    public boolean isValidLastName() {
        return !last_name.isEmpty();
    }
    public boolean isValidDob() {
        return (dob != null);
    }
    public boolean isValidAddress() {
        return !address.isEmpty();
    }

    public Users getUserByAccountID(int account_id) {
        PreparedStatement preparedStatement = null;
        ResultSet res = null;
        Users user = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT id, account_id, first_name, last_name, dob, address FROM users WHERE account_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, account_id);
            res = preparedStatement.executeQuery();

            if (res.next()) {
                user = new Users(
                        res.getInt("id"),
                        res.getInt("account_id"),
                        res.getString("first_name"),
                        res.getString("last_name"),
                        res.getDate("dob"),
                        res.getString("address")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    public void addUser() {
        PreparedStatement preparedStatement = null;

        try {
            conn = connectDB.getConnection();
            String query = "INSERT INTO users(first_name, last_name, dob, address) VALUES (?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setDate(3, dob);
            preparedStatement.setString(4, address);

            preparedStatement.executeUpdate();
            Log.i("Add User", "Insert user into database successful.");
        }
        catch (SQLException e) {
            e.printStackTrace();
            Log.e("Add User", "Insert user into database failure.");
        }
    }

    public void updateUser(int userID) {
        PreparedStatement preparedStatement = null;

        try {
            conn = connectDB.getConnection();
            String query = "UPDATE users SET first_name=?, last_name=?, dob=?, address=? WHERE id=?";
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setDate(3, dob);
            preparedStatement.setString(4, address);
            preparedStatement.setInt(5, userID);

            preparedStatement.executeUpdate();
            Log.i("Update User", "Update user in database successful.");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("Update User", "Update user in database failure.");
        }
    }
}
