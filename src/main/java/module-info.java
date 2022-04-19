module org.TRPGF {
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    requires org.json;
    requires com.fasterxml.jackson.databind;

    opens org.TRPGF to javafx.controls;
    exports org.TRPGF;
}