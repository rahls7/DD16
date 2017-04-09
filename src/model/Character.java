package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Silas on 2017/2/10.
 * Model level, Class of character,
 * Store the information of a character
 */
public class Character {
    private String id;
    private String name;
    private String type;
    private ArrayList<Item> equipment;
    private ArrayList<Item> backpack;
    public int[][] basicStats;
    public int[] basicAttributes;
    private boolean isSaved;
    private int[][] stats;
    private int[] attributes;

    /**
     * constructor to construct an object of character
     *
     * @param id
     */
    public Character(String id) {
        this.id = id;
        name = new String("");
        isSaved = false;
        equipment = new ArrayList<Item>();
        backpack = new ArrayList<Item>();
        stats = new int[6][2];
        basicStats = new int[6][2];
        attributes = new int[6];
        basicAttributes = new int[6];
    }

    /**
     * initiate the attributes and stats of a character
     */
    public void initiateStats(String type) {
        int dice = 0;
        int modifierValue = 0;
        Random rand = new Random();
        int[] diceStats = new int[6];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++)
                dice = dice + rand.nextInt(6) + 1;
            diceStats[i] = dice;
            dice = 0;
        }
        Arrays.sort(diceStats);
        if (type.equals("Bully")) {
            stats[0][0] = diceStats[5];
            stats[2][0] = diceStats[4];
            stats[1][0] = diceStats[3];
        } else if (type.equals("Nimble")) {
            stats[1][0] = diceStats[5];
            stats[2][0] = diceStats[4];
            stats[0][0] = diceStats[3];
        } else if (type.equals("Tank")) {
            stats[2][0] = diceStats[5];
            stats[1][0] = diceStats[4];
            stats[0][0] = diceStats[3];
        }
        stats[3][0] = diceStats[2];
        stats[5][0] = diceStats[1];
        stats[4][0] = diceStats[0];

        for (int i = 0; i < 6; i++) {
            basicStats[i][0] = stats[i][0];
            stats[i][1] = stats[i][0] / 2 - 5;
            basicStats[i][1] = stats[i][1];
        }

        //level
        attributes[0] = 1;
        basicAttributes[0] = attributes[0];
        //hitpoint
        attributes[1] = rand.nextInt(10) + 1;
        basicAttributes[1] = attributes[1];
        attributes[1] = (basicAttributes[1] + stats[2][1]) * attributes[0];
        //armor class
        attributes[2] = 10 + stats[1][1]; //+armor class bonus from items
        basicAttributes[2] = attributes[2];
        //attack bonus
        attributes[3] = attributes[0] + stats[0][1] + stats[1][1];
        basicAttributes[3] = attributes[3];
        //damage bonus
        attributes[4] = stats[0][1];
        basicAttributes[4] = attributes[4];
        //multiple attack
        if (attributes[3] > 6)
            attributes[5] = 1;
        else
            attributes[5] = 0;
        basicAttributes[5] = attributes[5];
        recalculateStats();
    }

    /**
     * everytime level or equipment is changed, recalculate the stats modifier and attributes
     * of this character
     */
    public void recalculateStats() {

        boolean rangedWeaponEquipped = false;
        boolean meleeWeaponEquipped = false;

        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 2; j++)
                stats[i][j] = basicStats[i][j];

        // first loop
        for (Item item : equipment) {
            if (item.getType().equals("Ranged Weapon"))
                rangedWeaponEquipped = true;

            if (item.getType().equals("Melee Weapon"))
                meleeWeaponEquipped = true;

            switch (item.getAttribute()) {
                case "Strength":
                    stats[0][1] = stats[0][1] + item.getAttributeValue();
                    break;
                case "Dexterity":
                    stats[1][1] = stats[1][1] + item.getAttributeValue();
                    break;
                case "Constitution":
                    stats[2][1] = stats[2][1] + item.getAttributeValue();
                    break;
                case "Intelligence":
                    stats[3][1] = stats[3][1] + item.getAttributeValue();
                    break;
                case "Wisdom":
                    stats[4][1] = stats[4][1] + item.getAttributeValue();
                    break;
                case "Charisma":
                    stats[5][1] = stats[5][1] + item.getAttributeValue();
                    break;
            }
        }

        //hitpoint
        attributes[1] = (basicAttributes[1] + stats[2][1]) * attributes[0];
        //armor class
        attributes[2] = 10 + stats[1][1];
        //attack bonus
        if (rangedWeaponEquipped)
            attributes[3] = attributes[0] + stats[1][1];
        else if (meleeWeaponEquipped)
            attributes[3] = attributes[0] + stats[0][1];
        else
            attributes[3] = attributes[0];
        //damage bonus
        attributes[4] = stats[0][1];

        //second loop
        for (Item item : equipment) {
            switch (item.getAttribute()) {
                case "Hit Point":
                    attributes[1] = attributes[1] + item.getAttributeValue();
                    break;
                case "Armor Class":
                    attributes[2] = attributes[2] + item.getAttributeValue();
                    break;
                case "Attack Bonus":
                    attributes[3] = attributes[3] + item.getAttributeValue();
                    break;
                case "Damage Bonus":
                    attributes[4] = attributes[4] + item.getAttributeValue();
                    break;
            }
            if(item.getType().equals("Ranged Weapon") || item.getType().equals("Melee Weapon")) {
                String[] parts = item.getAttribute().split(",");
                String att = parts[0];
                switch (att) {
                    case "Attack Bonus":
                        attributes[3] = attributes[3] + item.getAttributeValue();
                        break;
                    case "Damage Bonus":
                        attributes[4] = attributes[4] + item.getAttributeValue();
                        break;
                }
            }
        }

        //multiple attack
        if (attributes[3] > 6)
            attributes[5] = 1;
        else
            attributes[5] = 0;

        if (!(rangedWeaponEquipped || meleeWeaponEquipped))
            attributes[4] = 0;

    }

    /**
     * set the character is saved
     *
     * @param flag
     */
    public void setIsSaved(boolean flag) {
        this.isSaved = flag;
    }

    /**
     * get the state of character that if he is saved
     *
     * @return
     */
    public boolean getIsSaved() {
        return this.isSaved;
    }

    /**
     * get the id of character
     *
     * @return
     */
    public String getId() {
        return this.id;
    }

    /**
     * modify character's name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get character's name
     *
     * @return character's name
     */
    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    /**
     * character put on euipment
     *
     * @param equipment
     */
    public void setEquipment(Item equipment) {
        String type = new String();
        for (Item item : this.equipment) {
            if (item.getType().equals(equipment.getType()) || (item.getType().equals("Ranged Weapon") && equipment.getType().equals("Melee Weapon")) || (item.getType().equals("Melee Weapon") && equipment.getType().equals("Ranged Weapon"))) {
                this.equipment.remove(item);
                this.equipment.add(equipment);
                this.backpack.add(item);
                return;
            }
        }
        this.equipment.add(equipment);
    }

    /**
     * get all equipment dressed
     *
     * @return equipment in arraylist
     */
    public ArrayList<Item> getEquipment() {
        return this.equipment;
    }

    /**
     * take off the quipment
     *
     * @param equipment
     */
    public void deleteEquipment(Item equipment) {
        this.equipment.remove(equipment);
    }

    /**
     * take item from database to backpack
     *
     * @param backpack item to be added to backpack
     */
    public void setBackpack(Item backpack) {
        this.backpack.add(backpack);
    }

    /**
     * get all items in backpack
     *
     * @return backpack
     */
    public ArrayList<Item> getBackpack() {
        return this.backpack;
    }

    /**
     * remove item from backpack
     *
     * @param backpack all items in backpack
     */
    public void removeBackpack(Item backpack) {
        this.backpack.remove(backpack);
    }

    /**
     * set the character's stats
     *
     * @param stats abilities of character
     */
    public void setStats(int[][] stats) {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 2; j++)
                this.stats[i][j] = stats[i][j];
    }

    public void setBasicStats(int[][] basicStats) {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 2; j++)
                this.basicStats[i][j] = basicStats[i][j];
    }

    public int[][] getStats() {
        return stats;
    }

    public int[][] getBasicStats() {
        return basicStats;
    }


    /**
     * give character new attributes
     *
     * @param attributes the new attributes of character
     */
    public void setAttributes(int[] attributes) {
        for (int i = 0; i < 6; i++)
            this.attributes[i] = attributes[i];
    }

    /**
     * set the character's basic attributes
     *
     * @param basicAttributes initiated attributes of character
     */
    public void setBasicAttributes(int[] basicAttributes) {
        for (int i = 0; i < 6; i++)
            this.basicAttributes[i] = basicAttributes[i];
    }

    /**
     * get the attributes of character
     *
     * @return attributes
     */
    public int[] getAttributes() {
        return attributes;
    }

    public int[] getBasicAttributes() {
        return basicAttributes;
    }
}
