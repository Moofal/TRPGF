package org.TRPGF;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.Random;


/**
 * This class is used to create the GUI that the player is going to use.
 */
public class Screen {

    private final Stage stage;
    public Screen(Stage stage) {
        this.stage = stage;
    }

    private Scene startingScreen, tableScene, characterCreatorScreen, endingScreen;
    private boolean characterScreenIsThere = false;
    // This variable is set here so that both tableScreen and
    // characterScreen can the stat values shown on the tableScreen
    private final VBox characterStatInfo = new VBox();
    private Pane tableScreenLayout;
    private JSONObject currentDialog;
    private JSONArray currentOptions;


    /**
     * This is the starting screen.
     * Here you can show the title of the game and some intro text
     * @param gameTitle is the title/name of the game.
     * @param startingText this is the intro text that the player will see.
     * I advise you use a text file and a file reader to avoid long text strings in your code.
     */
    public void startingScreen (String gameTitle, String startingText) {
        stage.setTitle(gameTitle);
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
        nextScreenButton.relocate(630, 400);
        nextScreenButton.setOnAction(e -> {
            if (characterScreenIsThere) {
                stage.setScene(characterCreatorScreen);
            } else {
                stage.setScene(tableScene);
            }
        });


        layout.getChildren().addAll(title,text, nextScreenButton);
        startingScreen = new Scene(layout, 1280 , 720);

        stage.setScene(startingScreen);
        stage.show();
    }


    /*TODO: AttributeOptions, custom pane, dice checks for options*/
    /**
     * This creates the table screen where the actual game is played.
     * Here the dialog and character info is displayed and choices are made
     */
    public void tableScreen() {
        tableScreenLayout = new Pane();

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

        Text dialogText = new Text();
        dialogText.setWrappingWidth(625);
        dialogText.setFont(Font.font("Arial", 30));
        dialogHistory.setContent(dialogText);

        JSONObject tableScreenSettings = getTableScreenSettings();
        

        currentOptions = currentDialog.getJSONArray("dialogChoiceList");

        Label optionsLabel = new Label("Options:");
        optionsLabel.relocate(15,365);
        optionsLabel.setFont(Font.font("Arial", 20));

        VBox optionsVBox = new VBox();
        optionsVBox.relocate(30,390);
        setCurrentOptions(optionsVBox);

        TextField choice = new TextField();
        choice.setOnAction(e -> attemptOption(optionsVBox, choice, dialog, currentOptions, currentDialog, dialogText));
        choice.relocate(20,600);


        ImageView dialogImageView = new ImageView();
        dialogImageView.relocate(640,0);
        dialogImageView.maxHeight(640);
        dialogImageView.maxWidth(360);

        /* TODO: legg til funksjon som skjekker om next dialog og starting dialog har image link, hvis de har setImage
        *if (!currentDialog.getString("").isEmpty()) {
         *   Image dialogImage = new Image("");
          *  dialogImageView.setImage(dialogImage);
        }*/


        updateAllCharacterInfoOnTableScreen(tableScreenLayout);


        Menu menu = new Menu("Toggle");

        ToggleGroup paneToggle = new ToggleGroup();

        RadioMenuItem dialogHistoryMenuToggle = new RadioMenuItem("Dialog History");
        RadioMenuItem showImageMenuToggle = new RadioMenuItem("Image");

        dialogHistoryMenuToggle.setToggleGroup(paneToggle);
        showImageMenuToggle.setToggleGroup(paneToggle);

        dialogHistoryMenuToggle.setOnAction(e -> {
            toggleDialogHistory(tableScreenLayout, dialogHistory, dialogHistoryMenuToggle);
            toggleDialogImage(tableScreenLayout, showImageMenuToggle, dialogImageView);

        });
        showImageMenuToggle.setOnAction(e -> {
            toggleDialogImage(tableScreenLayout, showImageMenuToggle, dialogImageView);
            toggleDialogHistory(tableScreenLayout, dialogHistory, dialogHistoryMenuToggle);
        });

        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

        assert tableScreenSettings != null;
        String mapPath = tableScreenSettings.getString("Map");
        String mapName = tableScreenSettings.getString("Map Name");
        
        MenuItem mapBox = new MenuItem("Map");
        mapBox.setOnAction(e -> displayMapBox(mapName,mapPath));

        menu.getItems().addAll(dialogHistoryMenuToggle,showImageMenuToggle);

        if (!Objects.equals(mapPath,"")) {
            menu.getItems().addAll(separatorMenuItem,mapBox);
        }

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu);
        menuBar.relocate(641,361);

