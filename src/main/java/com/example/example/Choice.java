package com.example.example;

import javafx.scene.paint.Color;

public class Choice {
    
    /**
      * Denne klassen skal håndtere alt som har med dialog og de textbaserte valgene du kan velge.
      */

    Choice() {

    }

    public void createDialog(int id, String content, Color color) {
        /**
          * Lager en dialog
          * @param id er id'en til denne dialogen
          * @param content er teksten til denne dialogen feks: "Adventurer i need help!"
          * @param color er styling valg slik at du kan velge farge på dialogen.
          */
    }

    public void addOption(int id, int boxId, String content, Color color, int nextScene) {
        /**
          * Lager et svarsmultighet til dialogen den er knyttet til.
          * @param id er id'en til dette valget
          * @param boxId er id'en til dialogen den er knyttet til
          * @param content er teksten til denne dialogen feks: "i will help you inkeeper!"
          * @param color er styling valg slik at du kan velge farge på valget
          * @param nextScene er id'en til den neste dialogen hvis du velger dette valget.
          */
    }

    public void addOptionPrevious(int id, int boxId, int pChoiceId, String content, Color color, int nextScene) {
        /**
          * Lager et svarsmultighet til dialogen den er knyttet til, men denne har en "prerequisite" som vil si at du må velge A for å svare B
          * @param id er id'en til dette valget
          * @param boxId er id'en til dialogen den er knyttet til
          * @param pChoiceId er id'en tildet forrige valget som du måtte velge for å gjøre dette valget, feks: du måtte ha valgt 1, for å velge valg 30
          * @param content er teksten til denne dialogen feks: "i will help you inkeeper!"
          * @param color er styling valg slik at du kan velge farge på valget
          * @param nextScene er id'en til den neste dialogen hvis du velger dette valget.
          */
    }

    public void addOptionReq(int id, int boxId, String content, Color color, int nextScene, String stat, int statVal) {
        /**
          * Lager et svarsmultighet til dialogen den er knyttet til, men denne har en stat check, så du må ha mer enn statVal for å passere
          * @param id er id'en til dette valget
          * @param boxId er id'en til dialogen den er knyttet til
          * @param content er teksten til denne dialogen feks: "i will help you inkeeper!"
          * @param color er styling valg slik at du kan velge farge på valget
          * @param nextScene er id'en til den neste dialogen hvis du velger dette valget.
          * @param stat er statten som skal bli skjekket.
          * @param statVal er verdien til statten.
          */
    }

    public void addOptionReward(int id, int boxId, String content, Color color, int nextScene, String stat, int value) {
        /**
          * Lager et svarsmultighet til dialogen den er knyttet til, men denne gir deg en gave hvis du velger den.
          * @param id er id'en til dette valget
          * @param boxId er id'en til dialogen den er knyttet til
          * @param content er teksten til denne dialogen feks: "i will help you inkeeper!"
          * @param color er styling valg slik at du kan velge farge på valget
          * @param nextScene er id'en til den neste dialogen hvis du velger dette valget.
          * @param stat er statten som skal bli skjekket.
          * @param value er verdien statten skal øke med.
          */
    }
}
