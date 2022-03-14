package com.example.example.dialog;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class DialogWrapper extends Dialog{

    private ArrayList<Dialog> dialogArrayList = new ArrayList<>();

    public DialogWrapper() {

    }

    public void createDialog(int id, String content, Color color) {
        Dialog dialog = new Dialog(id, content, color);
        dialogArrayList.add(dialog);
        System.out.println(getDialogArrayList());
    }

    public ArrayList<Dialog> getDialogArrayList() {
        return new ArrayList<Dialog>(dialogArrayList);
    }

    public void setDialogArrayList(ArrayList<Dialog> dialogArrayList) {
        this.dialogArrayList = dialogArrayList;
    }
}
