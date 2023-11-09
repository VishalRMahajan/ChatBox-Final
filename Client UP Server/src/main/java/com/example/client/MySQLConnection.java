package com.example.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            String url = "jdbc:mysql://192.168.153.199:3307/users";
            String username = "Vishal_Mahajan"; // Replace 'root' with your MySQL username
            String password = "050607"; // Replace 'your_password' with your MySQL password
            connection = DriverManager.getConnection(url, username, password);

            // Connection successful
            System.out.println("Connected to the database!");

            // Perform your database operations here

        } catch (ClassNotFoundException e) {
            // Handle errors related to loading the JDBC driver
            e.printStackTrace();
        } catch (SQLException e) {
            // Handle any errors that may have occurred
            e.printStackTrace();
        } finally {
            // Close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
