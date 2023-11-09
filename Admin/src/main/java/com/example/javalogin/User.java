package com.example.javalogin;

import javafx.beans.property.SimpleStringProperty;

public class User {
    private final SimpleStringProperty id;
    private final SimpleStringProperty username;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty createdAt;

    public User(String id, String username, String gender, String createdAt) {
        this.id = new SimpleStringProperty(id);
        this.username = new SimpleStringProperty(username);
        this.gender = new SimpleStringProperty(gender);
        this.createdAt = new SimpleStringProperty(createdAt);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getGender() {
        return gender.get();
    }

    public SimpleStringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getCreatedAt() {
        return createdAt.get();
    }

    public SimpleStringProperty createdAtProperty() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt.set(createdAt);
    }
}
