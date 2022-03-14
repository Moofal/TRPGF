package com.example.example.dialog;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Choice extends Dialog{

    private int boxId;
    private int nextScene;

    Choice() {

    }

    public Choice(int id, String content, Color color, int boxId, int nextScene) {
        super(id, content, color);
        this.boxId = boxId;
        this.nextScene = nextScene;
    }

    @Override
    public String toString() {
        return "{ " + super.getId() +
                ", " + boxId +
                ", " + super.getContent() +
                ", Red:" + super.getColor().getRed() + " Green:" + super.getColor().getGreen() + " Blue:" + super.getColor().getBlue() +
                ", " + nextScene +
                "}";
    }
}