        tableScreenLayout.getChildren().addAll(horizontalLine, verticalLine, dialogScrollPane, choice,
                optionsVBox, characterStatInfo, menuBar,optionsLabel);
        tableScene = new Scene(tableScreenLayout, 1280, 720);
    }

    // Reading from file
    private JSONObject getTableScreenSettings() {
        try {
            Path tableScreenPath = Path.of("src/TableScreenSettings.json");
            String input = Files.readString(tableScreenPath);
            return new JSONObject(input);
        } catch (IOException e) {
            System.out.println(e+"\n Could not find TableScreenSettings.json file");
        }
        return null;
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
    private JSONObject getDialogHistory() {
        try {
            Path file = Path.of("src/DialogHistory.json");
            String input = Files.readString(file);

            return new JSONObject(input);
        } catch (IOException e) {
            System.out.println(e + "src/DialogHistory.json");
        }
        return null;
    }

    // Writing to file
    private void writeToDialogHistory(JSONObject currentDialog) {
        JSONObject dialogHistoryObject = new JSONObject();
        JSONArray arrayOfDialog = new JSONArray();
        arrayOfDialog.put(currentDialog);
        dialogHistoryObject.put("Dialog History", arrayOfDialog);
        try {
            FileWriter charCreationSettingsFile = new FileWriter("src/DialogHistory.json");
            charCreationSettingsFile.write(dialogHistoryObject.toString());
            charCreationSettingsFile.close();
        } catch (IOException e) {
            System.out.println("Could not find DialogHistory.json");
        }
    }
    private void updateCharacterJson(JSONObject chosenOptionObject) {
        String statIncreased = chosenOptionObject.getString("STAT");
        int statIncreasedAmount = chosenOptionObject.getInt("REWARD-VAL");

        JSONObject character = getCharacterInfo();
        assert character != null;
        JSONArray characterStats = character.getJSONArray("Stats");
        JSONObject requiredStat;
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
            updateCharacterStatsOnTableScreen(characterStatInfo);
        } catch (IOException e) {
            System.out.println(e+" Could not find Character.json");
        }
    }
    private void updateDialogHistory(JSONObject currentDialog, Text dialogText, int index) {
        StringBuilder dialog;
        JSONObject newDialog;
        JSONArray options;
        if (Objects.equals(dialogText.getText(), "")) {
            dialog = new StringBuilder(currentDialog.getString("CONTENT"));
            options = currentDialog.getJSONArray("dialogChoiceList");
            newDialog = currentDialog;
        } else {
            JSONObject oldDialogHistory = getDialogHistory();
            assert oldDialogHistory != null;
            JSONArray oldDialogArray = oldDialogHistory.getJSONArray("Dialog History");
            oldDialogArray.put(currentDialog);
            newDialog = oldDialogHistory;
            options = currentDialog.getJSONArray("dialogChoiceList");
            dialog = new StringBuilder(dialogText.getText());
            dialog.append(currentDialog.getString("CONTENT"));
        }
        String option;
        for (int i=0; i<options.length(); i++) {
            option = options.getJSONObject(i).getString("CONTENT");
            if (i == index) {
                option = option.toUpperCase();
            }
            dialog.append("\n").append(i+1).append(" ").append(option);
        }
        dialog.append(" -------------------------------------------------------------- ");
        dialogText.setText(dialog.toString());
        writeToDialogHistory(newDialog);
    }

    // Character info on table screen
    private void updateAllCharacterInfoOnTableScreen(Pane layout) {
        updateCharacterNameOnTableScreen(layout);

        updateCharacterStatsOnTableScreen(characterStatInfo);
    }
    private void updateCharacterNameOnTableScreen(Pane layout) {
        String characterName = Objects.requireNonNull(getCharacterInfo()).getString("Name");
        Text characterNameOnBord = new Text(characterName);
        characterNameOnBord.setFont(Font.font("Arial",20));
        characterNameOnBord.relocate(800,450);
        layout.getChildren().add(characterNameOnBord);
    }
    private void updateCharacterStatsOnTableScreen(VBox characterStatInfo) {
        characterStatInfo.getChildren().clear();
        JSONObject character = getCharacterInfo();
        assert character != null;
        JSONArray stats = character.getJSONArray("Stats");

        for (int i=0; i<stats.length(); i++) {
            JSONObject stat = stats.getJSONObject(i);
            String info = stat.getString("Name")+" "+stat.getInt("Value");
            Text statText = new Text();
            statText.setText(info);
            characterStatInfo.getChildren().add(statText);
        }
        characterStatInfo.relocate(700,500);
    }

    // Menu toggles
    private void toggleDialogHistory(Pane tableScreenLayout, ScrollPane dialogHistory, RadioMenuItem dialogHistoryMenuToggle) {
        if (dialogHistoryMenuToggle.isSelected()) {
            try {
                tableScreenLayout.getChildren().add(dialogHistory);
            } catch (IllegalArgumentException ignored) {}
        } else {
            tableScreenLayout.getChildren().remove(dialogHistory);
        }
    }
    private void toggleDialogImage(Pane tableScreenLayout, RadioMenuItem showMapMenuToggle, ImageView imageView) {
        if (showMapMenuToggle.isSelected()) {
            try {
                tableScreenLayout.getChildren().add(imageView);
            } catch (IllegalArgumentException ignored) {}
        } else {
            tableScreenLayout.getChildren().remove(imageView);
        }
    }

    // Option functions
    private void attemptOption(VBox optionsVBox, TextField choice, Text dialog, JSONArray currentOptionsJSON, JSONObject currentDialog, Text dialogHistory)  {
        String chosenOption = choice.getText().replaceAll("[^1-3]", "");
        choice.setText("");

        int index;
        try {
            index = Integer.parseInt(chosenOption);
        } catch (NumberFormatException e) {
            System.out.println(e);
            return;
        }
        index -= 1;
        JSONObject chosenOptionObject = currentOptionsJSON.getJSONObject(index);
        String optionType = chosenOptionObject.getString("TYPE");

        int nextDialogID;
        switch (optionType) {
            case "Normal Choice":
                nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                optionChosen(optionsVBox, dialog, nextDialogID);
                break;
            case "Previous Choice":
                int previousOptionId = chosenOptionObject.getInt("PREV-CHOICE");
                //checkIfOptionChosenPreviously(previousOptionId);
                break;
            case "Choice with Requirement":
                checkRequirementAndSetScreen(optionsVBox, dialog, chosenOptionObject);
                break;
            case "Choice with a reward":
                nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                updateCharacterJson(chosenOptionObject);
                optionChosen(optionsVBox, dialog, nextDialogID);
                break;
        }
        updateDialogHistory(currentDialog, dialogHistory, index);
    }
    private void checkRequirementAndSetScreen(VBox optionsVBox, Text dialog, JSONObject chosenOptionObject) {
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
            optionChosen(optionsVBox, dialog, nextDialogID);
        } else {
            nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
            optionChosen(optionsVBox, dialog, nextDialogID);
        }
    }
    private void optionChosen(VBox optionsVBox, Text dialog, int nextDialogID) {
        // Setting the dialog text box to be the new dialog text
        JSONObject nextDialogJSON = getDialog(nextDialogID);

        assert nextDialogJSON != null;
        String dialogString = nextDialogJSON.getString("CONTENT");
        dialog.setText(dialogString);

        // Setting the dialog options to be the options for the new dialog
        this.currentDialog = getDialog(nextDialogID);
        assert this.currentDialog != null;
        currentOptions = this.currentDialog.getJSONArray("dialogChoiceList");

        // This removes the old dialog options text
        optionsVBox.getChildren().clear();
        setCurrentOptions(optionsVBox);
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
        options.setText(optionNr+ ". " + text);
        options.relocate(relocateX,relocateY);
        options.setFont(Font.font(20));

        return options;
    }

    // Map box
    /**
     * Adds a map to your game for the player.
     * The image must be a BMP, GIF, JPEG or PNG.
     * Max height: 640px, max width: 360px.
     * @param imageFilePath The file path for the image.
     */
    public void addTableScreenMap(String mapName, String imageFilePath){
        JSONObject options = new JSONObject();
        options.put("Map", imageFilePath);
        options.put("Map Name", mapName);
        try {
            FileWriter charCreationSettingsFile = new FileWriter("src/TableScreenSettings.json");
            charCreationSettingsFile.write(options.toString());
            charCreationSettingsFile.close();
        } catch (IOException e) {
            System.out.println("Could not find CharCreatSettings.json");
        }
    }
    private void displayMapBox(String mapName, String mapPath) {
        Stage window = new Stage();

        Image map;
        try {
            map = new Image(mapPath);
        } catch (IllegalArgumentException e) {
            System.out.println("Could not find image form path in addTableScreenMap() function.");
            return;
        }

        ImageView mapView = new ImageView();
        mapView.setImage(map);
        mapView.maxHeight(640);
        mapView.maxWidth(360);

        Pane pane = new Pane();
        pane.getChildren().add(mapView);

        Scene scene = new Scene(pane);
        scene.fillProperty();

        window.setTitle(mapName);
        window.setScene(scene);
        window.show();
    }


    /**
     * This creates the character creation screen.
     * is what comes after the starting screen
     * @param displayedText text that is shows on this screen.
     */
    public void characterScreen(String displayedText) {
        characterScreenIsThere = true;
        Pane characterCreationPane = new Pane();

        Label displayedTextLabel = new Label(displayedText);
        displayedTextLabel.setFont(Font.font("Arial",20));
        displayedTextLabel.setMaxSize(300, 200);
        displayedTextLabel.setWrapText(true);
        displayedTextLabel.relocate(540, 50);

        Text errorText = new Text();
        errorText.relocate(550, 340);
        errorText.setFont(Font.font("Arial", 20));

        JSONObject character = getCharacterInfo();

        Path settingsFile = Path.of("src/CharCreatSettings.json");
        String settingsInput = null;
        try {
            settingsInput = Files.readString(settingsFile);
        } catch (IOException e) {
            errorText.setText("Could not find CharCreatSettings.json");
        }
        assert settingsInput != null;
        JSONObject settings = new JSONObject(settingsInput);

        JSONArray arrayOfStats = settings.getJSONArray("Stats");

        double x = 480,y = 200;
        ArrayList<TextField> storedArray = new ArrayList<>();
        for (int i = 0; i < arrayOfStats.length(); i++) {
            x+=60;
            JSONObject statObject = arrayOfStats.getJSONObject(i);
            createStatGenerationMethod(characterCreationPane, statObject, x, y, storedArray);
        }

        Label characterNameInputLabel = new Label("Character Name");
        characterNameInputLabel.relocate(550,275);
        characterNameInputLabel.setFont(Font.font("Arial",20));

        TextField characterNameInput = new TextField();
        characterNameInput.relocate(550,300);

        if (settings.getBoolean("Name Option")) {
            characterCreationPane.getChildren().addAll(characterNameInput,characterNameInputLabel);
        }

        Button doneButton = new Button("Done");
        doneButton.relocate(630, 400);

        doneButton.setOnAction(e -> {
            errorText.setText("");
            if (characterNameInput.getText().trim().isEmpty()) {
                errorText.setText("Please make a name");
                return;
            }
            try {
                assert character != null;
                characterCreated(stage, character, characterNameInput, storedArray, errorText);
                updateAllCharacterInfoOnTableScreen(tableScreenLayout);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        characterCreationPane.getChildren().addAll( displayedTextLabel, doneButton, errorText);

        characterCreatorScreen = new Scene(characterCreationPane, 1280, 720);
    }

    private void createStatGenerationMethod(Pane characterPane, JSONObject statObject, double relocateX, double relocateY, ArrayList<TextField> storedArray) {
        String statName = statObject.getString("Name");

        Label statLabel = new Label();
        statLabel.setText(statName);
        statLabel.setFont(Font.font("Arial", 15));
        statLabel.relocate(relocateX+4,relocateY-17);

        TextField stat = new TextField();

        Label valueLabel = new Label();
        valueLabel.setFont(Font.font("Arial", 15));
        valueLabel.relocate(relocateX+4,relocateY);

        switch (statObject.getString("Generation Type")) {
            case "Dice":
                valueLabel.setText("0");

                String dice = statObject.getString("Dice");
                Button generateStatValue = new Button("Roll "+dice);
                generateStatValue.relocate(relocateX,relocateY+40);

                generateStatValue.setOnAction(e -> {
                    String[] diceNumbers = dice.split("d",2);
                    int numOfDice = Integer.parseInt(diceNumbers[0]);
                    int valOfDice = Integer.parseInt(diceNumbers[1]);
                    int value = 0;
                    Random random = new Random();
                    for (int i=0; i<numOfDice; i++) {
                        value += random.nextInt(valOfDice)+1;
                    }
                    valueLabel.setText(String.valueOf(value));
                    stat.setText(String.valueOf(value));
                    characterPane.getChildren().remove(generateStatValue);
                });

                characterPane.getChildren().addAll(statLabel, valueLabel, generateStatValue);
                break;
            case "Manual":
                stat.setPrefColumnCount(2);
                stat.relocate(relocateX, relocateY);
                characterPane.getChildren().addAll(statLabel, stat);
                break;
            case "Set":
                String value = String.valueOf(statObject.getInt("Value"));
                valueLabel.setText(value);
                stat.setText(value);
                characterPane.getChildren().addAll(statLabel, valueLabel);
                break;
        }
        storedArray.add(stat);
    }

    private void characterCreated(Stage window, JSONObject character, TextField name,
                                  ArrayList<TextField> storedArray, Text errorText) throws IOException {
        JSONArray stats = character.getJSONArray("Stats");
        for (int i=0; i<stats.length(); i++) {
            JSONObject jsonStat = stats.getJSONObject(i);
            // Checks if the input for the stat is empty
            if (!storedArray.get(i).getText().trim().isEmpty()) {
                try {
                    int newValue = Integer.parseInt(storedArray.get(i).getText());
                    // Checks if the value of a given stat is wrong
                    if (newValue < jsonStat.getInt("Min Value") || newValue >jsonStat.getInt("Max Value")) {
                        String statName = jsonStat.getString("Name");
                        errorText.setText("The value of "+statName+" is ether too high or too low");
                        return;
                    }
                    jsonStat.put("Value", newValue);
                } catch (NumberFormatException e) {
                    errorText.setText("Input must be a integer");
                    return;
                }
            } else {
                errorText.setText("Please fill in all stats");
                return;
            }
        }
        character.put("Name", name.getText());

        try {
            FileWriter characterFile = new FileWriter("src/Character.json");
            characterFile.write(character.toString());
            characterFile.close();
        } catch (IOException e) {
            errorText.setText(e+" Could not find the Character.json file.");
        }

        window.setScene(tableScene);
    }


    /**
     * For creating ending screens, you can have multiple of these.
     * @param endingScreenId is the id of this endingScreen..
     * @param sceneText the text that is shown on this ending screen.
     * @param startOver is a boolean value that lets you dice if you want there to be a staring over button
     *                  that leads to the starting screen.
     * @param exit is a boolean value that lets you dice if you want there to be an exit button
     *             that closes the application.
     */
    public void endingScreen(int endingScreenId, String sceneText, boolean startOver, boolean exit) {
        Pane endingPane = new Pane();

        Text endingText = new Text(sceneText);
        endingText.setFont(Font.font("Arial", 20));
        endingText.relocate(500,100);
        endingPane.getChildren().add(endingText);

        Button startOverButton = new Button("Start Over");
        startOverButton.relocate(300, 200);
        startOverButton.setOnAction(e -> stage.setScene(startingScreen));

        if (startOver) {
            endingPane.getChildren().add(startOverButton);
        }

        Button exitButton = new Button("Exit");
        exitButton.relocate(600, 200);
        exitButton.setOnAction(e -> stage.close());

        if (exit) {
            endingPane.getChildren().add(exitButton);
        }

        endingScreen = new Scene(endingPane, 1280, 720);
    }
}
