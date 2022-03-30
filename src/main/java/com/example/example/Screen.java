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

    private Scene startingScreen, tableScreen, characterCreatorScreen, endingScreen;

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
        nextScreenButton.setOnAction(e -> window.setScene(tableScreen));


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


        currentDialog = getDialog(1);
        String dialogString = currentDialog.getString("CONTENT");

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


        currentOptions = currentDialog.getJSONArray("dialogChoiceList");

        VBox vBox = new VBox();
        vBox.relocate(30,390);
        double optionY = 390;
        for (int i = 0; i < currentOptions.length(); i++) {
            optionY += 20;
            String text = currentOptions.getJSONObject(i).getString("CONTENT");
            vBox.getChildren().add(addOption(text, i+1, 30, optionY));
        }

        TextField choice = new TextField();
        choice.setOnAction(e -> {
            try {
                choseOption(layout, vBox, choice, dialog, currentOptions);
                double Y = 390;
                for (int i = 0; i < currentOptions.length(); i++) {
                    Y += 20;
                    String text = currentOptions.getJSONObject(i).getString("CONTENT");
                    vBox.getChildren().add(addOption(text, i+1, 30, Y));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        choice.relocate(10,640);
        layout.getChildren().addAll(horizontalLine, verticalLine, dialogHistory,
                dialogScrollPane, choice, vBox);
        tableScreen = new Scene(layout, 1280, 720);
    }

    private JSONObject getDialog(int dialogID) throws IOException {
        int index = dialogID-1;

        Path file = Path.of("src/story.json");
        String input = Files.readString(file);

        JSONArray array = new JSONArray(input);

        return array.getJSONObject(index);
    }

    private void choseOption(Pane layout, VBox vBox, TextField choice, Text dialog, JSONArray currentOptionsJSON) throws IOException {
        String chosenOption = choice.getText().replaceAll("[^1-3]", "");
        choice.setText("");

        int index = Integer.parseInt(chosenOption);
        int nextDialogID = currentOptionsJSON.getJSONObject(index-1).getInt("NEXT-SCENE");
        currentDialog = getDialog(nextDialogID);

        JSONObject nextDialogJSON = getDialog(nextDialogID);
        String dialogString = nextDialogJSON.getString("CONTENT");
        dialog.setText(dialogString);

        JSONArray nextOptions = nextDialogJSON.getJSONArray("dialogChoiceList");
        currentOptions = currentDialog.getJSONArray("dialogChoiceList");
        vBox.getChildren().clear();

    }

    private Text addOption(String text, int optionNr, double relocateX, double relocateY) {
        Text options = new Text();
        options.setId("option");
        options.setText(optionNr + text);
        options.relocate(relocateX,relocateY);
        options.setFont(Font.font(20));

        return options;
    }

    /**
     * Denne skjermen lar deg lage en character creation screen.
     * @param displayedText er text som viser på denne skjermen,
     */
    public void characterScreen(Stage window, String displayedText) {
        Pane characterCreationPane = new Pane();

        Label displayedTextLabel = new Label(displayedText);
        displayedTextLabel.setFont(Font.font("Arial",20));
        displayedTextLabel.setMaxSize(300, 200);
        displayedTextLabel.setWrapText(true);
        displayedTextLabel.relocate(500, 50);

        double x = 100,y = 100;
        for (int i = 0; i < 6; i++) {
            x+=50;
            createStats(characterCreationPane, "Stat", x, y);
        }

        TextField name = new TextField();
        name.relocate(300,300);
        if (true) {
            characterCreationPane.getChildren().add(name);
        }

        Button doneButton = new Button("Done");
        doneButton.relocate(630, 200);
        doneButton.setOnAction(e -> window.setScene(tableScreen));

        characterCreationPane.getChildren().addAll(displayedTextLabel);

        characterCreatorScreen = new Scene(characterCreationPane, 1280, 720);
    }

    private void createStats(Pane characterPane, String statName, double relocateX, double relocateY) {
        Label statLabel = new Label();
        statLabel.setText(statName);
        statLabel.setFont(Font.font("Arial", 15));
        statLabel.relocate(relocateX+4,relocateY-17);

        TextField stat = new TextField();
        stat.setPrefColumnCount(2);
        stat.relocate(relocateX, relocateY);

        characterPane.getChildren().add(statLabel);
        characterPane.getChildren().add(stat);
    }

    /**
     * Dette er slutskjermen til spillet, du kan ha flere forskjellige slutskjermer.
     * @param endingScreenId er verdien som lar deg velge å bruke denne bestemte slutskjermen.
     * @param sceneText er teksten som blir vist fram på denne skjermen.
     * @param startOver er en boolean verdi der true betyr at starting over knappen blir vist,
     *                  den knappen lar spiller såarte spillet på nytt.
     * @param exit er en boolean verdi der true betyr at exit knappen blir vist,
     *             den knappen avslutter spillet og lukker det.
     */
    public void endingScreen(Stage window, int endingScreenId, String sceneText, boolean startOver, boolean exit) {
        Pane endingPane = new Pane();

        Text endingText = new Text(sceneText);
        endingText.setFont(Font.font("Arial", 20));
        endingText.relocate(500,100);
        endingPane.getChildren().add(endingText);

        Button startOverButton = new Button("Start Over");
        startOverButton.relocate(300, 200);
        startOverButton.setOnAction(e -> window.setScene(startingScreen));

        if (startOver) {
            endingPane.getChildren().add(startOverButton);
        }

        Button exitButton = new Button("Exit");
        exitButton.relocate(600, 200);
        exitButton.setOnAction(e -> window.close());

        if (exit) {
            endingPane.getChildren().add(exitButton);
        }

        endingScreen = new Scene(endingPane, 1280, 720);
    }
}
