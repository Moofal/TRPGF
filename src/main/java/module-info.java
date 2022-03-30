module com.example.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.json;


    opens com.example.example to javafx.fxml;
    exports com.example.example;
}