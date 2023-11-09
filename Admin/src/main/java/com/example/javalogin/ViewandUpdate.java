package com.example.javalogin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ViewandUpdate implements Initializable {

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> genderColumn;

    @FXML
    private TableColumn<User, String> idColumn;

    @FXML
    private TableColumn<User, String> createdAtColumn;

    @FXML
    private Button button_viewusers;


    @FXML
    private Button button_back;

    @FXML
    private TableColumn<User, Void> updateColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        genderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        createdAtColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAt()));

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<>() {
                    private final Button updateButton = new Button("Update");

                    {
                        updateButton.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            handleUpdateButtonAction(user);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(updateButton);
                        }
                    }
                };
                return cell;
            }
        };

        button_viewusers.setOnAction(event -> fetchData("users"));
        button_back.setOnAction(this::handleBackButtonAction);
        updateColumn.setCellFactory(cellFactory);
    }

    private void handleUpdateButtonAction(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javalogin/update.fxml"));
            Parent root = loader.load();
            UpdateController controller = loader.getController();
            controller.initData(user); // Pass the selected User object to the controller
            controller.initId(user.getId()); // Pass the ID of the selected User to the controller

            Scene currentScene = button_back.getScene();
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchData(String tableName) {
        ObservableList<User> userList = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, username, gender, created_at from " + tableName)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String retrievedId = resultSet.getString("id");
                String retrievedUsername = resultSet.getString("username");
                String retrievedGender = resultSet.getString("gender");
                String retrievedCreatedAt = resultSet.getString("created_at");
                userList.add(new User(retrievedId, retrievedUsername, retrievedGender, retrievedCreatedAt));
            }

            if (tableView != null) {
                tableView.setItems(userList);
            } else {
                System.out.println("TableView is null. Check FXML file.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleBackButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javalogin/logged-in.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) button_back.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpdateButton(ActionEvent event) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        handleUpdateButtonAction(selectedUser);
    }
}
