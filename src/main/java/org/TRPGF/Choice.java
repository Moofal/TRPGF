package org.TRPGF;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonPropertyOrder("TYPE")
public class Choice extends Dialog {

    /**
     * Denne klassen har choice konstruktøren som trengs til Dialog, og den Extender Dialog.
     */

    private String type;
    private int boxId;
    private int successScene;
    private int failScene;
    private int pChoiceId;
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

    public Choice(int id, String content, int boxId, int successScene, int pChoiceId) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.pChoiceId = pChoiceId;
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

    @JsonProperty("TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
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