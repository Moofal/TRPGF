module com.example.example {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.example to javafx.fxml;
    exports com.example.example;
}