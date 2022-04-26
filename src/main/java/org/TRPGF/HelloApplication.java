package org.TRPGF;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        Screen screen = new Screen(stage);

        screen.startingScreen("Kråkevisa", "Og mannen han gjekk seg i Vea skog\n" +
                "Hei fara i Vea skog\n" +
                "Då høyrde an ei kråke i lunden gol\n" +
                "Hei fara faltu riltu raltu ra");

        screen.characterScreen("Make your character!");

        screen.addTableScreenMap("E:\\Arbeid\\SM4\\Rammeverk\\workshop\\TRPGF\\TRPGF\\src\\ball of light.jpg");
        screen.tableScreen();

        screen.endingScreen(1,"The end", true, true);
    }

    public static void main(String[] args) throws IOException {

        Dialog dialog = new Dialog();

        dialog.createDialogBox(1, "Og mannen han tenkte med sjølve seg\n" +
                "Hei fara med sjølve seg\n" +
                "Eg meine den kråka vil drepa meg\n" +
                "Hei fara faltu riltu raltu ra");
        dialog.addOption(1, 1, "Angrep kråka", 2);
        dialog.addOption(2, 1, "Løp for liver", 3);
        dialog.addOption(3, 1, "observer", 1);
        dialog.createDialogBox(2, "Content 2");
        dialog.addOption(1, 2, "Go to 1", 1);
        dialog.addOption(2, 2, "Go to 3", 3);
        dialog.createDialogBox(3, "There is a dragon in front of you what do you do");
        dialog.addOptionWithRequirement(1, 3, "Need 15 str", 2, 1, "Str", 15);
        //dialog.addOptionPrevious(522, 3, 2, "slay it",0);
        dialog.addOptionWithReward(2, 3, "str+1", 1, "Str", 1);

        dialog.finishStory();

        CharacterCreator characterCreator = new CharacterCreator();

        characterCreator.addNameOption();

        characterCreator.addStat("Dex",0,20);
        characterCreator.setStat("Dex", 10);

        characterCreator.addStat("Wisdom",0,20);
        characterCreator.setStatGenerationManual("Wisdom");

        characterCreator.addStat("Str",0,20);
        characterCreator.setStatGenerationManual("Str");

        characterCreator.addStat("Con",0,20);
        characterCreator.setStatGenerationDice("Con", 4,6);

        characterCreator.addAttribute("Good",0);

        characterCreator.finishCharacter();

        launch();
    }
}