package com.example.javalogin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class UpdateController {




    @FXML
    private TextField tf_updateusername;

    @FXML
    private RadioButton rb_updatemale;

    @FXML
    private RadioButton rb_updatefemale;

    @FXML
    private Button button_back1;

    @FXML
    private Button button_updateuser;

    private String userId;
    private User dbuser;


    public void initData(User user) {
        this.dbuser = user;
        tf_updateusername.setText(user.getUsername());
        if (user.getGender().equals("Male")) {
            rb_updatemale.setSelected(true);
        } else {
            rb_updatefemale.setSelected(true);
        }
    }

    public void initId(String userId) {
        this.userId = userId;
        System.out.println("ID is " + userId);
    }



    public void handleUpdateUser() {
        String updatedUsername = tf_updateusername.getText();
        String updatedGender = rb_updatemale.isSelected() ? "Male" : "Female";
;
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "UPDATE users SET username = ?, gender = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, updatedUsername);
            statement.setString(2, updatedGender);
            statement.setString(3, userId);
            statement.executeUpdate();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javalogin/ViewandUpdate.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) button_updateuser.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javalogin/ViewandUpdate.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) button_back1.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

