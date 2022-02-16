package com.example.example;

import javafx.scene.paint.Color;

public class Choice {
    // nei men skjekker med discord strx, skjekk med folka dr
    Choice() {

    }

    public void createChoices(int id, String content, Color color) {
        System.out.println("createChoices works!");
    }

    public void addOption(int id, int boxId, String content, Color color, int nextScene) {
        System.out.println("addOption works!");
    }

    public void addOptionPrevious(int id, int boxId, int pChoiceId, String content, Color color, int nextScene) {
        System.out.println("addOptionPrevious works!");
    }

    public void addOptionReq(int id, int boxId, String content, Color color, int nextScene, String stat, int statVal) {
        System.out.println("addOptionReq works!");
    }

    //Bytte om funksjon navn ettervert -Rob
    public void addOptionReward(int id, int boxId, String content, Color color, int nextScene, String stat, int value) {
        System.out.println("addOptionReward works!");
    }

    public void addOptionStatCheck(int id, int boxId, String content, Color color, int nextScene, String stat, int value) {
        System.out.println("addOptionStatCheck works!");
    }
}
