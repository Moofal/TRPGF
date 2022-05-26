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

    private String type;
    private boolean endOnSuccess;
    private int endingScreenId;
    private int successEndingId;
    private int failEndingId;
    private int boxId;
    private int successScene;
    private int failScene;
    private int pChoiceId;
    private int pDialogBoxId;
    private String stat;
    private String rewardStat;
    private int statVal;
    private int rewardValue;

    Choice() {
    }

    public Choice(int id, String content, int boxId, int successScene) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
    }

    public Choice(int id, int boxId, String content, int endingScreenId) {
        super(id, content);
        this.boxId = boxId;
        this.endingScreenId = endingScreenId;
    }

    public Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.pChoiceId = pChoiceId;
        this.pDialogBoxId = pDialogBoxId;
    }


    public Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId, boolean endOnSuccess, int endingScreenId) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.pChoiceId = pChoiceId;
        this.pDialogBoxId = pDialogBoxId;
        this.endOnSuccess = endOnSuccess;
        this.endingScreenId = endingScreenId;
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

    public Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId, String stat, int statVal, boolean endOnSuccess, int endingScreenId) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.pChoiceId = pChoiceId;
        this.pDialogBoxId = pDialogBoxId;
        this.stat = stat;
        this.statVal = statVal;
        this.endOnSuccess = endOnSuccess;
        this.endingScreenId = endingScreenId;
    }

    public Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId, String stat, int statVal, String rewardStat, int rewardValue) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.pChoiceId = pChoiceId;
        this.pDialogBoxId = pDialogBoxId;
        this.stat = stat;
        this.statVal = statVal;
        this.rewardStat = rewardStat;
        this.rewardValue = rewardValue;
    }

    public Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId, String stat, int statVal, String rewardStat, int rewardValue, boolean endOnSuccess, int endingScreenId) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.pChoiceId = pChoiceId;
        this.pDialogBoxId = pDialogBoxId;
        this.stat = stat;
        this.statVal = statVal;
        this.rewardStat = rewardStat;
        this.rewardValue = rewardValue;
        this.endOnSuccess = endOnSuccess;
        this.endingScreenId = endingScreenId;
    }

    public Choice(int id, String content, int boxId, int successScene, String rewardStat, int rewardValue) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.rewardStat = rewardStat;
        this.rewardValue = rewardValue;
    }
    public Choice(int id, String content, int boxId, int successScene, String rewardStat, int rewardValue, int endingScreenId) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.rewardStat = rewardStat;
        this.rewardValue = rewardValue;
        this.endingScreenId = endingScreenId;
    }

    public Choice(int id, String content, int boxId, int successScene, int failScene, String stat, int statVal) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.stat = stat;
        this.statVal = statVal;
    }
    public Choice(int id, String content, int boxId, int successScene, int failScene, String stat, int statVal, boolean endOnSuccess, int endingScreenId) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.stat = stat;
        this.statVal = statVal;
        this.endOnSuccess = endOnSuccess;
        this.endingScreenId = endingScreenId;
    }

    public Choice(int id, String content, int boxId, int successScene, int failScene, String stat, int statVal, String rewardStat, int rewardValue) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.stat = stat;
        this.statVal = statVal;
        this.rewardStat = rewardStat;
        this.rewardValue = rewardValue;
    }

    public Choice(int id, String content, int boxId, int successScene, int failScene, String stat, int statVal, String rewardStat, int rewardValue, boolean endOnSuccess, int endingScreenId) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.stat = stat;
        this.statVal = statVal;
        this.rewardStat = rewardStat;
        this.rewardValue = rewardValue;
        this.endOnSuccess = endOnSuccess;
        this.endingScreenId = endingScreenId;
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

    @JsonProperty("END-ON-SUCCESS")
    public boolean isEndOnSuccess() {
        return endOnSuccess;
    }

    public void setEndOnSuccess(boolean endOnSuccess) {
        this.endOnSuccess = endOnSuccess;
    }

    @JsonProperty("ENDING-SCREEN-ID")
    public int getEndingScreenId() {
        return endingScreenId;
    }

    public void setEndingScreenId(int endingScreenId) {
        this.endingScreenId = endingScreenId;
    }

    @JsonProperty("SUCCESS-ENDING-ID")
    public int getSuccessEndingId() {
        return successEndingId;
    }

    public void setSuccessEndingId(int successEndingId) {
        this.successEndingId = successEndingId;
    }

    @JsonProperty("FAIL-ENDING-ID")
    public int getFailEndingId() {
        return failEndingId;
    }

    public void setFailEndingId(int failEndingId) {
        this.failEndingId = failEndingId;
    }

    @JsonProperty("REWARD-STAT")
    public String getRewardStat() {
        return rewardStat;
    }

    public void setRewardStat(String rewardStat) {
        this.rewardStat = rewardStat;
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