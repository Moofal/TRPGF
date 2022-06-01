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



        CharacterCreator characterCreator = CharacterCreator.getSingleInstance();
        characterCreator.addNameOption();

        characterCreator.addStat("Perception",0,20);
        characterCreator.setStatGenerationManual("Perception");

        characterCreator.finishCharacter();

        launch();
    }
}