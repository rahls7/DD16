package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Silas on 2017/2/10.
 * Model level, Class of character,
 * Store the information of a character
 */
public class Character {
    private String id;
    private String name;
    private ArrayList<Item> equipment;
    private ArrayList<Item> backpack;
    public int[][] basicStats;
    public int[] basicAttributes;
    private boolean isSaved;
    private int[][] stats;
    private int[] attributes;

    /**
     * constructor to construct an object of character
     * @param id
     */
    public Character(String id) {
        this.id = id;
        name= new String("");
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
    public void initiateStats() {
        int dice = 0;
        int modifierValue = 0;
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            // StatsValue
            for (int k = 0; k < 4; k++) dice = dice + rand.nextInt(6) + 1;
            for (int j = 0; j < 2; j++) {
                //ModifierValue
                modifierValue = dice / 2 - 5;
            }
            stats[i][0] = dice;
            basicStats[i][0] = stats[i][0];
            stats[i][1] = modifierValue;
            basicStats[i][1] = stats[i][1];
            dice = 0;
            modifierValue = 0;
        }
        //level
        attributes[0] = 1;
        basicAttributes[0] = attributes[0];
        //hitpoint
        attributes[1] = rand.nextInt(10)+1;
        basicAttributes[1] = attributes[1];
        attributes[1] = (basicAttributes[1] +  stats[2][1]) * attributes[0];
        //armor class
        attributes[2] = 10 + stats[1][1]; //+armor class bonus from items
        basicAttributes[2] = attributes[2];
        //attack bonus
        attributes[3] = attributes[0];
        basicAttributes[3] = attributes[3];
        //damage bonus
        attributes[4] = stats[0][1];
        basicAttributes[4] = attributes[4];
        //multiple attack
        if (attributes[3] > 6)
            attributes[5] = 1;
        else
            attributes[5]=0;
        basicAttributes[5] = attributes[5];
    }

    /**
     * everytime level or equipment is changed, recalculate the stats modifier and attributes
     * of this character
     */
    public void recalculateStats(){

        for (int i=0; i<6; i++)
            for (int j=0; j<2; j++)
                stats[i][j] = basicStats[i][j];

        // first loop
        for (Item item: equipment){
            switch (item.getAttribute()){
                case "Strength": stats[0][1] = stats[0][1] + item.getAttributeValue();
                    break;
                case "Dexterity": stats[1][1] = stats[1][1] + item.getAttributeValue();
                    break;
                case "Constitution": stats[2][1] = stats[2][1] + item.getAttributeValue();
                    break;
                case "Intelligence": stats[3][1] = stats[3][1] + item.getAttributeValue();
                    break;
                case "Wisdom": stats[4][1] = stats[4][1] + item.getAttributeValue();
                    break;
                case "Charisma": stats[5][1] = stats[5][1] + item.getAttributeValue();
                    break;
            }
        }

        //hitpoint
        attributes[1] = (basicAttributes[1] +  stats[2][1]) * attributes[0];
        //armor class
        attributes[2] = 10 + stats[1][1];
        //attack bonus
        attributes[3] = attributes[0];
        //damage bonus
        attributes[4] = stats[0][1];

        //second loop
        for (Item item : equipment){
            switch (item.getAttribute()){
                case "Hit Point": attributes[1] = attributes[1] + item.getAttributeValue();
                    break;
                case "Armor Class": attributes[2] = attributes[2] + item.getAttributeValue();
                    break;
                case "Attack Bonus": attributes[3] = attributes[3] + item.getAttributeValue();
                    break;
                case "Damage Bonus": attributes[4] = attributes[4] + item.getAttributeValue();
                    break;
            }
        }

        //multiple attack
        if (attributes[3] > 6)
            attributes[5] = 1;
        else
            attributes[5]=0;

    }

    /**
     * set the character is saved
     * @param flag
     */
    public void setIsSaved(boolean flag){
        this.isSaved = flag;
    }

    /**
     * get the state of character that if he is saved
     * @return
     */
    public boolean getIsSaved(){
        return this.isSaved;
    }

    /**
     * get the id of character
     * @return
     */
    public String getId(){return this.id;}

    /**
     * modify character's name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get character's name
     * @return character's name
     */
    public String getName() {
        return name;
    }

    /**
     * character put on euipment
     * @param equipment
     */
    public void setEquipment(Item equipment) {
        String type = new String();
        for (Item item: this.equipment){
            if (item.getType().equals(equipment.getType())) {
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
     * @return equipment in arraylist
     */
    public ArrayList<Item> getEquipment() {
        return this.equipment;
    }

    /**
     * take off the quipment
     * @param equipment
     */
    public void deleteEquipment(Item equipment) {
        this.equipment.remove(equipment);
    }

    /**
     * take item from database to backpack
     * @param backpack item to be added to backpack
     */
    public void setBackpack(Item backpack) {
        this.backpack.add(backpack);
    }

    /**
     * get all items in backpack
     * @return backpack
     */
    public ArrayList<Item> getBackpack() {
        return this.backpack;
    }

    /**
     * remove item from backpack
     * @param backpack all items in backpack
     */
    public void removeBackpack(Item backpack) {
        this.backpack.remove(backpack);
    }

    /**
     * set the character's stats
     * @param stats abilities of character
     */
    public void setStats(int[][] stats) {
        for (int i=0; i<6; i++)
            for (int j=0; j<2; j++)
                this.stats[i][j] = stats[i][j];
    }

    public void setBasicStats(int[][] basicStats) {
        for (int i=0; i<6; i++)
            for (int j=0; j<2; j++)
                this.basicStats[i][j] = basicStats[i][j];
    }

    public int[][] getStats() {
        return stats;
    }

    /** give character new attributes
     * @param attributes the new attributes of character
     */
    public void setAttributes(int[] attributes) {
        for (int i=0; i<6; i++)
            this.attributes[i] = attributes[i];
    }

    /**
     * set the character's basic attributes
     * @param basicAttributes initiated attributes of character
     */
    public void setBasicAttributes(int[] basicAttributes) {
        for (int i=0; i<6; i++)
            this.basicAttributes[i] = basicAttributes[i];
    }

    /**
     * get the attributes of character
     * @return attributes
     */
    public int[] getAttributes() {
        return attributes;
    }
}
