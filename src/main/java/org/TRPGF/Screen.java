package org.TRPGF;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
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
    // This variable is set here so that both tableScreen an
    // characterScreen can the stat values shown on the tableScreen
    private final VBox characterStatInfo = new VBox();
    private Pane tableScreenLayout;
    private JSONObject currentDialog;
    private JSONArray currentOptions;
    private final JSONArray arrayOfEndingScreen = new JSONArray();

    private Label optionsText;
    private Label nameLabel;
    private Label statText;
    private Font optionsTextFont = Font.font("Arial",20), statTextFont;
    private Color optionsTextColor, statTextColor;


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


    /*TODO: custom pane, dice checks for options*/
    /**
     * This creates the table screen where the actual game is played.
     * Here the dialog and character info is displayed and choices are made
     */
    public void tableScreen() {
        tableScreenLayout = new Pane();

        // This is how to change the background color of the table screen.


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

        Label dialog = new Label(dialogString);
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

        Label dialogText = new Label();
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

        MenuItem settings = new MenuItem("Settings");
        settings.setOnAction(e -> displaySettings(dialogScrollPane, dialogHistory, dialog, dialogText, optionsLabel));

        menu.getItems().addAll(dialogHistoryMenuToggle,showImageMenuToggle);

        if (!Objects.equals(mapPath,"")) {
            menu.getItems().addAll(separatorMenuItem,mapBox, settings);
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
        try {
            FileWriter charCreationSettingsFile = new FileWriter("src/DialogHistory.json");
            charCreationSettingsFile.write(currentDialog.toString());
            charCreationSettingsFile.close();
        } catch (IOException e) {
            System.out.println("Could not find DialogHistory.json");
        }
    }
    private void writeToCharacterJson(JSONObject character) {
        try {
            FileWriter characterFile = new FileWriter("src/Character.json");
            characterFile.write(character.toString());
            characterFile.close();
            updateCharacterStatsOnTableScreen(characterStatInfo);
        } catch (IOException e) {
            System.out.println(e+" Could not find Character.json");
        }
    }
    private void updateDialogHistory(JSONObject currentDialog, Label dialogText, int index) {
        StringBuilder dialog;
        JSONArray options = currentDialog.getJSONArray("dialogChoiceList");

        JSONObject dialogHistoryObject;

        for (int j=0; j<options.length(); j++) {
            if (options.getJSONObject(j).getInt("ID") == index+1) {
                options.getJSONObject(j).put("CHOSEN", true);
            }
        }

        if (Objects.equals(dialogText.getText(), "")) {
            dialog = new StringBuilder(currentDialog.getString("CONTENT"));

            dialogHistoryObject = new JSONObject();
            JSONArray arrayOfDialog = new JSONArray();
            arrayOfDialog.put(currentDialog);
            dialogHistoryObject.put("Dialog History", arrayOfDialog);


        } else {
            dialog = new StringBuilder(dialogText.getText());
            dialog.append(currentDialog.getString("CONTENT"));

            dialogHistoryObject = getDialogHistory();
            assert dialogHistoryObject != null;
            JSONArray arrayOfDialog = dialogHistoryObject.getJSONArray("Dialog History");
            arrayOfDialog.put(currentDialog);

        }

        writeToDialogHistory(dialogHistoryObject);

        String option;
        for (int i=0; i<options.length(); i++) {
            option = options.getJSONObject(i).getString("CONTENT");
            if (i == index) {
                option = option.toUpperCase();
            }
            dialog.append("\n").append(i+1).append(" ").append(option);
        }
        dialog.append("\n -------------------------------------------------------------- \n");
        dialogText.setText(dialog.toString());
    }

    // Character info on table screen
    private void updateAllCharacterInfoOnTableScreen(Pane layout) {
        updateCharacterNameOnTableScreen(layout);

        updateCharacterStatsOnTableScreen(characterStatInfo);
    }
    private void updateCharacterNameOnTableScreen(Pane layout) {
        layout.getChildren().remove(nameLabel);
        String characterName = Objects.requireNonNull(getCharacterInfo()).getString("Name");
        nameLabel = new Label("Name: " +characterName);
        nameLabel.setFont(Font.font("Arial",20));
        nameLabel.relocate(740,450);
        layout.getChildren().add(nameLabel);
    }
    private void updateCharacterStatsOnTableScreen(VBox characterStatInfo) {
        characterStatInfo.getChildren().clear();
        JSONObject character = getCharacterInfo();
        assert character != null;
        JSONArray stats = character.getJSONArray("Stats");

        statText = new Label();
        String info;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stats:\n");
        for (int i=0; i<stats.length(); i++) {
            JSONObject stat = stats.getJSONObject(i);
            info = stat.getString("Name")+" "+stat.getInt("Value")+"\n";
            stringBuilder.append(info);

        }
        statText.setText(stringBuilder.toString());
        if (statTextFont!=null) {
            statText.setFont(statTextFont);
        }
        if (statTextColor!=null) {
            statText.setTextFill(statTextColor);
        }
        characterStatInfo.getChildren().add(statText);
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
    private void attemptOption(VBox optionsVBox, TextField choice, Label dialog, JSONArray currentOptionsJSON, JSONObject currentDialog, Label dialogHistory)  {
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
        JSONObject chosenOptionObject;
        try { chosenOptionObject= currentOptionsJSON.getJSONObject(index);} catch (JSONException e) {
            return;
        }

        String optionType = chosenOptionObject.getString("TYPE");
        /*
          Type Cheat Sheet:
          0000 Normal Option
          0001 Normal Ending
          1000 Previous Choice
          1001 Previous Ending
          1100 Previous Choice + Requirement
          1101 Previous + Requirement Ending
          1010 Previous Choice + Reward
          1011 Previous + Reward Ending
          1110 Previous + Requirement + Reward Choice
          1111 Previous + Requirement + Reward Ending
          0100 Requirement Choice
          0101 Requirement Ending
          0110 Requirement Choice + Reward
          0111 Requirement Choice + Reward Ending
          0010 Reward Choice
         */
        int nextDialogID = 0;
        int endingScreenID;
        int previousOptionId = chosenOptionObject.getInt("PREV-CHOICE");
        int previousBoxId = chosenOptionObject.getInt("PREV-CHOICE-BOX");
        boolean meetsRequirements, hasChosenOption, ending = false, endOnSuccess;
        switch (optionType) {
            case "0000":
                nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                break;
            case "0001":
                endingScreenID = chosenOptionObject.getInt("ENDING-SCREEN-ID");
                setEndingScreen(endingScreenID);
                ending = true;
                break;
            case "1000":
                if (checkIfOptionChosenPreviously(previousOptionId, previousBoxId)) {
                    nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                } else {
                    nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
                }
                break;
            case "1001":
                endOnSuccess = chosenOptionObject.getBoolean("END-ON-SUCCESS");
                if (endOnSuccess) {
                    if (checkIfOptionChosenPreviously(previousOptionId, previousBoxId)) {
                        endingScreenID = chosenOptionObject.getInt("ENDING-SCREEN-ID");
                        setEndingScreen(endingScreenID);
                        ending = true;
                    } else {
                        nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
                    }
                } else {
                    if (checkIfOptionChosenPreviously(previousOptionId, previousBoxId)) {
                        nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                    } else {
                        endingScreenID = chosenOptionObject.getInt("ENDING-SCREEN-ID");
                        setEndingScreen(endingScreenID);
                        ending = true;
                    }
                }
                break;
            case "1100":
                meetsRequirements = checkRequirement(chosenOptionObject);
                hasChosenOption = checkIfOptionChosenPreviously(previousOptionId, previousBoxId);
                if (meetsRequirements && hasChosenOption) {
                    nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                } else {
                    nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
                }
                break;
            case "1101":
                endOnSuccess = chosenOptionObject.getBoolean("END-ON-SUCCESS");
                meetsRequirements = checkRequirement(chosenOptionObject);
                hasChosenOption = checkIfOptionChosenPreviously(previousOptionId, previousBoxId);
                if (endOnSuccess) {
                    if (meetsRequirements && hasChosenOption) {
                        endingScreenID = chosenOptionObject.getInt("ENDING-SCREEN-ID");
                        setEndingScreen(endingScreenID);
                        ending = true;
                    } else {
                        nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
                    }
                } else {
                    if (meetsRequirements && hasChosenOption) {
                        nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                    } else {
                        endingScreenID = chosenOptionObject.getInt("ENDING-SCREEN-ID");
                        setEndingScreen(endingScreenID);
                        ending = true;
                    }
                }
                break;
            case "1010":
                hasChosenOption = checkIfOptionChosenPreviously(previousOptionId, previousBoxId);
                if (hasChosenOption) {
                    nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                    updateCharacterJsonWithReward(chosenOptionObject);
                } else {
                    nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
                }
                break;
            case "1011":
                hasChosenOption = checkIfOptionChosenPreviously(previousOptionId, previousBoxId);
                endOnSuccess = chosenOptionObject.getBoolean("END-ON-SUCCESS");
                if (endOnSuccess) {
                    if (hasChosenOption) {
                        endingScreenID = chosenOptionObject.getInt("ENDING-SCREEN-ID");
                        setEndingScreen(endingScreenID);
                        ending = true;
                    } else {
                        nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
                    }
                } else {
                    if (hasChosenOption) {
                        nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                        updateCharacterJsonWithReward(chosenOptionObject);
                    } else {
                        endingScreenID = chosenOptionObject.getInt("ENDING-SCREEN-ID");
                        setEndingScreen(endingScreenID);
                        ending = true;
                    }
                }
                break;
            case "1110":
                meetsRequirements = checkRequirement(chosenOptionObject);
                hasChosenOption = checkIfOptionChosenPreviously(previousOptionId, previousBoxId);
                if (meetsRequirements && hasChosenOption) {
                    nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                    updateCharacterJsonWithReward(chosenOptionObject);
                } else {
                    nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
                }
                break;
            case "1111":
                meetsRequirements = checkRequirement(chosenOptionObject);
                hasChosenOption = checkIfOptionChosenPreviously(previousOptionId, previousBoxId);
                endOnSuccess = chosenOptionObject.getBoolean("END-ON-SUCCESS");
                if (endOnSuccess) {
                    if (meetsRequirements && hasChosenOption) {
                        endingScreenID = chosenOptionObject.getInt("ENDING-SCREEN-ID");
                        setEndingScreen(endingScreenID);
                        ending = true;
                    } else {
                        nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
                    }
                } else {
                    if (meetsRequirements && hasChosenOption) {
                        nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                        updateCharacterJsonWithReward(chosenOptionObject);
                    } else {
                        endingScreenID = chosenOptionObject.getInt("ENDING-SCREEN-ID");
                        setEndingScreen(endingScreenID);
                        ending = true;
                    }
                }
                break;
            case "0100":
                if (checkRequirement(chosenOptionObject)) {
                    nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                } else {
                    nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
                }
                break;
            case "0101":
                endOnSuccess = chosenOptionObject.getBoolean("END-ON-SUCCESS");
                if (endOnSuccess) {
                    if (checkRequirement(chosenOptionObject)) {
                        endingScreenID = chosenOptionObject.getInt("ENDING-SCREEN-ID");
                        setEndingScreen(endingScreenID);
                        ending = true;
                    } else {
                        nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
                    }
                } else {
                    if (checkRequirement(chosenOptionObject)) {
                        nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                    } else {
                        endingScreenID = chosenOptionObject.getInt("ENDING-SCREEN-ID");
                        setEndingScreen(endingScreenID);
                        ending = true;
                    }
                }
                break;
            case "0110":
                if (checkRequirement(chosenOptionObject)) {
                    nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                    updateCharacterJsonWithReward(chosenOptionObject);
                } else {
                    nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
                }
                break;
            case "0111":
                endOnSuccess = chosenOptionObject.getBoolean("END-ON-SUCCESS");
                if (endOnSuccess) {
                    if (checkRequirement(chosenOptionObject)) {
                        endingScreenID = chosenOptionObject.getInt("ENDING-SCREEN-ID");
                        setEndingScreen(endingScreenID);
                        ending = true;
                    } else {
                        nextDialogID = chosenOptionObject.getInt("FAIL-SCENE");
                    }
                } else {
                    if (checkRequirement(chosenOptionObject)) {
                        nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                        updateCharacterJsonWithReward(chosenOptionObject);
                    } else {
                        endingScreenID = chosenOptionObject.getInt("ENDING-SCREEN-ID");
                        setEndingScreen(endingScreenID);
                        ending = true;
                    }
                }
                break;
            case "0010":
                nextDialogID = chosenOptionObject.getInt("SUCCESS-SCENE");
                updateCharacterJsonWithReward(chosenOptionObject);
                break;
        }
        if (!ending) {
            optionChosen(optionsVBox, dialog, nextDialogID);
            updateDialogHistory(currentDialog, dialogHistory, index);
        }

    }
    private void updateCharacterJsonWithReward(JSONObject chosenOptionObject) {
        String statIncreased = chosenOptionObject.getString("REWARD-STAT");
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
        writeToCharacterJson(character);
    }
    private boolean checkIfOptionChosenPreviously(int previousOptionId, int previousBoxId) {
        JSONObject dialogHistory = getDialogHistory();
        assert dialogHistory != null;
        JSONArray arrayOfDialog = dialogHistory.getJSONArray("Dialog History");
        JSONObject dialog;
        JSONArray options;

        int boxId;
        int optionId;
        for (int i=0; i<arrayOfDialog.length(); i++) {
            dialog = arrayOfDialog.getJSONObject(i);
            options = dialog.getJSONArray("dialogChoiceList");
            for (int l=0; l<options.length(); l++) {
                boxId = options.getJSONObject(i).getInt("BOXID");
                if (boxId == previousBoxId) {
                        optionId = options.getJSONObject(l).getInt("ID");
                        // This catch exists because the CHOOSEN key is only added  when an option is chosen
                        // if the option we are looking at does not have the CHOOSEN key and triggers the exception
                        // we know that it has not been chosen before
                        try {
                            boolean hasBenChosen = options.getJSONObject(l).getBoolean("CHOSEN");
                            if (optionId == previousOptionId && hasBenChosen) {
                                return true;
                            }
                        } catch (JSONException e) {
                            return false;
                        }
                }
            }
        }

        return false;
    }
    private boolean checkRequirement(JSONObject chosenOptionObject) {
        boolean hasRequiredStatValue;
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
        // sets value true is stat >= required val
        hasRequiredStatValue = requiredStat.getInt("Value") >= requiredStatValue;
        return hasRequiredStatValue;
    }
    private void optionChosen(VBox optionsVBox, Label dialog, int nextDialogID) {
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
    private void changeAttribute(String attributeName, int value) {
        JSONObject character = getCharacterInfo();
        assert character != null;
        JSONArray attributesArray = character.getJSONArray("Attributes");
        JSONObject attribute;
        for (int i=0; i<attributesArray.length(); i++) {
            attribute = attributesArray.getJSONObject(i);
            boolean sameName = Objects.equals(attribute.getString("Name"),attributeName);
            if (sameName) {
                int newValue = attribute.getInt("Value")+value;
                attribute.put("Value", newValue);
            }
        }
        writeToCharacterJson(character);
    }
    private boolean checkAttribute(String attributeName, int value) {
        JSONObject character = getCharacterInfo();
        assert character != null;
        JSONArray attributesArray = character.getJSONArray("Attributes");
        JSONObject attribute;
        for (int i=0; i<attributesArray.length(); i++) {
            attribute = attributesArray.getJSONObject(i);
            boolean sameName = Objects.equals(attribute.getString("Name"),attributeName);
            if (sameName) {
                if (attribute.getInt("Value") >= value) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setEndingScreen(int endingScreenID) {
        JSONArray endingScreens = endingScreenReader();
        JSONObject endingScreen;
        int checkID;
        boolean exit = false;
        boolean startOver = false;
        String sceneText = null;
        for (int i = 0; i < Objects.requireNonNull(endingScreens).length(); i++) {
            endingScreen = endingScreens.getJSONObject(i);
            checkID = endingScreen.getInt("ID");
            if (Objects.equals(checkID,endingScreenID)) {
                exit = endingScreen.getBoolean("exit");
                startOver = endingScreen.getBoolean("startOver");
                sceneText = endingScreen.getString("sceneText");
            }
        }
        setEndingScreen(sceneText,startOver,exit);
        changeToEndingScreen();
    }
    private void changeToEndingScreen() {
        stage.setScene(endingScreen);
    }

    private void setCurrentOptions(VBox vBox) {
        double optionY = 390;
        for (int i = 0; i < currentOptions.length(); i++) {
            optionY += 20;
            String text = currentOptions.getJSONObject(i).getString("CONTENT");
            vBox.getChildren().add(addOption(text, i+1, 30, optionY));
        }
    }
    private Label addOption(String text, int optionNr, double relocateX, double relocateY) {
        optionsText = new Label();
        optionsText.setId("option");
        optionsText.setText(optionNr+ ". " + text);
        optionsText.relocate(relocateX,relocateY);
        optionsText.setFont(optionsTextFont);

        if (optionsTextColor!=null) {
            optionsText.setTextFill(optionsTextColor);
        }

        return optionsText;
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

    // Setting Menu
    /**
     * Adds a settings menu for the screen
     */
    private void displaySettings(ScrollPane dialogScrollPane, ScrollPane dialogHistory, Label dialog, Label dialogText, Label optionsLabel) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane displaySettingsPane = new Pane();

        ComboBox<String> backgroundColorComboBox = new ComboBox<>();
        backgroundColorComboBox.getItems().addAll(
                "Red","Black","Green"
        );
        backgroundColorComboBox.relocate(1,75);
        backgroundColorComboBox.setEditable(true);

        Label backgroundColorComboBoxLabel = new Label("Select the color you want the background to be." +
                "\nYou can write the hex number or choose from a list." +
                "\nExample: #2a742a");
        backgroundColorComboBoxLabel.setFont(Font.font("Arial",20));
        backgroundColorComboBoxLabel.relocate(1,1);


        Label dialogLabel = new Label("Dialog style");
        dialogLabel.relocate(1,124);
        dialog.setFont(Font.font("Arial", 15));

        ComboBox<String> dialogFontBox = new ComboBox<>();
        dialogFontBox.getItems().addAll("Verdana","Helvetica","Times New Roman","Comic Sans MS","Impact"
        ,"Lucida Sans Unicode", "Arial");
        dialogFontBox.setEditable(true);
        dialogFontBox.relocate(1,140);

        TextField dialogFontSize = new TextField();
        dialogFontSize.setText("30");
        dialogFontSize.relocate(200,140);


        Label optionsLabel1 = new Label("Options style");
        optionsLabel1.setFont(Font.font("Arial",15));
        optionsLabel1.relocate(1,175);

        ComboBox<String> optionsFontBox = new ComboBox<>();
        optionsFontBox.getItems().addAll("Verdana","Helvetica","Times New Roman","Comic Sans MS","Impact"
                ,"Lucida Sans Unicode", "Arial");
        optionsFontBox.setEditable(true);
        optionsFontBox.relocate(1,193);

        TextField optionsFontSize = new TextField();
        optionsFontSize.setText("20");
        optionsFontSize.relocate(200,193);


        Label statsLabel = new Label("Stats style");
        statsLabel.setFont(Font.font("Arial",15));
        statsLabel.relocate(1,226);

        ComboBox<String> statsLabelFontBox = new ComboBox<>();
        statsLabelFontBox.getItems().addAll("Verdana","Helvetica","Times New Roman","Comic Sans MS","Impact"
                ,"Lucida Sans Unicode", "Arial");
        statsLabelFontBox.setEditable(true);
        statsLabelFontBox.relocate(1,243);

        TextField statsLabelFontSize = new TextField();
        statsLabelFontSize.setText("10");
        statsLabelFontSize.relocate(200,243);


        Label dialogHistoryLabel = new Label("Dialog history style");
        dialogHistoryLabel.setFont(Font.font("Arial",15));
        dialogHistoryLabel.relocate(1,275);

        ComboBox<String> dialogHistoryLabelFontBox = new ComboBox<>();
        dialogHistoryLabelFontBox.getItems().addAll("Verdana","Helvetica","Times New Roman","Comic Sans MS","Impact"
                ,"Lucida Sans Unicode", "Arial");
        dialogHistoryLabelFontBox.setEditable(true);
        dialogHistoryLabelFontBox.relocate(1,292);

        TextField dialogHistoryLabelFontSize = new TextField();
        dialogHistoryLabelFontSize.setText("30");
        dialogHistoryLabelFontSize.relocate(200,292);


        Label characterNameLabel = new Label("Character name style");
        characterNameLabel.setFont(Font.font("Arial",15));
        characterNameLabel.relocate(1,327);

        ComboBox<String> characterNameLabelFontBox = new ComboBox<>();
        characterNameLabelFontBox.getItems().addAll("Verdana","Helvetica","Times New Roman","Comic Sans MS","Impact"
                ,"Lucida Sans Unicode", "Arial");
        characterNameLabelFontBox.setEditable(true);
        characterNameLabelFontBox.relocate(1,347);

        TextField characterNameLabelFontSize = new TextField();
        characterNameLabelFontSize.setText("20");
        characterNameLabelFontSize.relocate(200,347);


        Label textStyle = new Label("Text color");
        textStyle.setFont(Font.font("Arial",15));
        textStyle.relocate(500,1);

        ComboBox<String> textColorComboBox = new ComboBox<>();
        textColorComboBox.getItems().addAll(
                "Red","Black","Green"
        );
        textColorComboBox.relocate(500,75);
        textColorComboBox.setEditable(true);


        Button settingsChosenButton = new Button("Done");
        settingsChosenButton.relocate(640,360);

        settingsChosenButton.setOnAction(e-> {
            // Text fonts
            double size = Double.parseDouble(dialogFontSize.getText());
            dialog.setFont(Font.font(dialogFontBox.getValue(),size));

            size = Double.parseDouble(dialogHistoryLabelFontSize.getText());
            dialogText.setFont(Font.font(dialogHistoryLabelFontBox.getValue(),size));

            size = Double.parseDouble(optionsFontSize.getText());
            optionsTextFont = Font.font(optionsFontBox.getValue(),size);
            optionsText.setFont(optionsTextFont);
            optionsLabel.setFont(optionsTextFont);


            size = Double.parseDouble(statsLabelFontSize.getText());
            statTextFont = Font.font(statsLabelFontBox.getValue(),size);
            statText.setFont(statTextFont);

            size = Double.parseDouble(characterNameLabelFontSize.getText());
            nameLabel.setFont(Font.font(characterNameLabelFontBox.getValue(),size));

            // Text color
            String textColorString = textColorComboBox.getValue().trim();
            Color textColor = Color.web(textColorString);
            if (!Objects.equals(textColor, null)) {
                optionsTextColor = textColor;
                statTextColor = textColor;

                dialog.setTextFill(textColor);
                dialogText.setTextFill(textColor);
                optionsLabel.setTextFill(textColor);
                nameLabel.setTextFill(textColor);
                statText.setTextFill(textColor);
                optionsText.setTextFill(textColor);
            }

            // Background Colors
            String backgroundColorString = backgroundColorComboBox.getValue().trim();
            Color backgroundColor = Color.web(backgroundColorString);
            if (!Objects.equals(backgroundColor, null)) {
                dialogScrollPane.setStyle("-fx-background:" + backgroundColorString);
                dialogHistory.setStyle("-fx-background:" + backgroundColorString);
                displaySettingsPane.setStyle("-fx-background-color:" + backgroundColorString);
                tableScreenLayout.setStyle("-fx-background-color:" + backgroundColorString);
            }
            window.close();
        });

        displaySettingsPane.getChildren().addAll(backgroundColorComboBox,settingsChosenButton,backgroundColorComboBoxLabel,
                dialogLabel,dialogFontBox,dialogFontSize,optionsLabel1,optionsFontBox,optionsFontSize,textStyle,textColorComboBox,
                statsLabel,statsLabelFontBox,statsLabelFontSize,dialogHistoryLabel,dialogHistoryLabelFontBox,dialogHistoryLabelFontSize,
                characterNameLabel,characterNameLabelFontBox,characterNameLabelFontSize);
        Scene scene = new Scene(displaySettingsPane, 1280 , 720);

        window.setTitle("Settings");
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
    public void addEndingScreen(int endingScreenId, String sceneText, boolean startOver, boolean exit) {
        JSONObject endingScreen = new JSONObject();
        endingScreen.put("ID", endingScreenId);
        endingScreen.put("sceneText", sceneText);
        endingScreen.put("startOver", startOver);
        endingScreen.put("exit", exit);

        arrayOfEndingScreen.put(endingScreen);
    }
    public void finishEndingScreens() {
        writeToEndingScreenJSON(arrayOfEndingScreen);
    }
    private JSONArray endingScreenReader() {
        try {
            Path endingScreenPath = Path.of("src/EndingScreen.json");
            String input = Files.readString(endingScreenPath);
            return new JSONArray(input);
        } catch (IOException e) {
            System.out.println(e+"\n Could not find EndingScreen.json file");
        }
        return null;
    }
    private void writeToEndingScreenJSON(JSONArray arrayOfEndingScreen) {
        try {
            FileWriter charCreationSettingsFile = new FileWriter("src/EndingScreen.json");
            charCreationSettingsFile.write(arrayOfEndingScreen.toString());
            charCreationSettingsFile.close();
        } catch (IOException e) {
            System.out.println("Could not find EndingScreen.json");
        }
    }

    private void setEndingScreen(String sceneText, boolean startOver, boolean exit) {
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