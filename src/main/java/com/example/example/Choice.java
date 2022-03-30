package com.example.example;

import com.example.example.Dialog;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Choice extends Dialog {

    /**
     * Denne klassen har choice konstruktøren som trengs til Dialog, og den Extender Dialog.
     */

    private int boxId;
    private int nextScene;
    private int pChoiceId;
    private String stat;
    private int statVal;

    Choice() {
    }

    public Choice(int id, String content, int boxId, int nextDialog) {
        super(id, content);
        this.boxId = boxId;
        this.nextScene = nextDialog;
    }

    public Choice(int id, String content, int boxId, int nextScene, int pChoiceId) {
        super(id, content);
        this.boxId = boxId;
        this.nextScene = nextScene;
        this.pChoiceId = pChoiceId;
    }

    public Choice(int id, String content, int boxId, int nextScene, String stat, int statVal) {
        super(id, content);
        this.boxId = boxId;
        this.nextScene = nextScene;
        this.stat = stat;
        this.statVal = statVal;
    }

//-----------------------------------Getters and Setters-----------------------------------//

    @JsonProperty("BOXID")
    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    @JsonProperty("NEXT-SCENE")
    public int getNextScene() {
        return nextScene;
    }

    public void setNextScene(int nextScene) {
        this.nextScene = nextScene;
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

    @JsonProperty("STAT-VAL")
    public int getStatVal() {
        return statVal;
    }

    public void setStatVal(int statVal) {
        this.statVal = statVal;
    }

    @JsonIgnore @Override
    public ArrayList<Choice> getDialogChoiceList() {
        return super.getDialogChoiceList();
    }

    //-----------------------------------To String-----------------------------------//


    @Override
    public String toString() {
        return "{" +
                " id=" + super.getId() +
                ", boxId=" + boxId +
                ", content=" + super.getContent() +
                ", stat='" + stat + '\'' +
                ", statVal=" + statVal +
                ", pChoiceId=" + pChoiceId +
                ", nextScene=" + nextScene +
                '}';
    }
}