package com.example.example;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Denne klassen blir brukt til å lage GUI som spilleren kommer til å bruke.
 */
public class Screen {

    public Screen() {

    }

    private Scene startingScreen, tableScreen, characterCreatorScreen, endingScreen;
    VBox characterStatInfo = new VBox();
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
    public void tableScreen(Stage window) {
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
        assert currentDialog != null;
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

        VBox optionsVBox = new VBox();
        optionsVBox.relocate(30,390);
        setCurrentOptions(optionsVBox);

        TextField choice = new TextField();
        choice.setOnAction(e -> {
            try {
                attemptOption(layout, optionsVBox, choice, dialog, currentOptions);
                setCurrentOptions(optionsVBox);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        choice.relocate(10,640);


        characterStatInfo.relocate(700,500);
        displayCharacterStats(characterStatInfo);

        layout.getChildren().addAll(horizontalLine, verticalLine, dialogHistory,
                dialogScrollPane, choice, optionsVBox, characterStatInfo);
        tableScreen = new Scene(layout, 1280, 720);
    }

    private JSONObject getDialog(int dialogID) {
        try {
            int index = dialogID-1;

            Path file = Path.of("src/story.json");
            String input = Files.readString(file);

            JSONArray array = new JSONArray(input);

            return array.getJSONObject(index);
        } catch (IOException e) {
            System.out.println(e + "src/story.json");
        }
        return null;
    }

    private JSONObject getCharacterInfo() {
        try {
            Path characterPath = Path.of("src/Character.json");
            String input = Files.readString(characterPath);
            return new JSONObject(input);
        } catch (IOException e) {
            System.out.println(e+"\n Could not find Character.json file");
        }
        return null;
    }

    private void setCurrentOptions(VBox vBox) {
        double optionY = 390;
        for (int i = 0; i < currentOptions.length(); i++) {
            optionY += 20;
            String text = currentOptions.getJSONObject(i).getString("CONTENT");
            vBox.getChildren().add(addOption(text, i+1, 30, optionY));
        }
    }

    private Text addOption(String text, int optionNr, double relocateX, double relocateY) {
        Text options = new Text();
        options.setId("option");
        options.setText(optionNr + text);
        options.relocate(relocateX,relocateY);
        options.setFont(Font.font(20));

        return options;
    }

    private void displayCharacterStats(VBox characterStatInfo) {
        characterStatInfo.getChildren().clear();
        JSONObject character = getCharacterInfo();
        assert character != null;
        JSONArray stats = character.getJSONArray("Stats");

        for (int i=0; i<stats.length(); i++) {
            JSONObject stat = stats.getJSONObject(i);
            String info =stat.getString("Name")+" "+stat.getInt("Value");
            Text statText = new Text();
            statText.setText(info);
            characterStatInfo.getChildren().add(statText);
        }
    }

    private void attemptOption(Pane layout, VBox vBox, TextField choice, Text dialog, JSONArray currentOptionsJSON) throws IOException {
        String chosenOption = choice.getText().replaceAll("[^1-3]", "");
        choice.setText("");

        int index = Integer.parseInt(chosenOption);
        JSONObject chosenOptionObject = currentOptionsJSON.getJSONObject(index-1);
        String optionType = chosenOptionObject.getString("TYPE");

        switch (optionType) {
            case "Normal Choice":
                int nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                optionChosen(vBox, dialog, nextDialogID);
                break;
            case "Previous Choice":
                break;
            case "Choice with Requirement":
                checkRequirementAndSetScreen(vBox, dialog, chosenOptionObject);
                break;
            case "Choice with a reward":
                nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                updateCharacterJson(chosenOptionObject);
                optionChosen(vBox, dialog, nextDialogID);
                break;
        }
    }

    private void updateCharacterJson(JSONObject chosenOptionObject) {
        String statIncreased = chosenOptionObject.getString("STAT");
        int statIncreasedAmount = chosenOptionObject.getInt("REWARD-VAL");

        JSONObject character = getCharacterInfo();
        assert character != null;
        JSONArray characterStats = character.getJSONArray("Stats");
        JSONObject requiredStat = null;
        for (int i=0; i<characterStats.length(); i++) {
            String statName = characterStats.getJSONObject(i).getString("Name");
            if (Objects.equals(statName,statIncreased)) {
                requiredStat = characterStats.getJSONObject(i);
                int currentStatValue = requiredStat.getInt("Value");
                int newValue = currentStatValue+statIncreasedAmount;
                requiredStat.put("Value", newValue);
            }
        }
        try {
            FileWriter characterFile = new FileWriter("src/Character.json");
            characterFile.write(character.toString());
            characterFile.close();
            displayCharacterStats(characterStatInfo);
        } catch (IOException e) {
            System.out.println(e+" Could not find Character.json");
        }
    }

    private void checkRequirementAndSetScreen(VBox vBox, Text dialog, JSONObject chosenOptionObject) {
        int nextDialogID;
        JSONArray characterStats = Objects.requireNonNull(getCharacterInfo()).getJSONArray("Stats");
        String requiredStatName = chosenOptionObject.getString("STAT");
        int requiredStatValue = chosenOptionObject.getInt("STAT-REQ-VAL");
        JSONObject requiredStat = null;
        for (int i=0; i<characterStats.length(); i++) {
            String statName = characterStats.getJSONObject(i).getString("Name");
            if (Objects.equals(statName,requiredStatName)) {
                requiredStat = characterStats.getJSONObject(i);
            }
        }


        assert requiredStat != null;
        if (requiredStat.getInt("Value") >= requiredStatValue) {
            // The dialog option had no requirements
            // Getting the id of the next dialog from the chosen option
            nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
            optionChosen(vBox, dialog, nextDialogID);
        } else {
            nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
            optionChosen(vBox, dialog, nextDialogID);
        }
    }

    private void optionChosen(VBox vBox, Text dialog, int nextDialogID) {
        // Setting the dialog text box to be the new dialog text
        JSONObject nextDialogJSON = getDialog(nextDialogID);
        assert nextDialogJSON != null;
        String dialogString = nextDialogJSON.getString("CONTENT");
        dialog.setText(dialogString);
        // Setting the dialog options to be the options for the new dialog
        currentDialog = getDialog(nextDialogID);
        assert currentDialog != null;
        currentOptions = currentDialog.getJSONArray("dialogChoiceList");
        // This removes the old dialog options text
        vBox.getChildren().clear();
    }




    /**
     * Denne skjermen lar deg lage en character creation screen.
     * @param displayedText er text som viser på denne skjermen,
     */
    public void characterScreen(Stage window, String displayedText) throws IOException {
        Pane characterCreationPane = new Pane();

        Label displayedTextLabel = new Label(displayedText);
        displayedTextLabel.setFont(Font.font("Arial",20));
        displayedTextLabel.setMaxSize(300, 200);
        displayedTextLabel.setWrapText(true);
        displayedTextLabel.relocate(500, 50);

        Path characterFile = Path.of("src/Character.json");
        String characterInput = Files.readString(characterFile);
        JSONObject character = new JSONObject(characterInput);

        Path settingsFile = Path.of("src/CharCreatSettings.json");
        String settingsInput = Files.readString(settingsFile);
        JSONObject settings = new JSONObject(settingsInput);

        JSONArray arrayOfStats = settings.getJSONArray("Stats");

        double x = 100,y = 100;
        ArrayList<TextField> storedArray = new ArrayList<>();
        for (int i = 0; i < arrayOfStats.length(); i++) {
            x+=60;
            JSONObject statObject = arrayOfStats.getJSONObject(i);
            String statName = statObject.getString("Name");
            createStatTextField(characterCreationPane, statName, x, y, storedArray);
        }

        TextField name = new TextField();
        name.relocate(300,300);
        if (settings.getBoolean("Name Option")) {
            characterCreationPane.getChildren().add(name);
        }

        Button doneButton = new Button("Done");
        doneButton.relocate(630, 200);
        doneButton.setOnAction(e -> {
            try {
                characterCreated(window, character, name, storedArray);
                displayCharacterStats(characterStatInfo);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        characterCreationPane.getChildren().addAll(displayedTextLabel, doneButton);

        characterCreatorScreen = new Scene(characterCreationPane, 1280, 720);
    }

    private void createStatTextField(Pane characterPane, String statName, double relocateX, double relocateY, ArrayList<TextField> storedArray) {
        Label statLabel = new Label();
        statLabel.setText(statName);
        statLabel.setFont(Font.font("Arial", 15));
        statLabel.relocate(relocateX+4,relocateY-17);

        TextField stat = new TextField();
        stat.setPrefColumnCount(2);
        stat.relocate(relocateX, relocateY);

        storedArray.add(stat);
        characterPane.getChildren().add(statLabel);
        characterPane.getChildren().add(stat);
    }

    private void characterCreated(Stage window, JSONObject character, TextField name, ArrayList<TextField> storedArray) throws IOException {
        JSONArray stats = character.getJSONArray("Stats");
        for (int i=0; i<storedArray.size(); i++) {
            JSONObject jsonStat = stats.getJSONObject(i);
            int newValue = Integer.parseInt(storedArray.get(i).getText());
            jsonStat.put("Value", newValue);
        }
        character.put("Name", name.getText());

        try {
            FileWriter characterFile = new FileWriter("src/Character.json");
            characterFile.write(character.toString());
            characterFile.close();
        } catch (IOException e) {
            System.out.println(e+" Could not find Character.json");
        }

        window.setScene(tableScreen);
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
