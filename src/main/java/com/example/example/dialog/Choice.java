package com.example.example.dialog;
public class Choice extends Dialog{

    /**
     * Denne klassen har choice konstruktøren som trengs til Dialog, og den Extender Dialog.
     */

    private int boxId;
    private int nextScene;
    private int pChoiceId;
    private String stat;
    private int statVal;
    private int value;

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

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    public int getNextScene() {
        return nextScene;
    }

    public void setNextScene(int nextScene) {
        this.nextScene = nextScene;
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
                ", value=" + value +
                ", pChoiceId=" + pChoiceId +
                ", nextScene=" + nextScene +
                '}';
    }
}