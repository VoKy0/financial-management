package com.example.financial_management_app.models;

import android.util.Log;

import com.example.financial_management_app.utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Users {
    private int id;
    private String first_name;
    private String last_name;
    private String dob;
    private String address;
    private ConnectDB connectDB;
    private Connection conn;

    public Users() {
        id = -1;
        first_name = "";
        last_name = "";
        dob = "";
        address = "";
        connectDB = new ConnectDB();
        conn = null;
    }

    public Users(int id, String first_name, String last_name, String dob, String address) {
        super();
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.address = address;
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
    public String getDob() {
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
    public void setDob(String dob) {
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

    public void addUser() {
        PreparedStatement preparedStatement = null;

        try {
            conn = connectDB.getConnection();
            String query = "INSERT INTO users(first_name, last_name, dob, address) VALUES (?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, dob);
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
            preparedStatement.setString(3, dob);
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
