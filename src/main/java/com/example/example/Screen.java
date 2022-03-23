package com.example.example;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;



public class Screen {
    /**
     * Denne klassen blir brukt til å lage GUI som spilleren kommer til å bruke.
     */

    public Screen() {

    }

    private Stage stage;
    private Scene scene, scene2, scene1;
    private Parent root;

    public Scene test(Stage window) {
        Label label = new Label("Scene 1");
        Button button = new Button();
        button.setText("Chage to 2");
        button.setOnAction(e -> window.setScene(scene2));

        AnchorPane layout = new AnchorPane();
        layout.getChildren().addAll(label, button);

        scene1 = new Scene(layout, 400 , 300);

        return scene1;
    }

    public void startingScreen (Stage setStage, String gameTitle, String startingText) throws IOException {
        /**
         * Dette er den første skjermen som spilleren kommer til å se.
         * @param gameTitle er navnet på spillet.
         * @param startingText er intro teksten som bli vist fram til spilleren.
         */


    }

    public Scene tableScreen(Stage window) {
        /**
         * Lager skjermen der spillet blir spilt.
         */
        Label label = new Label("Scene 1");
        Button button = new Button();
        button.setText("Chage to 1");
        button.setOnAction(e -> window.setScene(scene1));

        AnchorPane layout = new AnchorPane();
        layout.getChildren().addAll(label, button);

        scene2 = new Scene(layout, 400 , 300);

        return scene2;
    }

    public void characterScreen (String displayedText) {
        /**
         * Denne skjermen lar deg lage en character creation screen.
         * @param displayedText er text som viser på denne skjermen,
         */

    }

    public void endingScreen (int endingScreenId, String sceneText, Color textColor, boolean startOver, boolean exit) {
        /**
         * Dette er slutskjermen til spillet, du kan ha flere forskjellige slutskjermer.
         * @param endingScreenId er verdien som lar deg velge å bruke denne bestemte slutskjermen.
         * @param sceneText er teksten som blir vist fram på denne skjermen.
         * @param textColor er fargen som teksten i sceneText kommer til å ha.
         * @param startOver er en boolean verdi der true betyr at starting over knappen blir vist,
         *                  den knappen lar spiller såarte spillet på nytt.
         * @param exit er en boolean verdi der true betyr at exit knappen blir vist,
         *             den knappen avslutter spillet og lukker det.
         */
    }
}
