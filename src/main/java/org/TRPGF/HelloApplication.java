package org.TRPGF;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        Screen screen = new Screen(stage);

        screen.startingScreen("Kråkevisa", "The man went into the woods\n" +
                "Hey fara into the woods\n" +
                "When a crow sat in the meadow and crawed\n" +
                "Hey fara. Faltu riltu raltura");

        screen.characterScreen("Make your character!");

        String path = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/7087ef35-84c5-472a-9ac1-c092dd681b70/d313jvn-5b4f00f8-e387-445a-8186-cdb98f203c93.jpg/v1/fill/w_1024,h_768,q_75,strp/middle_earth_map_by_kilbeth-d313jvn.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwic3ViIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl0sIm9iaiI6W1t7InBhdGgiOiIvZi83MDg3ZWYzNS04NGM1LTQ3MmEtOWFjMS1jMDkyZGQ2ODFiNzAvZDMxM2p2bi01YjRmMDBmOC1lMzg3LTQ0NWEtODE4Ni1jZGI5OGYyMDNjOTMuanBnIiwid2lkdGgiOiI8PTEwMjQiLCJoZWlnaHQiOiI8PTc2OCJ9XV19.Ulz1jIp_ifIq6To7pZ-F64LIN7S1tjPvu6wrmiVS-p8";
        screen.addTableScreenMap("Map", path);
        screen.tableScreen();

        screen.addEndingScreen(1,"The end", true, true);
        screen.addEndingScreen(2,"The end 2", true, false);
        screen.finishEndingScreens();
    }

    public static void main(String[] args) throws IOException {
        Dialog dialog = new Dialog();

        dialog.createDialogBox(1, "Default Content");
        dialog.addOption(1, 1, "I GO TO 2", 2);
        dialog.addOption(2, 1, "I GO TO 5", 5);
        dialog.createDialogBox(2, "I AM BOX 2");
        dialog.addOptionPrevious(1, 2, 1, 1, "Chose 1, in 1", 3, 4);
        dialog.addOptionPreviousRequirement(2, 2, 1, 1, "CHOSE 1, IN 1, + STAT 15 STR", "Str", 15, 3, 4);
        dialog.addOptionPreviousReward(3, 2, 1, 1, "CHOSE 1, IN 1, +2 STR", "Str", 2, 3, 4);
        dialog.createDialogBox(3, "GOOD CHOICE");
        dialog.addOptionWithReward(1, 3, "CON +10", "Con", 10, 6);
        dialog.createDialogBox(4, "BAD CHOICE");
        dialog.createDialogBox(5, "MORE OPTIONS!");
        dialog.addOptionPreviousRequirementReward(1, 5, 1, 1, "CHOSE 1, IN 1, + STAT 15 STR +2 DEX", "Str", 15, "Dex", 2, 3, 4);
        dialog.addOptionWithRequirement(2, 5, "STAT 15 STR", "Str", 15, 3, 4);
        dialog.addOptionWithRequirementReward(3, 5, "STAT 15 STR, +2 DEX", "Str", 15, "Dex", 2, 3, 4);
        dialog.createDialogBox(6, "GOOD AGAIN!");
        dialog.addOption(1, 6, "Go back?", 1);

        dialog.finishStory();



        CharacterCreator characterCreator = new CharacterCreator();

        //characterCreator.setName("B");
        characterCreator.addNameOption();

        characterCreator.addStat("Dex",0,20);
        characterCreator.setStat("Dex", 10);

        characterCreator.addStat("Wisdom",0,20);
        characterCreator.setStat("Wisdom", 10);

        characterCreator.addStat("Str",0,20);
        characterCreator.setStat("Str", 15);

        characterCreator.addStat("Con",0,20);
        characterCreator.setStat("Con", 20);

        characterCreator.addAttribute("Good",0);

        characterCreator.finishCharacter();

        launch();
    }
}