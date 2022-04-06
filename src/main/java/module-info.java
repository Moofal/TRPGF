module org.TRPGF {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.json;
    requires com.fasterxml.jackson.databind;

    opens org.TRPGF to javafx.fxml;
    exports org.TRPGF;
}