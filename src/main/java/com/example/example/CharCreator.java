package com.example.example;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Denne klassen lar deg bestemme hva som kommer til å være på character creation screenen.
 */
public class CharCreator {

    public CharCreator() {

    }

    private static class Stat {
        String name;
        String generationType;
        int value;
        int minValue;
        int maxValue;

        public Stat(String name, int minValue, int maxValue) {
            this.name = name;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        public void setGenerationType(String generationType) {
            this.generationType = generationType;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getMinValue() {
            return minValue;
        }

        public int getMaxValue() {
            return maxValue;
        }
    }

    private static class Character {
        String name;
        boolean nameOption;
        ArrayList <Stat> stats = new ArrayList<>();
        HashMap<String, Integer> attributes = new HashMap<>();

        public Character() {
            this.nameOption = false;
        }

        public void setNameOption(boolean nameOption) {
            this.nameOption = nameOption;
        }

        public void addStat (Stat stat) {
            stats.add(stat);
        }

        public void addAttribute(String attributeName, Integer startingValue) {
            attributes.put(attributeName, startingValue);
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Stat> getStats() {
            return stats;
        }

        public HashMap<String, Integer> getAttributes() {
            return attributes;
        }
    }

    Character character = new Character();

    /**
     * Lar spilleren velge et navn til karakteren sin.
     */
    public void addNameOption () {
        character.setNameOption(true);
    }

    public void setName (String name) {
        character.setName(name);
    }

    /**
     * Lager en stat som karakteren får.
     * @param statName er navnet på statten.
     * @param minValue er den minste verdien staten kan ha.
     * @param maxValue er den største verdien staten kan ha.
     */
    public void addStat (String statName, int minValue, int maxValue) {
        character.addStat(new Stat(statName, minValue, maxValue));
    }

    /**
     * Lar deg bestemme verdien til en stat du har allerede laget.
     * @param statName er navnet til staten du setter.
     * @param value er verdien du setter staten til.
     */
    public  void setStat (String statName, int value) {
        for (Stat stat: character.getStats()) {
            if (Objects.equals(stat.name, statName)) {
                stat.setValue(value);
            }
        }
    }

    /**
     * Her bestemmer du at vardien til en bestemt stat skal genereres via terninger.
     * @param statName er navnet til staten som blir brukt.
     * @param numOfDice er hvor mange terninger som skal bli brukt.
     * @param diceSides er hovr mange sider som terningene skal ha.
     */
    public void setStatGenerationDice (String statName, int numOfDice, int diceSides) {
        for (Stat stat: character.getStats()) {
            if (Objects.equals(stat.name, statName)) {
                stat.setGenerationType(numOfDice+"d"+diceSides);
            }
        }
    }

    /**
     * Her bestemmer du at en bestemt stat skal bli bestemmet av spilleren selv.
     * @param statName er hvilken stat dette gjelller.
     */
    public void setStatGenerationManual (String statName) {
        for (Stat stat: character.getStats()) {
            if (Objects.equals(stat.name, statName)) {
                stat.setGenerationType("Manual");
            }
        }
    }

    /**
     * Legger til en atribute til karakteren.
     * Atributter er skjulte verdier som spilleren ikke kan se men som du kan manipulere.
     * @param attributeName navnet på atributten.
     * @param startingValue verdier som atributten starter som.
     */
    public void addAttribute(String attributeName, int startingValue) {
        character.addAttribute(attributeName, startingValue);
    }

    public void finishCharacter() throws IOException {
        JSONArray stats = new JSONArray();

        for (Stat stat: character.getStats()) {
            JSONObject jsonStat = new JSONObject();
            jsonStat.put("Max Value",stat.getMaxValue());
            jsonStat.put("Min Value",stat.getMinValue());
            jsonStat.put("Generation Type", stat.generationType);
            jsonStat.put("Value", stat.value);
            jsonStat.put("Name", stat.name);
            stats.put(jsonStat);
        }

        writeCharacterCreationToJSON(stats);
        writeCharacterToJSON(stats);
    }

    private void writeCharacterCreationToJSON(JSONArray stats) throws IOException {
        JSONObject options = new JSONObject();
        options.put("Stats", stats);
        options.put("Name Option", character.nameOption);
        FileWriter charCreationSettingsFile = new FileWriter("src/CharCreatSettings.json");
        charCreationSettingsFile.write(options.toString());
        charCreationSettingsFile.close();
    }

    private void writeCharacterToJSON(JSONArray stats) throws IOException {
        JSONObject character = new JSONObject();
        character.put("Name", "");
        character.put("Stats", stats);

        FileWriter characterFile = new FileWriter("src/Character.json");
        characterFile.write(character.toString());
        characterFile.close();
    }
}