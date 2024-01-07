package com.bank.app.Classes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

//class used to store password to database
public class DataBaseHandler {
    private static String  url;
    private static String user;
    private static String password;

    //connect to database
    //if connection fails return null
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void loadProperties() {
        Properties properties = new Properties();
        FileInputStream input;
        try {
            input = new FileInputStream("config.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        url = properties.getProperty("db.url");
        user = properties.getProperty("db.user");
        password = properties.getProperty("db.password");
    }
}

