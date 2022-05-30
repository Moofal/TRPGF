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

    private ArrayList<Choice> dialogChoiceList = new ArrayList<>();
    private ArrayList<Dialog> dialogArrayList = new ArrayList<>();

    private int id;
    private String content;
    private String imgUrl;

    /**
     * This instances a new dialog which you have to use for all the options and dialog.
     */
    public Dialog() {
    }

    protected Dialog(int id, String content) {
        this.id = id;
        this.content = content;
    }
    private Dialog(int id, String content, String imgUrl) {
        this.id = id;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    //Dialog
    /**
     * Creates a dialog
     * @param id is the id for this dialog box
     * @param content is the text for this dialog for example: "Adventurer i need help!"
     */
    public void createDialogBox(int id, String content) {
        Dialog dialog = new Dialog(id, content);
        dialogArrayList.add(dialog);

    }
    /**
     * Creates a dialog with an image
     * @param id is the id for this dialog box
     * @param content is the text for this dialog
     * @param imgUrl is the absolute path for an image
     */
    public void createDialogBoxWithImage(int id, String content, String imgUrl) {
        Dialog dialog = new Dialog(id, content, imgUrl);
        dialogArrayList.add(dialog);

    }

    // Options
    /**
     * Creates a dialog option, there can only be a maximum of 3 options per dialog.
     * @param id is the id for this current option, option id's are relative, so they have to be in order, and only use 1, 2 or 3.
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param content is the text that will be displayed for example: "I will help you innkeeper!"
     * @param successDialog is the id of the next dialog on a successful choice
     */
    public void addOption(int id, int dialogBoxId, String content, int successDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog);
        choice.setType("0000");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates a dialog option which ends the game
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param content is the text that will be displayed for example: "I will help you innkeeper!"
     * @param endingScreenID is the id of the ending that will be displayed when the option is chosen.
     */
    public void addOptionEnding(int id, int dialogBoxId, String content, int endingScreenID) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, dialogBoxId, content, endingScreenID);
        choice.setType("0001");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates a dialog with a prerequisite option having to be chosen to be successful,
     * where if you chose A get to go to B, if you did not choose A, you get routed to C
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param pChoiceId is the id of the previous option having to be chosen for it to go to successDialog
     * @param pDialogBoxId is the id of the previous dialog pChoiceId is in relation to
     * @param content is the text that will be displayed for example: "I chose 1 in dialog 3"
     * @param successDialog is the next dialog if you clear the requirements
     * @param failDialog is the next dialog if you did not clear the requirements
     */
    public void addOptionPrevious(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, int successDialog, int failDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog, pChoiceId, pDialogBoxId);
        choice.setType("1000");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates a dialog with a prerequisite option having to be chosen to be successful,
     * where if you chose A get to go to B, if you did not choose A, you get routed to C
     * this one ends the game based on if you did or did not pass
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param pChoiceId is the id of the previous option having to be chosen for it to go to successDialog
     * @param pDialogBoxId is the id of the previous dialog pChoiceId is in relation to
     * @param content is the text that will be displayed for example: "I chose 1 in dialog 3"
     * @param successDialog is the next dialog if you clear the requirements
     * @param failDialog is the next dialog if you did not clear the requirements
     * @param endOnSuccess is a boolean value where true means the game ends if u clear the requirements,
     *                    false means the game ends if u fail the requirements
     * @param endingScreenId is the id for the ending screen that will be shown
     */
    public void addOptionPreviousEnding(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, int successDialog, int failDialog, boolean endOnSuccess, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog, pChoiceId, pDialogBoxId, endOnSuccess, endingScreenId);
        setEndingType(dialogBoxId, endOnSuccess, endingScreenId, choice);
        choice.setType("1001");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates a dialog with a requirement of a stat having to be greater than or equal to n, in addition to having chosen an option beforehand
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param pChoiceId is the id of the previous option having to be chosen for it to go to successDialog
     * @param pDialogBoxId is the id of the previous dialog pChoiceId is in relation to
     * @param content is the text that will be displayed for example: "I chose 1 in dialog 3"
     * @param stat is the name of the stat used in character creation, this is case-sensitive!!!
     * @param statVal is the value of what your stat has to be greater than or equal to >=
     * @param successDialog is the next dialog if you clear the requirements
     * @param failDialog is the next dialog if you did not clear the requirements
     */
    public void addOptionPreviousRequirement(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, String stat, int statVal, int successDialog, int failDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog,  pChoiceId, pDialogBoxId, stat, statVal);
        choice.setType("1100");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates a dialog with a requirement of a stat having to be greater than or equal to n
     * this ends the game
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param pChoiceId is the id of the previous option having to be chosen for it to go to successDialog
     * @param pDialogBoxId is the id of the previous dialog pChoiceId is in relation to
     * @param content is the text that will be displayed for example: "I chose 1 in dialog 3"
     * @param stat is the name of the stat used in character creation, this is case-sensitive!!!
     * @param statVal is the value of what your stat has to be greater than or equal to >=
     * @param successDialog is the next dialog if you clear the requirements
     * @param failDialog is the next dialog if you did not clear the requirements
     * @param endOnSuccess is a boolean value where true means the game ends if u clear the requirements,
     *                    false means the game ends if u fail the requirements
     * @param endingScreenId is the id for the ending screen that will be shown
     */
    public void addOptionPreviousRequirementEnding(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, String stat, int statVal, int successDialog, int failDialog, boolean endOnSuccess, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog,  pChoiceId, pDialogBoxId, stat, statVal, endOnSuccess, endingScreenId);
        setEndingType(dialogBoxId, endOnSuccess, endingScreenId, choice);
        choice.setType("1101");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates a dialog with a reward for a stat, if you have chosen a specific option previouslyc
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param pChoiceId is the id of the previous option having to be chosen for it to go to successDialog
     * @param pDialogBoxId is the id of the previous dialog pChoiceId is in relation to
     * @param content is the text that will be displayed for example: "I chose 1 in dialog 3"
     * @param rewardStat is the name of the stat used in character creation, this is case-sensitive!!!
     *                   this stat will be the one to be rewarded + or -
     * @param rewardVal is the value that the stat will increase or decrease by
     * @param successDialog is the next dialog if you clear the requirements
     * @param failDialog is the next dialog if you did not clear the requirements
     */
    public void addOptionPreviousReward(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, String rewardStat, int rewardVal, int successDialog, int failDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog,  pChoiceId, pDialogBoxId, rewardStat, rewardVal);
        choice.setRewardStat(rewardStat);
        choice.setRewardValue(rewardVal);
        choice.setStat(null);
        choice.setStatVal(0);
        choice.setType("1010");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates a dialog with a reward for a stat, if you have chosen a specific option previously
     * this ends the game
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param pChoiceId is the id of the previous option having to be chosen for it to go to successDialog
     * @param pDialogBoxId is the id of the previous dialog pChoiceId is in relation to
     * @param content is the text that will be displayed for example: "I chose 1 in dialog 3"
     * @param rewardStat is the name of the stat used in character creation, this is case-sensitive!!!
     *                   this stat will be the one to be rewarded + or -
     * @param rewardVal is the value that the stat will increase or decrease by
     * @param successDialog is the next dialog if you clear the requirements
     * @param failDialog is the next dialog if you did not clear the requirements
     * @param endOnSuccess is a boolean value where true means the game ends if u clear the requirements,
     *                    false means the game ends if u fail the requirements
     * @param endingScreenId is the id for the ending screen that will be shown
     */
    public void addOptionPreviousRewardEnding(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, String rewardStat, int rewardVal, int successDialog, int failDialog, boolean endOnSuccess, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog,  pChoiceId, pDialogBoxId, rewardStat, rewardVal, endOnSuccess, endingScreenId);
        setEndingType(dialogBoxId, endOnSuccess, endingScreenId, choice);
        choice.setRewardStat(rewardStat);
        choice.setRewardValue(rewardVal);
        choice.setStat(null);
        choice.setStatVal(0);
        choice.setType("1011");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates an option where you need to have chosen an option previously, and meet the stat requirements for it, with a reward.
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param pChoiceId is the id of the previous option having to be chosen for it to go to successDialog
     * @param pDialogBoxId is the id of the previous dialog pChoiceId is in relation to
     * @param content is the text that will be displayed for example: "I chose 1 in dialog 3"
     * @param stat is the name of the stat used in character creation, this is case-sensitive!!!
     * @param statVal is the value of what your stat has to be greater than or equal to >=
     * @param rewardStat is the name of the stat used in character creation, this is case-sensitive!!!
     *                   this stat will be the one to be rewarded + or -
     * @param rewardVal is the value that the stat will increase or decrease by
     * @param successDialog is the next dialog if you clear the requirements
     * @param failDialog is the next dialog if you did not clear the requirements
     */
    public void addOptionPreviousRequirementReward(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, String stat, int statVal, String rewardStat, int rewardVal, int successDialog, int failDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog,  pChoiceId, pDialogBoxId, stat, statVal, rewardStat, rewardVal);
        choice.setType("1110");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates an option where you need to have chosen an option previously, and meet the stat requirements for it, with a reward.
     * this ends the game
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param pChoiceId is the id of the previous option having to be chosen for it to go to successDialog
     * @param pDialogBoxId is the id of the previous dialog pChoiceId is in relation to
     * @param content is the text that will be displayed for example: "I chose 1 in dialog 3"
     * @param stat is the name of the stat used in character creation, this is case-sensitive!!!
     * @param statVal is the value of what your stat has to be greater than or equal to >=
     * @param rewardStat is the name of the stat used in character creation, this is case-sensitive!!!
     *                   this stat will be the one to be rewarded + or -
     * @param rewardVal is the value that the stat will increase or decrease by
     * @param successDialog is the next dialog if you clear the requirements
     * @param failDialog is the next dialog if you did not clear the requirements
     * @param endOnSuccess is a boolean value where true means the game ends if u clear the requirements,
     *                    false means the game ends if u fail the requirements
     * @param endingScreenId is the id for the ending screen that will be shown
     */
    public void addOptionPreviousRequirementRewardEnding(int id, int dialogBoxId, int pChoiceId, int pDialogBoxId, String content, String stat, int statVal, String rewardStat, int rewardVal, int successDialog, int failDialog, boolean endOnSuccess, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog,  pChoiceId, pDialogBoxId, stat, statVal, rewardStat, rewardVal, endOnSuccess, endingScreenId);
        setEndingType(dialogBoxId, endOnSuccess, endingScreenId, choice);
        choice.setType("1111");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates a dialog with a requirement of a stat having to be greater than or equal to n
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param content is the text that will be displayed for example: "I chose 1 in dialog 3"
     * @param stat is the name of the stat used in character creation, this is case-sensitive!!!
     * @param statVal is the value of what your stat has to be greater than or equal to >=
     * @param successDialog is the next dialog if you clear the requirements
     * @param failDialog is the next dialog if you did not clear the requirements
     */
    public void addOptionWithRequirement(int id, int dialogBoxId, String content, String stat, int statVal, int successDialog, int failDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog, stat, statVal);
        choice.setType("0100");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates a dialog with a requirement of a stat having to be greater than or equal to n
     * this ends the game
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param content is the text that will be displayed for example: "I chose 1 in dialog 3"
     * @param stat is the name of the stat used in character creation, this is case-sensitive!!!
     * @param statVal is the value of what your stat has to be greater than or equal to >=
     * @param successDialog is the next dialog if you clear the requirements
     * @param failDialog is the next dialog if you did not clear the requirements
     * @param endOnSuccess is a boolean value where true means the game ends if u clear the requirements,
     *                    false means the game ends if u fail the requirements
     * @param endingScreenId is the id for the ending screen that will be shown
     */
    public void addOptionWithRequirementEnding(int id, int dialogBoxId, String content, String stat, int statVal, int successDialog, int failDialog, boolean endOnSuccess, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog, stat, statVal, endOnSuccess, endingScreenId);
        setEndingType(dialogBoxId, endOnSuccess, endingScreenId, choice);
        choice.setType("0101");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates an option where you have a stat requirement and reward a stat for clearing the requirement
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param content is the text that will be displayed for example: "I chose 1 in dialog 3"
     * @param stat is the name of the stat used in character creation, this is case-sensitive!!!
     * @param statVal is the value of what your stat has to be greater than or equal to >=
     * @param rewardStat is the name of the stat used in character creation, this is case-sensitive!!!
     *                   this stat will be the one to be rewarded + or -
     * @param rewardVal is the value that the stat will increase or decrease by
     * @param successDialog is the next dialog if you clear the requirements
     * @param failDialog is the next dialog if you did not clear the requirements
     */
    public void addOptionWithRequirementReward(int id, int dialogBoxId, String content, String stat, int statVal, String rewardStat, int rewardVal, int successDialog, int failDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog, stat, statVal, rewardStat, rewardVal);
        choice.setType("0110");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates an option where you have a stat requirement and reward a stat for clearing the requirement
     * this ends the game
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param content is the text that will be displayed for example: "I chose 1 in dialog 3"
     * @param stat is the name of the stat used in character creation, this is case-sensitive!!!
     * @param statVal is the value of what your stat has to be greater than or equal to >=
     * @param rewardStat is the name of the stat used in character creation, this is case-sensitive!!!
     *                   this stat will be the one to be rewarded + or -
     * @param rewardVal is the value that the stat will increase or decrease by
     * @param successDialog is the next dialog if you clear the requirements
     * @param failDialog is the next dialog if you did not clear the requirements
     * @param endOnSuccess is a boolean value where true means the game ends if u clear the requirements,
     *                    false means the game ends if u fail the requirements
     * @param endingScreenId is the id for the ending screen that will be shown
     */
    public void addOptionWithRequirementRewardEnding(int id, int dialogBoxId, String content, String stat, int statVal, String rewardStat, int rewardVal, int successDialog, int failDialog, boolean endOnSuccess, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, failDialog, stat, statVal, rewardStat, rewardVal, endOnSuccess, endingScreenId);
        setEndingType(dialogBoxId, endOnSuccess, endingScreenId, choice);
        choice.setType("0111");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates an option where you reward a stat
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param content is the text that will be displayed
     * @param rewardStat is the name of the stat used in character creation, this is case-sensitive!!!
     *                   this stat will be the one to be rewarded + or -
     * @param rewardVal is the value that the stat will increase or decrease by
     * @param successDialog is the next dialog
     */
    public void addOptionWithReward(int id, int dialogBoxId, String content, String rewardStat, int rewardVal, int successDialog) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, successDialog, rewardStat, rewardVal);
        choice.setType("0010");
        checkAndAdd(dialog, id, choice);
    }
    /**
     * Creates an option where you reward a stat
     * this ends the game
     * @param id is the id for this current option
     * @param dialogBoxId is the id of the dialog it is in relation to.
     * @param content is the text that will be displayed
     * @param rewardStat is the name of the stat used in character creation, this is case-sensitive!!!
     *                   this stat will be the one to be rewarded + or -
     * @param rewardVal is the value that the stat will increase or decrease by
     * @param endingScreenId is the id for the ending screen that will be shown
     */
    public void addOptionWithRewardEnding(int id, int dialogBoxId, String content, String rewardStat, int rewardVal, int endingScreenId) {
        Dialog dialog = getDialogById(dialogBoxId);
        Choice choice = new Choice(id, content, dialogBoxId, rewardStat, rewardVal, endingScreenId);
        choice.setType("0011");
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

    /**
     * You have to use this function under all of your Dialog and Choices, and right before Launch();
     * This function finishes your story and makes a .json containing the entire story with all the options
     */
    public void finishStory() {
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = "src/story.json";
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), getDialogArrayList());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("ID")
    public int getId() {
        return id;
    }
    //-----------------------------------Getters and Setters-----------------------------------//

    protected void setId(int id) {
        this.id = id;
    }
    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("CONTENT")
    public String getContent() {
        return content;
    }

    protected void setContent(String content) {
        this.content = content;
    }
    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("IMAGE-URL")
    public String getImgUrl() {
        return imgUrl;
    }

    protected void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    public ArrayList<Choice> getDialogChoiceList() {
        return dialogChoiceList;
    }

    protected void setDialogChoiceList(ArrayList<Choice> dialogChoiceList) {
        this.dialogChoiceList = dialogChoiceList;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonIgnore
    public ArrayList<Dialog> getDialogArrayList() {
        return new ArrayList<>(dialogArrayList);
    }

    protected void setDialogArrayList(ArrayList<Dialog> dialogArrayList) {
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