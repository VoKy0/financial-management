package com.example.financial_management_app.utils;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    public Connection getConnection() {
        Connection connection = null;
        String url = "jdbc:mysql://192.168.1.14:3306/financial_management_db";
        String user = "fm_app";
        String password = "123123";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

            Log.i("ConnectDB", "Connect DB Successful");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Connect DB", "Connect DB failure");
        }

        return connection;
    }
}
