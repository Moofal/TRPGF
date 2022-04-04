package com.example.example;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Screen screen = new Screen();
        screen.startingScreen(stage, "Lord of the rings", "One ring to bind them and rule them all");
        screen.characterScreen(stage, "Make your character!");
        screen.tableScreen(stage);
        screen.endingScreen(stage,1,"The end", true, true);
    }

    public static void main(String[] args) throws IOException {

        Dialog dialog = new Dialog();

        dialog.createDialog(1, "Content 1");
        dialog.addOption(1, 1, "Something 1", 2);
        dialog.addOption(2, 1, "Something 2", 1);
        dialog.addOption(3, 1, "Something 3", 1);
        dialog.createDialog(2, "Content 2");
        dialog.addOption(1, 2, "Something", 1);
        dialog.addOption(2, 2, "Something", 1);
        dialog.createDialog(3, "There is a dragon in front of you what do you do");
        dialog.addOptionWithReq(1, 3, "Conect3rw", 54, "Strenght", 15);
        dialog.addOptionPrevious(522, 3, 2, "slay it",0);
        dialog.addOptionReward(5425, 3, "seduce the dragon", 0, "Strength", 1);

        dialog.finishStory();

        CharCreator charCreator = new CharCreator();

        charCreator.addNameOption();
        charCreator.addStat("Wisdom",0,20);
        charCreator.setStatGenerationManual("Wisdom");
        charCreator.addStat("Str",0,20);
        charCreator.setStatGenerationManual("Str");
        charCreator.addStat("Con",0,20);
        charCreator.setStatGenerationDice("Con", 4,6);
        charCreator.finishCharacter();

        launch();
    }
}