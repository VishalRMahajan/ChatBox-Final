package com.example.javalogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBUtils{
    private static Connection connection = null;
    public static void changeScene(ActionEvent event, String fxmlFile,String title, String username, String gender){
        Parent root=null;

        if (username!=null && gender!=null){
            try{
                FXMLLoader loader= new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root=loader.load();
                LoggedInController loggedInController=loader.getController();



            }catch (IOException e){
                e.printStackTrace();
            }

        }
        else {
            try {
                root=FXMLLoader.load(DBUtils.class.getResource(fxmlFile));

            }catch (IOException e){
                e.printStackTrace();
             }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root,851, 536));
        stage.show();
    }

    public static void signUpAdmin(ActionEvent event, String username, String password, String gender){
        PreparedStatement psInsert =null;
        PreparedStatement psCheckUserExists=null;
        ResultSet resultSet=null;

        try {
            Connection connection = DatabaseConfig.getConnection();
            psCheckUserExists=connection.prepareStatement( "SELECT * FROM admin WHERE username= ?");
            psCheckUserExists.setString(1, username);
            resultSet=psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("Admin already exists.");
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            }else {
                psInsert=connection.prepareStatement("INSERT INTO admin (username, password, gender) VALUES (?, ?, ?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setString(3,gender);
                psInsert.executeUpdate();

                changeScene(event,"logged-In.fxml","Welcome!",username,gender);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet!=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists!=null){
                try{
                    psCheckUserExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert!=null){
                try{
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logInAdmin(ActionEvent event, String username, String password){
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try{
            Connection connection = DatabaseConfig.getConnection();
            preparedStatement=connection.prepareStatement( "SELECT password, gender FROM admin WHERE username= ?");
            preparedStatement.setString( 1, username);
            resultSet=preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in database.");
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect.");
                alert.show();
            }else {
                while (resultSet.next()){
                    String retrievedPassword =resultSet.getString( "password");
                    String retrievedGender=resultSet.getString("gender");
                    if (retrievedPassword.equals(password)){
                        changeScene(event, "logged-In.fxml","Welcome",username,retrievedGender);
                    }else {
                        System.out.println("Password did not match");
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The credentials provided are incorrect");
                        alert.show();
                    }
                }

            }
        }catch (SQLException e){
            e.printStackTrace();

        }finally {
            if (resultSet!=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (preparedStatement!=null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void signUpUser(ActionEvent event, String username, String password, String gender){

        PreparedStatement psInsert =null;
        PreparedStatement psCheckUserExists=null;
        ResultSet resultSet=null;

        try {
            Connection connection = DatabaseConfig.getConnection();
            psCheckUserExists=connection.prepareStatement( "SELECT * FROM users WHERE username= ?");
            psCheckUserExists.setString(1, username);
            resultSet=psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("User already exists.");
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            }else {
                psInsert=connection.prepareStatement("INSERT INTO users (username, password, gender) VALUES (?, ?, ?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setString(3,gender);
                psInsert.executeUpdate();

                changeScene(event,"logged-In.fxml","Welcome!",username,gender);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet!=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists!=null){
                try{
                    psCheckUserExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert!=null){
                try{
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password){
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try{
            Connection connection = DatabaseConfig.getConnection();
            preparedStatement=connection.prepareStatement( "SELECT password, gender FROM users WHERE username= ?");
            preparedStatement.setString( 1, username);
            resultSet=preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in database.");
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect.");
                alert.show();
            }else {
                while (resultSet.next()){
                    String retrievedPassword =resultSet.getString( "password");
                    String retrievedGender=resultSet.getString("gender");
                    if (retrievedPassword.equals(password)){
                        changeScene(event, "logged-In.fxml","Welcome",username,retrievedGender);
                    }else {
                        System.out.println("Password did not match");
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The credentials provided are incorrect");
                        alert.show();
                    }
                }

            }
        }catch (SQLException e){
            e.printStackTrace();

        }finally {
            if (resultSet!=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (preparedStatement!=null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
