package com.example.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load());
        stage.setTitle("Hello!");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {



        /*
        Choice choiceBox = new Choice();
        choiceBox.createChoices(1, "Would u like a room", Color.GREEN);
        choiceBox.addOption(1, 1, "Innkeeper blah blah", Color.GREEN, 3);
        choiceBox.addOption(2, 1, "yes 50 please", Color.GREEN, 4);
        choiceBox.addOptionStatCheck(3, 1, "let me drink 100 beers", Color.GREEN, 200, "Strength", 15); // var for å vise fram
        choiceBox.createChoices(20, "There is a dragon in front of you what do you do", Color.GREEN);
        choiceBox.addOptionPrevious(522, 20, 2, "slay it", Color.GREEN,0);
        choiceBox.addOption(5425, 20, "seduce the dragon", Color.GREEN, 0);
         */

        launch();
    }
}