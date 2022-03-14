package com.example.example;
import com.example.example.dialog.Dialog;
import com.example.example.dialog.DialogWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load());
        stage.setTitle("Hello!");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        Dialog dialog = new Dialog();
        DialogWrapper dialogWrapper = new DialogWrapper();
        dialog.addOption(1, 1, "Something", Color.RED, 1);
        dialogWrapper.createDialog(1, "Content", Color.AQUA);
        dialogWrapper.createDialog(2, "Content2", Color.AQUA);

        System.out.println(dialogWrapper.getDialogChoiceList());

        //dialogBox.addOptionStatCheck(3, 1, "let me drink 100 beers", Color.GREEN, 200, "Strength", 15);
        //dialogBox.createChoices(20, "There is a dragon in front of you what do you do", Color.GREEN);
        //dialogBox.addOptionPrevious(522, 20, 2, "slay it", Color.GREEN,0);
        //dialogBox.addOption(5425, 20, "seduce the dragon", Color.GREEN, 0);


        //launch();
    }
}