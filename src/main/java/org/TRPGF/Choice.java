package org.TRPGF;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

/**
 * This class gets used by Dialog.java as a constructor class
 */

@JsonPropertyOrder("TYPE")
public class Choice extends Dialog {

    private int type;
    private boolean Ending;
    private int boxId;
    private int successScene;
    private int failScene;
    private int pChoiceId;
    private int pDialogBoxId;
    private String stat;
    private int statVal;
    private int rewardValue;

    Choice() {
    }

    public Choice(int id, String content, int boxId, int successScene) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
    }

    public Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.pChoiceId = pChoiceId;
        this.pDialogBoxId = pDialogBoxId;
    }

    public Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId, String stat, int statVal) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.pChoiceId = pChoiceId;
        this.pDialogBoxId = pDialogBoxId;
        this.stat = stat;
        this.statVal = statVal;
    }

    public Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId, String stat, int statVal, int rewardValue) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.pChoiceId = pChoiceId;
        this.pDialogBoxId = pDialogBoxId;
        this.stat = stat;
        this.statVal = statVal;
        this.rewardValue = rewardValue;
    }

    public Choice(int id, String content, int boxId, int successScene, String stat, int rewardValue) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.stat = stat;
        this.rewardValue = rewardValue;
    }

    public Choice(int id, String content, int boxId, int successScene, int failScene, String stat, int statVal) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.stat = stat;
        this.statVal = statVal;
    }

    //-----------------------------------Getters and Setters-----------------------------------//

    /**
     * Type Cheat Sheet:
     * 000 Normal Option
     * 100 Previous Choice
     * 110 Previous Choice + Requirement
     * 101 Previous Choice + Reward
     * 111 Previous Choice + Requirement + Reward
     * 010 Requirement Choice
     * 011 Requirement Choice + Reward
     * 001 Reward Choice
     *
     */
    @JsonProperty("TYPE")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @JsonProperty("BOXID")
    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    @JsonProperty("SUCCESS-SCENE")
    public int getSuccessScene() {
        return successScene;
    }

    public void setSuccessScene(int successScene) {
        this.successScene = successScene;
    }

    @JsonProperty("FAIL-SCENE")
    public int getFailScene() {
        return failScene;
    }

    public void setFailScene(int failScene) {
        this.failScene = failScene;
    }

    @JsonProperty("PREV-CHOICE")
    public int getpChoiceId() {
        return pChoiceId;
    }

    public void setpChoiceId(int pChoiceId) {
        this.pChoiceId = pChoiceId;
    }

    @JsonProperty("PREV-CHOICE-BOX")
    public int getpDialogBoxId() {
        return pDialogBoxId;
    }

    public void setpDialogBoxId(int pDialogBoxId) {
        this.pDialogBoxId = pDialogBoxId;
    }


    @JsonProperty("STAT")
    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @JsonProperty("STAT-REQ-VAL")
    public int getStatVal() {
        return statVal;
    }

    public void setStatVal(int statVal) {
        this.statVal = statVal;
    }

    @JsonProperty("REWARD-VAL")
    public int getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(int rewardValue) {
        this.rewardValue = rewardValue;
    }

    @JsonIgnore @Override
    public ArrayList<Choice> getDialogChoiceList() {
        return super.getDialogChoiceList();
    }

    //-----------------------------------To String-----------------------------------//


    @Override
    public String toString() {
        return "{" +
                " type=" + type +
                " id=" + super.getId() +
                ", boxId=" + boxId +
                ", content=" + super.getContent() +
                ", stat='" + stat + '\'' +
                ", statVal=" + statVal +
                ", pChoiceId=" + pChoiceId +
                ", nextScene=" + successScene +
                '}';
    }
}