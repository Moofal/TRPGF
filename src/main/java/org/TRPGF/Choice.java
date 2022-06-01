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

    private int dialogBoxId;
    private int pChoiceId;
    private int pDialogBoxId;
    private String stat;
    private int statVal;
    private String rewardStat;
    private int rewardValue;
    private int failDialogBoxId;
    private int successDialogBoxId;
    private boolean endOnSuccess;
    private int successEndingId;
    private int failEndingId;
    private int endingScreenId;
    private String type;

    Choice() {
    }

    protected Choice(int id, int dialogBoxId, String content, int pChoiceId, int pDialogBoxId, String stat, int statVal, String rewardStat, int rewardValue, int failDialogBoxId, int successDialogBoxId, boolean endOnSuccess, int endingScreenId) {
        super(id, content);
        this.dialogBoxId = dialogBoxId;
        this.pChoiceId = pChoiceId;
        this.pDialogBoxId = pDialogBoxId;
        this.stat = stat;
        this.statVal = statVal;
        this.rewardStat = rewardStat;
        this.rewardValue = rewardValue;
        this.failDialogBoxId = failDialogBoxId;
        this.successDialogBoxId = successDialogBoxId;
        this.endOnSuccess = endOnSuccess;
        this.endingScreenId = endingScreenId;
    }
    protected Choice(int id, int dialogBoxId, String content, int successDialogBoxId) {
        this(id, dialogBoxId, content, 0, 0, null, 0, null, 0, 0, successDialogBoxId, false, 0);
    }
    protected Choice(int id, int dialogBoxId, String content, int pChoiceId, int pDialogBoxId, int successDialogBoxId, int failDialogBoxId) {
        this(id, dialogBoxId, content, pChoiceId, pDialogBoxId, null, 0, null, 0, failDialogBoxId, successDialogBoxId, false, 0);
    }
    protected Choice(int id, int dialogBoxId, String content, int pChoiceId, int pDialogBoxId, int successDialogBoxId, int failDialogBoxId, boolean endOnSuccess, int endingScreenId) {
        this(id, dialogBoxId, content, pChoiceId, pDialogBoxId, null, 0, null, 0, failDialogBoxId, successDialogBoxId, endOnSuccess, endingScreenId);
    }
    protected Choice(int id, int dialogBoxId, String content, int pChoiceId, int pDialogBoxId, String stat, int statVal, int successDialogBoxId, int failDialogBoxId) {
        this(id, dialogBoxId, content, pChoiceId, pDialogBoxId, stat, statVal, null, 0, failDialogBoxId, successDialogBoxId, false, 0);
    }
    protected Choice(int id, int dialogBoxId, String content, int pChoiceId, int pDialogBoxId, String stat, int statVal, int successDialogBoxId, int failDialogBoxId,  boolean endOnSuccess, int endingScreenId) {
        this(id, dialogBoxId, content, pChoiceId, pDialogBoxId, stat, statVal, null, 0, failDialogBoxId, successDialogBoxId, endOnSuccess, endingScreenId);
    }
    protected Choice(int id, int dialogBoxId, String content, int pChoiceId, int pDialogBoxId, String stat, int statVal, String rewardStat, int rewardVal, int successDialogBoxId, int failDialogBoxId) {
        this(id, dialogBoxId, content, pChoiceId, pDialogBoxId, stat, statVal, rewardStat, rewardVal, failDialogBoxId, successDialogBoxId, false, 0);
    }
    protected Choice(int id, int dialogBoxId, String content, String stat, int statVal, int successDialogBoxId, int failDialogBoxId) {
        this(id, dialogBoxId, content, 0, 0, stat, statVal, null, 0, failDialogBoxId, successDialogBoxId, false, 0);
    }
    protected Choice(int id, int dialogBoxId, String content, String stat, int statVal, int successDialogBoxId, int failDialogBoxId, boolean endOnSuccess, int endingScreenId) {
        this(id, dialogBoxId, content, 0, 0, stat, statVal, null, 0, failDialogBoxId, successDialogBoxId, endOnSuccess, endingScreenId);
    }
    protected Choice(int id, int dialogBoxId, String content, String stat, int statVal, String rewardStat, int rewardVal, int successDialogBoxId, int failDialogBoxId) {
        this(id, dialogBoxId, content, 0, 0, stat, statVal, rewardStat, rewardVal, failDialogBoxId, successDialogBoxId, false, 0);
    }
    protected Choice(int id, int dialogBoxId, String content, String stat, int statVal, String rewardStat, int rewardVal, int successDialogBoxId, int failDialogBoxId, boolean endOnSuccess, int endingScreenId) {
        this(id, dialogBoxId, content, 0, 0, stat, statVal, rewardStat, rewardVal, failDialogBoxId, successDialogBoxId, endOnSuccess, endingScreenId);
    }
    protected Choice(int id, int dialogBoxId, String content, String rewardStat, int rewardValue, int successDialogBoxId) {
        this(id, dialogBoxId, content, 0, 0, null, 0, rewardStat, rewardValue, 0, successDialogBoxId, false, 0);
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
    public int getDialogBoxId() {
        return dialogBoxId;
    }

    protected void setDialogBoxId(int dialogBoxId) {
        this.dialogBoxId = dialogBoxId;
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
    public int getSuccessDialogBoxId() {
        return successDialogBoxId;
    }

    protected void setSuccessDialogBoxId(int successDialogBoxId) {
        this.successDialogBoxId = successDialogBoxId;
    }

    /**
     * This gets used by Jackson.databind to make JSON Files.
     */
    @JsonProperty("FAIL-SCENE")
    public int getFailDialogBoxId() {
        return failDialogBoxId;
    }

    protected void setFailDialogBoxId(int failDialogBoxId) {
        this.failDialogBoxId = failDialogBoxId;
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
                ", boxId=" + dialogBoxId +
                ", content=" + super.getContent() +
                ", stat='" + stat + '\'' +
                ", statVal=" + statVal +
                ", pChoiceId=" + pChoiceId +
                ", nextScene=" + successDialogBoxId +
                '}';
    }
}