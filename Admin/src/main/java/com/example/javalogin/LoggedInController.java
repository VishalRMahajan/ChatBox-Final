package com.example.javalogin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.net.InetAddress;

public class LoggedInController implements Initializable {

    @FXML
    private Button button_logout;


    @FXML
    private Button button_viewandupdatepath;

    @FXML
    private Button button_adduserpath;

    @FXML
    private Button button_viewpath;




    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DBUtils.changeScene(event, "sample.fxml", "Log-in", null, null);

            }
        });

        button_viewandupdatepath.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "ViewandUpdate.fxml", "View and Update", null, null);

            }
        });

        button_adduserpath.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DBUtils.changeScene(event, "AddUser.fxml", "Add User", null, null);

            }
        });

        button_viewpath.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DBUtils.changeScene(event, "ViewUsers.fxml", "View Entries", null, null);

            }
        });




    }





}






