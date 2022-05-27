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
    private String imgUrl;

    public Dialog() {
    }

    public Dialog(int id, String content) {
        this.id = id;
        this.content = content;
    }
    public Dialog(int id, String content, String imgUrl) {
        this.id = id;
        this.content = content;
        this.imgUrl = imgUrl;
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
    public void createDialogBoxWithImage(int id, String content, String imgUrl) {
        Dialog dialog = new Dialog(id, content, imgUrl);
        dialogArrayList.add(dialog);

    }

    /**
     * Lager et svarsmultighet til dialogen den er knyttet til.
     * @param id er id'en til dette valget
     * @param dialogBoxId er id'en til createDialogBox den er knyttet til
     * @param content er teksten til denne dialogen feks: "i will help you inkeeper!"
     * @param successDialog er id'en til den neste dialogen hvis du velger dette valget.
     */
    public void addOption(int id, int dialogBoxId, String content, int successDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog);
        choice.setType("0000");
        checkAndAdd(dialog, id, choice);
    }
    public void addOptionEnding(int id, int dialogBoxId, String content, int endingScreenID) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, dialogBoxId, content, endingScreenID);
        choice.setType("0001");
        checkAndAdd(dialog, id, choice);
    }

    /**
     * Lager et svarsmultighet til dialogen den er knyttet til, men denne har en "prerequisite" som vil si at du må velge A for å svare B
     * @param id er id'en til dette valget
     * @param dialogBoxId er id'en til createDialogBox den er knyttet til
     * @param pChoiceId er id'en tildet forrige valget som du måtte velge for å gjøre dette valget, feks: du måtte ha valgt 1, for å velge valg 30
     * @param content er teksten til denne dialogen feks: "i will help you inkeeper!"
     * @param successDialog er id'en til den neste dialogen hvis du velger dette valget.
     * @param failDialog er id'en til den neste dialogen hvis du velger dette valget.
     */
    public void addOptionPrevious(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, int successDialog, int failDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog, pChoiceId, pDialogBoxId);
        choice.setType("1000");
        checkAndAdd(dialog, id, choice);
    }
    public void addOptionPreviousEnding(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, int successDialog, int failDialog, boolean endOnSuccess, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog, pChoiceId, pDialogBoxId, endOnSuccess, endingScreenId);
        setEndingType(dialogBoxId, endOnSuccess, endingScreenId, choice);
        choice.setType("1001");
        checkAndAdd(dialog, id, choice);
    }
    public void addOptionPreviousRequirement(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, String stat, int statVal, int successDialog, int failDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog,  pChoiceId, pDialogBoxId, stat, statVal);
        choice.setType("1100");
        checkAndAdd(dialog, id, choice);
    }
    public void addOptionPreviousRequirementEnding(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, String stat, int statVal, int successDialog, int failDialog, boolean endOnSuccess, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog,  pChoiceId, pDialogBoxId, stat, statVal, endOnSuccess, endingScreenId);
        setEndingType(dialogBoxId, endOnSuccess, endingScreenId, choice);
        choice.setType("1100");
        checkAndAdd(dialog, id, choice);
    }
    public void addOptionPreviousReward(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, String stat, int rewardVal, int successDialog, int failDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog,  pChoiceId, pDialogBoxId, stat, rewardVal);
        choice.setRewardStat(stat);
        choice.setRewardValue(rewardVal);
        choice.setType("1010");
        checkAndAdd(dialog, id, choice);
    }
    public void addOptionPreviousRewardEnding(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, String stat, int rewardVal, int successDialog, int failDialog, boolean endOnSuccess, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog,  pChoiceId, pDialogBoxId, stat, rewardVal, endOnSuccess, endingScreenId);
        setEndingType(dialogBoxId, endOnSuccess, endingScreenId, choice);
        choice.setType("1010");
        checkAndAdd(dialog, id, choice);
    }
    public void addOptionPreviousRequirementReward(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, String stat, int statVal, String rewardStat, int rewardValue, int successDialog, int failDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog,  pChoiceId, pDialogBoxId, stat, statVal, rewardStat, rewardValue);
        choice.setType("1110");
        checkAndAdd(dialog, id, choice);
    }
    public void addOptionPreviousRequirementRewardEnding(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, String stat, int statVal, String rewardStat, int rewardValue, int successDialog, int failDialog, boolean endOnSuccess, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog,  pChoiceId, pDialogBoxId, stat, statVal, rewardStat, rewardValue, endOnSuccess, endingScreenId);
        setEndingType(dialogBoxId, endOnSuccess, endingScreenId, choice);
        choice.setType("1110");
        checkAndAdd(dialog, id, choice);
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
    public void addOptionWithRequirement(int id, int dialogBoxId, String content, String stat, int statVal, int successDialog, int failDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog, stat, statVal);
        choice.setType("0100");
        checkAndAdd(dialog, id, choice);
    }
    public void addOptionWithRequirementEnding(int id, int dialogBoxId, String content, String stat, int statVal, int successDialog, int failDialog, boolean endOnSuccess, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog, stat, statVal, endOnSuccess, endingScreenId);
        setEndingType(dialogBoxId, endOnSuccess, endingScreenId, choice);
        choice.setType("0100");
        checkAndAdd(dialog, id, choice);
    }
    public void addOptionWithRequirementReward(int id, int dialogBoxId, String content, String stat, int statVal, String rewardStat, int rewardVal, int successDialog, int failDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog, stat, statVal, rewardStat, rewardVal);
        choice.setType("0110");
        checkAndAdd(dialog, id, choice);
    }
    public void addOptionWithRequirementRewardEnding(int id, int dialogBoxId, String content, String stat, int statVal, String rewardStat, int rewardVal, int successDialog, int failDialog, boolean endOnSuccess, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog, stat, statVal, rewardStat, rewardVal, endOnSuccess, endingScreenId);
        setEndingType(dialogBoxId, endOnSuccess, endingScreenId, choice);
        choice.setType("0110");
        checkAndAdd(dialog, id, choice);
    }

    /**
     * Lager et svarsmultighet til dialogen den er knyttet til, men denne gir deg en gave hvis du velger den.
     * @param id er id'en til dette valget
     * @param dialogBoxId er id'en til createDialogBox den er knyttet til
     * @param content er teksten til denne dialogen feks: "i will help you inkeeper!"
     * @param successDialog er id'en til den neste dialogen hvis du velger dette valget.
     * @param stat er statten som skal bli skjekket.
     * @param rewardVal er verdien statten skal øke med.
     */
    public void addOptionWithReward(int id, int dialogBoxId, String content, String stat, int rewardVal, int successDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, stat, rewardVal);
        choice.setType("0010");
        checkAndAdd(dialog, id, choice);
    }
    public void addOptionWithRewardEnding(int id, int dialogBoxId, String content, String stat, int rewardVal, int successDialog, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, stat, rewardVal, endingScreenId);
        choice.setType("0010");
        checkAndAdd(dialog, id, choice);
    }

    private void checkAndAdd(Dialog dialog, int id, Choice choice) {
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

    @JsonIgnore
    private void setEndingType(int dialogBoxId, boolean endOnSuccess, int endingScreenId, Choice choice) {
        if (endOnSuccess) {
            choice.setSuccessEndingId(endingScreenId);
            choice.setSuccessScene(dialogBoxId);
        } else {
            choice.setFailEndingId(endingScreenId);
            choice.setFailScene(dialogBoxId);
        }
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