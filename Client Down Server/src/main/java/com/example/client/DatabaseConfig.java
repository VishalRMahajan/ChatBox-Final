package com.example.client;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    // Change these values to match your database configuration
    private static final String URL = "jdbc:mysql://192.168.153.199:3307/users";
    private static final String USERNAME = "Vishal_Mahajan";
    private static final String PASSWORD = "050607";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
//NOTE TO SELF
//Change In View and Update Also