package org.TRPGF;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class lets you decide on what will be on the character creator screen
 * and what stats / attributes the character wil have and how the values are decided.
 */
public class CharacterCreator {

    /**
     * This class lets you decide on what will be on the character creator screen
     * and what stats / attributes the character wil have and how the values are decided.
     */
    public CharacterCreator() {

    }

    private static class Stat {
        String name;
        String generationType;
        int value;
        int minValue;
        int maxValue;
        int numberOfDice;
        int valueOfDice;

        public Stat(String name, int minValue, int maxValue) {
            this.name = name;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        public void setDice (int numDice, int valDice) {
            this.numberOfDice = numDice;
            this.valueOfDice = valDice;
        }

        public void setGenerationType(String generationType) {
            this.generationType = generationType;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getDice() {
            return numberOfDice+"d"+valueOfDice;
        }

        public int getValue() {
            return value;
        }

        public String getGenerationType() {
            return generationType;
        }

        public String getName() {
            return name;
        }

        public int getMinValue() {
            return minValue;
        }

        public int getMaxValue() {
            return maxValue;
        }
    }

    private static class Attribute  {
        String name;
        int value;

        public Attribute(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }

    private static class Character {
        String name;
        boolean nameOption;
        ArrayList <Stat> stats = new ArrayList<>();
        ArrayList<Attribute> attributes = new ArrayList<>();

        public Character() {
            this.nameOption = false;
        }

        public void setNameOption(boolean nameOption) {
            this.nameOption = nameOption;
        }

        public void addStat (Stat stat) {
            stats.add(stat);
        }

        public void addAttribute(String attributeName, int startingValue) {
            Attribute attribute = new Attribute(attributeName,startingValue);
            attributes.add(attribute);
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Stat> getStats() {
            return stats;
        }

        public ArrayList<Attribute> getAttributes() {
            return attributes;
        }
    }

    Character character = new Character();

    /**
     * Adding the option for the player to choose a name
     */
    public void addNameOption () {
        character.setNameOption(true);
    }

    /**
     * If you want to decide the name of the player character
     * Remember to not use this and addNameOption
     * @param name The name of the player character
     */
    public void setName (String name) {
        character.setName(name);
    }

    public String getName () {
        return character.name;
    }

    /**
     * Creates a stat for the character
     * @param statName is the name of the stat.
     * @param minValue is the lowest value the stat can have.
     * @param maxValue is the highest value the stat can have.
     */
    public void addStat (String statName, int minValue, int maxValue) {
        character.addStat(new Stat(statName, minValue, maxValue));
    }

    /**
     * Lets you deice the value of a stat you have already crated.
     * @param statName is the name of the stat you are deciding value of.
     * @param value is the value you are assigning it.
     */
    public  void setStat (String statName, int value) {
        for (Stat stat: character.getStats()) {
            if (Objects.equals(stat.getName(), statName)) {
                stat.setGenerationType("Set");
                stat.setValue(value);
            }
        }
    }

    /**
     * Set the stat to have its value generated with dice
     * @param statName is the name of the stat.
     * @param numOfDice how many dice are used to generate the value.
     * @param diceSides how many sides does the dice have.
     */
    public void setStatGenerationDice (String statName, int numOfDice, int diceSides) {
        for (Stat stat: character.getStats()) {
            if (Objects.equals(stat.getName(), statName)) {
                stat.setGenerationType("Dice");
                stat.setDice(numOfDice, diceSides);
            }
        }
    }

    /**
     * Decide that the value of a stat is to be set by the player
     * @param statName is the name of the stat.
     */
    public void setStatGenerationManual (String statName) {
        for (Stat stat: character.getStats()) {
            if (Objects.equals(stat.getName(), statName)) {
                stat.setGenerationType("Manual");
            }
        }
    }

    /**
     * Add A attribute to the character.
     * Attribute are hidden values that the players can not use, but you can manipulate
     * @param attributeName the name of the attribute.
     * @param startingValue the value it starts with.
     */
    public void addAttribute(String attributeName, int startingValue) {
        character.addAttribute(attributeName, startingValue);
    }

    /**
     * Write this function when you are done with the character creation.
     * Must be written bellow the other functions.
     * Saves all the options to the file that the screen reads from.
     */
    public void finishCharacter() {
        JSONArray stats = new JSONArray();
        JSONArray attributes = new JSONArray();

        for (Stat stat: character.getStats()) {
            JSONObject jsonStat = new JSONObject();
            jsonStat.put("Max Value",stat.getMaxValue());
            jsonStat.put("Min Value",stat.getMinValue());
            jsonStat.put("Generation Type", stat.getGenerationType());
            if (Objects.equals(stat.getGenerationType(), "Dice")) {
                jsonStat.put("Dice", stat.getDice());
            }
            jsonStat.put("Value", stat.getValue());
            jsonStat.put("Name", stat.getName());
            stats.put(jsonStat);
        }

        for (Attribute attribute: character.getAttributes()) {
            JSONObject jsonAttribute = new JSONObject();
            jsonAttribute.put("Name", attribute.getName());
            jsonAttribute.put("Value", attribute.getValue());
            attributes.put(jsonAttribute);
        }

        writeCharacterCreationToJSON(stats);
        writeCharacterToJSON(stats, attributes);
    }

    private void writeCharacterCreationToJSON(JSONArray stats) {
        JSONObject options = new JSONObject();
        options.put("Stats", stats);
        options.put("Name Option", character.nameOption);
        try {
            FileWriter charCreationSettingsFile = new FileWriter("src/CharCreatSettings.json");
            charCreationSettingsFile.write(options.toString());
            charCreationSettingsFile.close();
        } catch (IOException e) {
            System.out.println("Could not find CharCreatSettings.json");
        }

    }

    private void writeCharacterToJSON(JSONArray stats, JSONArray attributes) {
        JSONObject character = new JSONObject();

        if (getName() != null) {
            character.put("Name", getName());
        } else {
            character.put("Name", "");
        }
        character.put("Stats", stats);
        character.put("Attributes", attributes);

        try {
            FileWriter characterFile = new FileWriter("src/Character.json");
            characterFile.write(character.toString());
            characterFile.close();
        } catch (IOException e) {
            System.out.println("Could not find Character.json");
        }

    }
}