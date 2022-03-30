package com.example.example;
import com.example.example.dialog.Dialog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Screen screen = new Screen();
        screen.startingScreen(stage,"Lord of the rings", "One ring to bind them and rule them all");
        screen.tableScreen(stage);
        screen.characterScreen("Make your character!");
    }

    public static void main(String[] args) {

        Dialog dialog = new Dialog();

        dialog.createDialog(1, "Content");
        dialog.addOption(1, 1, "Something", 1);
        dialog.addOption(2, 1, "Something", 1);
        dialog.addOption(3, 1, "Something", 1);
        dialog.createDialog(2, "Content2");
        dialog.addOption(1, 2, "Something", 1);
        dialog.addOption(2, 2, "Something", 1);
        dialog.createDialog(3, "There is a dragon in front of you what do you do");
        dialog.addOptionWithReq(1, 3, "Conect3rw", 54, "Strenght", 15);
        dialog.addOptionPrevious(522, 3, 2, "slay it",0);
        dialog.addOptionReward(5425, 3, "seduce the dragon", 0, "Strength", 1);

        System.out.println(dialog.getDialogArrayList());

        launch();
    }
}