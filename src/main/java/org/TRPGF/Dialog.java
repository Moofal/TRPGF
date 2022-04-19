package org.TRPGF;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class handles everything with dialog.
 */
@JsonPropertyOrder({"ID", "CONTENT", "CHOICES"})
public class Dialog {

    public ArrayList<Choice> dialogChoiceList = new ArrayList<>();
    public ArrayList<Dialog> dialogArrayList = new ArrayList<>();

    private int id;
    private String content;

    public Dialog() {
    }

    public Dialog(int id, String content) {
        this.id = id;
        this.content = content;
    }

    /**
     * Lager en dialog
     * @param id is the id for this dialog box
     * @param content is the text for this dialog for example: "Adventurer i need help!"
     */
    public void createDialogBox(int id, String content) {
        Dialog dialog = new Dialog(id, content);
        dialogArrayList.add(dialog);

    }

    /**
     * Lager et svarsmultighet til dialogen den er knyttet til.
     * @param id er id'en til dette valget
     * @param dialogBoxId er id'en til createDialogBox den er knyttet til
     * @param content er teksten til denne dialogen feks: "i will help you inkeeper!"
     * @param nextDialog er id'en til den neste dialogen hvis du velger dette valget.
     */
    public void addOption(int id, int dialogBoxId, String content, int nextDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, nextDialog);
        choice.setType("Normal Choice");
        if (dialogSizeCheck(dialog)) return;
        if (choiceIdCheck(id, dialog)) return;
        dialog.dialogChoiceList.add(choice);
    }

    /**
     * Lager et svarsmultighet til dialogen den er knyttet til, men denne har en "prerequisite" som vil si at du må velge A for å svare B
     * @param id er id'en til dette valget
     * @param dialogBoxId er id'en til createDialogBox den er knyttet til
     * @param pChoiceId er id'en tildet forrige valget som du måtte velge for å gjøre dette valget, feks: du måtte ha valgt 1, for å velge valg 30
     * @param content er teksten til denne dialogen feks: "i will help you inkeeper!"
     * @param nextDialog er id'en til den neste dialogen hvis du velger dette valget.
     */
    public void addOptionPrevious(int id, int dialogBoxId, int pChoiceId, String content, int nextDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, nextDialog, pChoiceId);
        choice.setType("Previous Choice");
        if (dialogSizeCheck(dialog)) return;
        if (choiceIdCheck(id, dialog)) return;
        dialog.dialogChoiceList.add(choice);
    }

    /**
     * Lager et svarsmultighet til dialogen den er knyttet til, men denne har en stat check, så du må ha mer enn statVal for å passere
     * @param id er id'en til dette valget
     * @param dialogBoxId er id'en til createDialogBox den er knyttet til
     * @param content er teksten til denne dialogen feks: "i will help you inkeeper!"
     * @param successDialog er id'en til den nye dialog boxen hvis karakteren har større eller lik statval
     * @param failDialog er id'en til den nye dialog boxen hvis karakteren ikke har større eller lik statval
     * @param stat er statten som skal bli skjekket.
     * @param statVal er verdien til statten.
     */
    public void addOptionWithRequirement(int id, int dialogBoxId, String content, int successDialog, int failDialog, String stat, int statVal) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog, stat, statVal);
        choice.setType("Choice with Requirement");
        if (dialogSizeCheck(dialog)) return;
        if (choiceIdCheck(id, dialog)) return;
        dialog.dialogChoiceList.add(choice);
    }

    /**
     * Lager et svarsmultighet til dialogen den er knyttet til, men denne gir deg en gave hvis du velger den.
     * @param id er id'en til dette valget
     * @param dialogBoxId er id'en til createDialogBox den er knyttet til
     * @param content er teksten til denne dialogen feks: "i will help you inkeeper!"
     * @param nextDialog er id'en til den neste dialogen hvis du velger dette valget.
     * @param stat er statten som skal bli skjekket.
     * @param value er verdien statten skal øke med.
     */
    public void addOptionWithReward(int id, int dialogBoxId, String content, int nextDialog, String stat, int value) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, nextDialog, stat, value);
        choice.setType("Choice with a reward");
        if (dialogSizeCheck(dialog)) return;
        if (choiceIdCheck(id, dialog)) return;
        dialog.dialogChoiceList.add(choice);
    }

    @JsonIgnore
    private Dialog getDialogById(int dialogBoxId) {
        return getDialogArrayList().get(dialogBoxId - 1);
    }

    @JsonIgnore
    private boolean dialogSizeCheck(Dialog dialog) {
        if ( dialog.dialogChoiceList.size() >= 3) {
            System.out.println("A Dialog cannot have more than 3 options");
            return true;
        }
        return false;
    }

    @JsonIgnore
    private boolean choiceIdCheck(int id, Dialog dialog) {
        for (Choice choice1 : dialog.dialogChoiceList) {
            if (choice1.getId() == id) {
                System.out.println("You cannot have 2 choices with the same ID");
                return true;
            }
        }
        return false;
    }

    public void finishStory() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = "src/story.json";
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), getDialogArrayList());

    }

    //-----------------------------------Getters and Setters-----------------------------------//

    @JsonProperty("ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Choice> getDialogChoiceList() {
        return dialogChoiceList;
    }

    public void setDialogChoiceList(ArrayList<Choice> dialogChoiceList) {
        this.dialogChoiceList = dialogChoiceList;
    }

    @JsonIgnore
    public ArrayList<Dialog> getDialogArrayList() {
        return new ArrayList<>(dialogArrayList);
    }

    public void setDialogArrayList(ArrayList<Dialog> dialogArrayList) {
        this.dialogArrayList = dialogArrayList;
    }

    //-----------------------------------To String-----------------------------------//

    @Override
    public String toString() {
        return "{" +
                ", id=" + id +
                ", content='" + content + '\'' +
                ", dialogChoiceList=" + dialogChoiceList +
                '}';
    }
}
