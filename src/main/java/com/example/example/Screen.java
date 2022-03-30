package com.example.example;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Denne klassen blir brukt til å lage GUI som spilleren kommer til å bruke.
 */
public class Screen {

    public Screen() {

    }

    private Scene startingScreen, tableScreen, characterCreatorScreen;

    private JSONObject currentDialog;
    private JSONArray currentOptions;

    /**
     * Dette er den første skjermen som spilleren kommer til å se.
     * @param gameTitle er navnet på spillet.
     * @param startingText er intro teksten som bli vist fram til spilleren.
     */
    public void startingScreen (Stage window, String gameTitle, String startingText) {
        window.setTitle(gameTitle);
        Pane layout = new Pane();

        Label title = new Label(gameTitle);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        title.setMaxWidth(300);
        title.setWrapText(true);
        title.relocate(500,20);

        Label text = new Label(startingText);
        text.setFont(Font.font("Arial",20));
        text.setMaxSize(300, 200);
        text.setWrapText(true);
        text.relocate(500,100);

        Button nextScreenButton = new Button("Next");
        nextScreenButton.relocate(630, 200);
        nextScreenButton.setOnAction(e -> window.setScene(characterCreatorScreen));


        layout.getChildren().addAll(title,text, nextScreenButton);
        startingScreen = new Scene(layout, 1280 , 720);

        window.setScene(startingScreen);
        window.show();
    }

    /**
     * Lager skjermen der spillet blir spilt.
     */
    public void tableScreen(Stage window) throws IOException {
        Pane layout = new Pane();

        Line horizontalLine = new Line();
        horizontalLine.setStartX(0.0f);
        horizontalLine.setStartY(360.0f);
        horizontalLine.setEndX(1280.0f);
        horizontalLine.setEndY(360.0f);

        Line verticalLine = new Line();
        verticalLine.setStartX(640.0f);
        verticalLine.setStartY(0.0f);
        verticalLine.setEndX(640.0f);
        verticalLine.setEndY(720.0f);


        currentDialog = getDialog("1");
        String dialogString = currentDialog.getString("content");

        Text dialog = new Text(dialogString);
        dialog.setWrappingWidth(625);
        dialog.setFont(Font.font("Arial", 30));

        ScrollPane dialogScrollPane = new ScrollPane();
        dialogScrollPane.setContent(dialog);
        dialogScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        dialogScrollPane.relocate(0,0);
        dialogScrollPane.setPrefSize(640,360);

        ScrollPane dialogHistory = new ScrollPane();
        //dialogHistory.setContent(dialog2);
        dialogHistory.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        dialogHistory.relocate(640,0);
        dialogHistory.setPrefSize(640,360);


        currentOptions = currentDialog.getJSONArray("options");

        Text options = new Text();
        options.setText(formatOptions(currentOptions));
        options.relocate(30,400);
        options.setFont(Font.font(20));



        TextField choice = new TextField();
        choice.setOnAction(e -> {
            try {
                choseOption(choice, dialog, options, currentOptions);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        choice.relocate(10,640);

        layout.getChildren().addAll(horizontalLine, verticalLine, dialogHistory, dialogScrollPane,
                options, choice);
        tableScreen = new Scene(layout, 1280, 720);
    }

    private JSONObject getDialog(String dialogID) throws IOException {
        int index = Integer.parseInt(dialogID)-1;

        Path file = Path.of("src/main/java/com/example/example/dialog.json");
        String input = Files.readString(file);

        JSONObject obj = new JSONObject(input);

        JSONArray array = obj.getJSONArray("dialog");

        return array.getJSONObject(index);
    }

    private void choseOption(TextField choice, Text dialog, Text options, JSONArray currentOptionsJSON) throws IOException {
        String chosenOption = choice.getText().replaceAll("[^1-3]", "");
        choice.setText("");

        int index = Integer.parseInt(chosenOption);
        String nextDialogID = currentOptionsJSON.getJSONObject(index-1).getString("next");
        currentDialog = getDialog(nextDialogID);

        JSONObject nextDialogJSON = getDialog(nextDialogID);
        String dialogString = nextDialogJSON.getString("content");
        dialog.setText(dialogString);

        JSONArray nextOptions = nextDialogJSON.getJSONArray("options");
        currentOptions = currentDialog.getJSONArray("options");
        options.setText(formatOptions(nextOptions));

    }

    private String formatOptions(JSONArray currentOptions) {
        String option1 = currentOptions.getJSONObject(0).getString("content");
        String option2 = currentOptions.getJSONObject(1).getString("content");
        String option3 = currentOptions.getJSONObject(2).getString("content");

        return "1. " + option1 + "\n" +
                "2. " + option2 + "\n" +
                "3. " + option3;
    }

    /**
     * Denne skjermen lar deg lage en character creation screen.
     * @param displayedText er text som viser på denne skjermen,
     */
    public void characterScreen (String displayedText) {
        Pane characterCreationPane = new Pane();

        Label displayedTextLabel = new Label(displayedText);
        displayedTextLabel.setFont(Font.font("Arial",20));
        displayedTextLabel.setMaxSize(300, 200);
        displayedTextLabel.setWrapText(true);
        displayedTextLabel.relocate(500, 50);

        Label statLabel = new Label();
        statLabel.setText("sdf");
        statLabel.setFont(Font.font("Arial", 15));
        statLabel.relocate(104,83);

        TextField stat1 = new TextField();
        stat1.setPrefColumnCount(2);
        stat1.relocate(100, 100);

        characterCreationPane.getChildren().addAll(displayedTextLabel, stat1);
        characterCreationPane.getChildren().add(statLabel);
        characterCreatorScreen = new Scene(characterCreationPane, 1280, 720);
    }

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
    public void endingScreen (int endingScreenId, String sceneText, Color textColor, boolean startOver, boolean exit) {

    }
}
