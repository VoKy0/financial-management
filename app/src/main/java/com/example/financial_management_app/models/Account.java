package com.example.financial_management_app.models;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import javax.xml.transform.Result;

import com.example.financial_management_app.utils.ConnectDB;

public class Account {
    private int id = -1;
    private int user_id = -1;
    private String email = "";
    private String password = "";
    private String avatar_url = "";
    private ConnectDB connectDB = new ConnectDB();
    private Connection conn;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isValidEmail() {
         return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean isValidPassword() {
        return !TextUtils.isEmpty(password) && password.length() > 6;
    }
    public int checkAccount() {
        try {
            ResultSet res = findAccountFromDB(email);
            if (res != null && res.next()) {
                String pass = res.getString("password");
                if (password.equals(pass))
                    // Đúng mật khẩu
                    return 1;
                // Sai mật khẩu
                return 0;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        // Có lỗi xảy ra hoặc là không tồn tại tài khoản
        return -1;
    }

    public ResultSet findAccountFromDB(String email) {
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {
            Connection conn = connectDB.getConnection();

            if (conn != null) {
                String query = "SELECT email, password FROM accounts WHERE email=?";
                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, email);
                res = preparedStatement.executeQuery();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public ResultSet getEmailFromDB() {
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT email FROM accounts";
            preparedStatement = conn.prepareStatement(query);
            res = preparedStatement.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
    public ResultSet getPasswordFromDB() {
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT password FROM accounts";
            preparedStatement = conn.prepareStatement(query);
            res = preparedStatement.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public void addAccount() {
        user_id = getMaxUserID() + 1;
        PreparedStatement preparedStatement = null;

        try {
            conn = connectDB.getConnection();
            String query = "INSERT INTO accounts(user_id, email, password, avatar_url) VALUES (?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, user_id);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, avatar_url);

            preparedStatement.executeUpdate();
            Log.i("Add Account","Add account successful.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getMaxUserID() {
        PreparedStatement preparedStatement = null;
        ResultSet res = null;
        int max_uid = -1;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT MAX(user_id) FROM accounts";
            preparedStatement = conn.prepareStatement(query);
            res = preparedStatement.executeQuery();

            if (res.next()) {
                max_uid = res.getInt("user_id");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return max_uid;
    }
}
