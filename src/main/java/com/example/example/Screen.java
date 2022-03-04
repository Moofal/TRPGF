package com.example.example;
import javafx.scene.paint.Color;


public class Screen {
    /**
     * Denne klassen blir brukt til å lage GUI som spilleren kommer til å bruke.
     */

    Screen() {

    }

    public void startingScreen (String gameTitle, String startingText) {
        /**
         * Dette er den første skjermen som spilleren kommer til å se.
         * @param gameTitle er navnet på spillet.
         * @param startingText er intro teksten som bli vist fram til spilleren.
         */

    }

    public void tableScreen () {
        /**
         *
         */

    }

    public void characterScreen (String displayedText) {
        /**
         * Denne skjermen lar deg lage en character creation screen.
         * @param displayedText er text som viser på denne skjermen,
         */

    }

    public void endingScreen (int endingScreenId, String sceneText, Color textColor, boolean startOver, boolean exit) {
        /**
         * Dette er slutskjermen til spillet, du kan ha flere forskjellige slutskjermer.
         * @param endingScreenId er verdien som lar deg velge å bruke denne bestemte slutskjermen.
         * @param sceneText er teksten som blir vist fram på denne skjermen.
         * @param textColor er fargen som teksten i sceneText kommer til å ha.
         * @param startOver er en boolean verdi der true betyr at starting over knappen blir vist,
         *                  den knappen lar spiller såarte spillet på nytt.
         * @param exit er en boolean verdi der true betyr at exit knappen blir vist,
         *             den knappen avslutter spillet og lukker det.
         */
    }
}
