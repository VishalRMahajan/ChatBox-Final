package com.example.javalogin;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnection {
    public static void main(String[] args) {
        Connection connection = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

           connection = DatabaseConfig.getConnection();

            System.out.println("Connected to the database!");



        } catch (Exception e) {
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
