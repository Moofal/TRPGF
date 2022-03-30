package com.example.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Screen screen = new Screen();
        //screen.startingScreen(stage,"Game title", "ello there");
        stage.setTitle("title");
        Scene scene1 = screen.test(stage);
        Scene scene2 = screen.tableScreen(stage);
        stage.setScene(scene1);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}