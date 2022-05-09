package org.TRPGF;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application{

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

        screen.endingScreen(1,"The end", true, true);
    }

    public static void main(String[] args) throws IOException {

        Dialog dialog = new Dialog();

        dialog.createDialogBox(1, "The man went into the woods\n" +
                "Hey fara into the woods\n" +
                "When a crow sat in the meadow and crawed\n" +
                "Hey fara. Faltu riltu raltura\n " +
                "The man thought with himself\n" +
                "I wonder if that crow wants to kill me?");
        dialog.addOption(1, 1, "Attack the crow", 2);
        dialog.addOption(2, 1, "Run for your life", 3);
        dialog.addOption(3, 1, "Observe", 1);
        dialog.createDialogBox(2, "Content 2");
        dialog.addOption(1, 2, "Go to 1", 1);
        dialog.addOption(2, 2, "Go to 3", 3);
        dialog.createDialogBox(3, "There is a dragon in front of you what do you do");
        dialog.addOptionWithRequirement(1, 3, "Need 15 str", 2, 1, "Str", 15);
        dialog.addOptionWithReward(2, 3, "str+1", 1, "Str", 1);
        //dialog.addOptionPrevious(3, 3, 2, "slay it",3);

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
        characterCreator.setStatGenerationDice("Con", 3,6);

        characterCreator.addAttribute("Good",0);

        characterCreator.finishCharacter();

        launch();
    }
}