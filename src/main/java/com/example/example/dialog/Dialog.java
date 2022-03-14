package com.example.example.dialog;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Dialog {

    private ArrayList<Choice> dialogChoiceList = new ArrayList<>();
    private int id;
    private String content;
    private Color color;

    public Dialog() {

    }

    public Dialog(int id, String content, Color color) {
        this.id = id;
        this.content = content;
        this.color = color;
    }

    public void addOption(int id, int boxId, String content, Color color, int nextScene) {
        Choice choice = new Choice(id, content, color, boxId, nextScene);
        dialogChoiceList.add(choice);
        System.out.println(getDialogChoiceList());
    }

    public void addOptionPrevious(int id, int boxId, int pChoiceId, String content, Color color, int nextScene) {
        System.out.println("addOptionPrevious works!");
    }

    public void addOptionReq(int id, int boxId, String content, Color color, int nextScene, String stat, int statVal) {
        System.out.println("addOptionReq works!");
    }

    public void addOptionReward(int id, int boxId, String content, Color color, int nextScene, String stat, int value) {
        System.out.println("addOptionReward works!");
    }

    public void addOptionStatCheck(int id, int boxId, String content, Color color, int nextScene, String stat, int value) {
        System.out.println("addOptionStatCheck works!");
    }

    //-----------------------------------Getters and Setters-----------------------------------//
    public ArrayList<Choice> getDialogChoiceList() {
        return new ArrayList<Choice>(dialogChoiceList);
    }

    public void setDialogChoiceList(ArrayList<Choice> dialogChoiceList) {
        this.dialogChoiceList = dialogChoiceList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    //-----------------------------------To String-----------------------------------//

    @Override
    public String toString() {
        return "Dialog{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", color=" + color +
                ", dialogChoiceList=" + dialogChoiceList +
                '}';
    }
}
