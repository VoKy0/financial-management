package com.example.financial_management_app.models;

import android.util.Log;

import com.example.financial_management_app.utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id = -1;
    private String first_name = "";
    private String last_name = "";
    private String dob = null;
    private String address = "";
    private ConnectDB connectDB = new ConnectDB();
    private Connection conn = null;

    public User(String first_name, String last_name, String dob, String address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.address = address;
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
}
