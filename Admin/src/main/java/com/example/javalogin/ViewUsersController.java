package com.example.javalogin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

public class ViewUsersController implements Initializable {

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
    private Button button_viewadmin;

    @FXML
    private Button button_back;

    @FXML
    private TableColumn<User, Void> deleteColumn;

    private boolean isAdminView = false;


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
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            if (isAdminView) {
                                // Handle action for admin view
                                System.out.println("Use MySQL");
                            } else {
                                deleteFromDatabase(user.getId());
                                tableView.getItems().remove(user);
                            }
                        });
                    }

                    private final Button useMySQLButton = new Button("Use MySQL");

                    {
                        useMySQLButton.setOnAction(event -> {
                            // Handle action for admin view
                            System.out.println("Use MySQL");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            if (isAdminView) {
                                setGraphic(useMySQLButton);
                            } else {
                                setGraphic(deleteButton);
                            }
                        }
                    }
                };
                return cell;
            }
        };
        deleteColumn.setCellFactory(cellFactory);

        button_viewusers.setOnAction(event ->{
            isAdminView = false;
            fetchData("users");});
        button_viewadmin.setOnAction(event ->{
            isAdminView = true;
            fetchData("admin");});
        button_back.setOnAction(this::handleBackButtonAction);
    }

    private void deleteFromDatabase(String userId) {
        // SQL query to delete the user from the database based on the ID
        String query = "DELETE FROM users WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("User with ID " + userId + " has been deleted successfully.");
            } else {
                System.out.println("No user found with ID " + userId + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void fetchData(String tableName)  {
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
                tableView.getItems().clear();
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

}
