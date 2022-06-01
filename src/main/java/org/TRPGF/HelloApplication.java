package org.TRPGF;
import javafx.application.Application;
import javafx.stage.Stage;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        Screen screen = Screen.getSingleInstance(stage);

        String statingText = "You are shopping for you mother.\n" +
                "Remember that you need to get a cake and a book on weeds!";
        screen.startingScreen("Distant Isles", statingText);

        screen.characterScreen("Make your character!");

        String path = "E:\\example_map.png";

        screen.tableScreen();
        screen.addTableScreenMap("Map", path);

        screen.addEndingScreen(1,"You forgot the cake \n do you want try again?", true, true);
        screen.addEndingScreen(2,"You forgot the book \n do you want try again?", true, true);
        screen.addEndingScreen(3,"You did it!",true,true);

        screen.finishEndingScreens();
    }

    public static void main(String[] args) {
        Dialog dialog = new Dialog();

        dialog.createDialogBox(1,"You see a bakery and a book store");
        dialog.addOption(1,1,"Go to the bakery",2);
        dialog.addOption(2,1,"Go to the book store",4);
        dialog.addOption(3,1,"go home",7);

        dialog.createDialogBox(2,"You enter the bakery. There are two options for you\n" +
                                "Cake or sweet roles");
        dialog.addOption(1,2,"Go for the cake",3);
        dialog.addOption(2,2,"Go for the sweet roles",3);

        dialog.createDialogBox(3,"Now do you go home \nOr was there something you forgot?");
        dialog.addOption(1,3,"Go home",7);
        dialog.addOption(2,3,"Go to the bakery",2);
        dialog.addOption(3,3,"Go to the book store",4);

        dialog.createDialogBox(4,"You enter the book store, it is big.\n" +
                                "You will need to look for the right book");
        dialog.addOptionWithRequirement(1,4,"Look for the right book","Perception",12,5,6);

        dialog.createDialogBox(5,"You Found it, Hurray!");
        dialog.addOptionPrevious(1,5, 1,2,"Go outside.",7,1);

        dialog.createDialogBox(6,"you did not find the book");
        dialog.addOption(1,6,"Go outside",3);

        dialog.createDialogBox(7, "You mother ask you to show if you got the cake");
        dialog.addOptionPreviousEnding(1,7,1,2,"Show the cake",8,1,false,1);

        dialog.createDialogBox(8,"You mother ask you to show if you got the book");
        dialog.addOptionPreviousEnding(1,8,1,5,"Show the book",9,1,false,2);

        dialog.createDialogBox(9, "You mom thanks you and tells you that you can rest now");
        dialog.addOptionEnding(1,9,"Go to sleep",3);

        dialog.finishStory();

        CharacterCreator characterCreator = CharacterCreator.getSingleInstance();
        characterCreator.addNameOption();

        characterCreator.addStat("Perception",0,20);
        characterCreator.setStatGenerationManual("Perception");

        characterCreator.finishCharacter();

        launch();
    }
}