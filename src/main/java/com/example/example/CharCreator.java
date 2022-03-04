package com.example.example;

public class CharCreator {

    /**
     * Denne klassen lar deg bestemme hva som kommer til å være på character creation screenen.
     */

    CharCreator() {

    }

    public void addNameOption (String name) {
        /**
         * Lar spilleren velge et navn til karakteren sin.
         */
    }

    public void addStat (String statName, int minValue, int maxValue) {
        /**
         * Lager en stat som karakteren får.
         * @param statName er navnet på statten.
         * @param minValue er den minste verdien staten kan ha.
         * @param maxValue er den største verdien staten kan ha.
         */
    }

    public  void setStat (String statName, int value) {
        /**
         * Lar deg bestemme verdien til en stat du har allerede laget.
         * @param statName er navnet til staten du setter.
         * @param value er verdien du setter staten til.
         */
    }

    public void setStatGenerationDice (String statName, int numOfDice, int diceSides) {
        /**
         * Her bestemmer du at vardien til en bestemt stat skal genereres via terninger.
         * @param statName er navnet til staten som blir brukt.
         * @param numOfDice er hvor mange terninger som skal bli brukt.
         * @param diceSides er hovr mange sider som terningene skal ha.
         */
    }

    public void setStatGenerationManual (String statName) {
        /**
         * Her bestemmer du at en bestemt stat skal bli bestemmet av spilleren selv.
         * @param statName er hvilken stat dette gjelller.
         */
    }

    public void addAttribute(String attributeName, int startingValue) {
        /**
         * Legger til en atribute til karakteren.
         * Atributter er skjulte verdier som spilleren ikke kan se men som du kan manipulere.
         * @param attributeName navnet på atributten.
         * @param startingValue verdier som atributten starter som.
         */
    }
}
