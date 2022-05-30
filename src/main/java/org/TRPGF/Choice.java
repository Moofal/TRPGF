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

    protected Choice(int id, String content, int boxId, int successScene) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
    }

    protected Choice(int id, int boxId, String content, int endingScreenId) {
        super(id, content);
        this.boxId = boxId;
        this.endingScreenId = endingScreenId;
    }

    protected Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.pChoiceId = pChoiceId;
        this.pDialogBoxId = pDialogBoxId;
    }


    protected Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId, boolean endOnSuccess, int endingScreenId) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.pChoiceId = pChoiceId;
        this.pDialogBoxId = pDialogBoxId;
        this.endOnSuccess = endOnSuccess;
        this.endingScreenId = endingScreenId;
    }

    protected Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId, String stat, int statVal) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.pChoiceId = pChoiceId;
        this.pDialogBoxId = pDialogBoxId;
        this.stat = stat;
        this.statVal = statVal;
    }

    protected Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId, String stat, int statVal, boolean endOnSuccess, int endingScreenId) {
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

    protected Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId, String stat, int statVal, String rewardStat, int rewardValue) {
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

    protected Choice(int id, String content, int boxId, int successScene, int failScene, int pChoiceId, int pDialogBoxId, String stat, int statVal, String rewardStat, int rewardValue, boolean endOnSuccess, int endingScreenId) {
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

    protected Choice(int id, String content, int boxId, int successScene, String rewardStat, int rewardValue) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.rewardStat = rewardStat;
        this.rewardValue = rewardValue;
    }
    protected Choice(int id, String content, int boxId, String rewardStat, int rewardValue, int endingScreenId) {
        super(id, content);
        this.boxId = boxId;
        this.rewardStat = rewardStat;
        this.rewardValue = rewardValue;
        this.endingScreenId = endingScreenId;
    }

    protected Choice(int id, String content, int boxId, int successScene, int failScene, String stat, int statVal) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.stat = stat;
        this.statVal = statVal;
    }
    protected Choice(int id, String content, int boxId, int successScene, int failScene, String stat, int statVal, boolean endOnSuccess, int endingScreenId) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.stat = stat;
        this.statVal = statVal;
        this.endOnSuccess = endOnSuccess;
        this.endingScreenId = endingScreenId;
    }

    protected Choice(int id, String content, int boxId, int successScene, int failScene, String stat, int statVal, String rewardStat, int rewardValue) {
        super(id, content);
        this.boxId = boxId;
        this.successScene = successScene;
        this.failScene = failScene;
        this.stat = stat;
        this.statVal = statVal;
        this.rewardStat = rewardStat;
        this.rewardValue = rewardValue;
    }

    protected Choice(int id, String content, int boxId, int successScene, int failScene, String stat, int statVal, String rewardStat, int rewardValue, boolean endOnSuccess, int endingScreenId) {
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
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("TYPE")
    public String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("BOXID")
    public int getBoxId() {
        return boxId;
    }

    protected void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("PREV-CHOICE")
    public int getpChoiceId() {
        return pChoiceId;
    }

    protected void setpChoiceId(int pChoiceId) {
        this.pChoiceId = pChoiceId;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("PREV-CHOICE-BOX")
    public int getpDialogBoxId() {
        return pDialogBoxId;
    }

    protected void setpDialogBoxId(int pDialogBoxId) {
        this.pDialogBoxId = pDialogBoxId;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("STAT")
    public String getStat() {
        return stat;
    }

    protected void setStat(String stat) {
        this.stat = stat;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("STAT-REQ-VAL")
    public int getStatVal() {
        return statVal;
    }

    protected void setStatVal(int statVal) {
        this.statVal = statVal;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("REWARD-VAL")
    public int getRewardValue() {
        return rewardValue;
    }

    protected void setRewardValue(int rewardValue) {
        this.rewardValue = rewardValue;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("SUCCESS-SCENE")
    public int getSuccessScene() {
        return successScene;
    }

    protected void setSuccessScene(int successScene) {
        this.successScene = successScene;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("FAIL-SCENE")
    public int getFailScene() {
        return failScene;
    }

    protected void setFailScene(int failScene) {
        this.failScene = failScene;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("END-ON-SUCCESS")
    public boolean isEndOnSuccess() {
        return endOnSuccess;
    }

    protected void setEndOnSuccess(boolean endOnSuccess) {
        this.endOnSuccess = endOnSuccess;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("ENDING-SCREEN-ID")
    public int getEndingScreenId() {
        return endingScreenId;
    }

    protected void setEndingScreenId(int endingScreenId) {
        this.endingScreenId = endingScreenId;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("SUCCESS-ENDING-ID")
    public int getSuccessEndingId() {
        return successEndingId;
    }

    protected void setSuccessEndingId(int successEndingId) {
        this.successEndingId = successEndingId;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("FAIL-ENDING-ID")
    public int getFailEndingId() {
        return failEndingId;
    }

    protected void setFailEndingId(int failEndingId) {
        this.failEndingId = failEndingId;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("REWARD-STAT")
    public String getRewardStat() {
        return rewardStat;
    }

    protected void setRewardStat(String rewardStat) {
        this.rewardStat = rewardStat;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonIgnore @Override
    public ArrayList<Choice> getDialogChoiceList() {
        return super.getDialogChoiceList();
    }

    //-----------------------------------To String-----------------------------------//


    /**
     * @deprecated
     */
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