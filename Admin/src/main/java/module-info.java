module com.example.javalogin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.javalogin to javafx.fxml;
    exports com.example.javalogin;
}