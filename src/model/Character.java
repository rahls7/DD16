package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Silas on 2017/2/10.
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

    public Character(String id) {
        this.id = id;
        name= new String("");
        isSaved = false;
        equipment = new ArrayList<Item>();
        backpack = new ArrayList<Item>();
        stats = new int[7][2];
        basicStats = new int[7][2];
        attributes = new int[6];
        basicAttributes = new int[6];
    }

    public void initiateStats() {
        int dice = 0;
        int modifierValue = 0;
        Random rand = new Random();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                // StatsValue
                for (int k = 0; k < 4; k++) dice = dice + rand.nextInt(6) + 1;
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
        attributes[1] = ((rand.nextInt(10)+1) + stats[3][1]) * attributes[0];
        basicAttributes[1] = attributes[1];
        //armor class
        attributes[2] = 10 + stats[2][1]; //+armor class bonus from items
        basicAttributes[2] = attributes[2];
        //attack bonus
        attributes[3] = attributes[0];
        basicAttributes[3] = attributes[3];
        //damage bonus
        attributes[4] = stats[1][1];
        basicAttributes[4] = attributes[4];
        //multiple attack
        if (attributes[3] > 6)
            attributes[5] = 1;
        else
            attributes[5]=0;
        basicAttributes[5] = attributes[5];
    }

    //Recalculate Stats
    public void recalculateStats(){

        basicAttributes[3] = attributes[0];
        for (int i=0; i<7; i++)
            for (int j=0; j<1; j++)
                stats[i][j] = basicStats[i][j];
        for (int i=1; i<5; i++)
            attributes[i] = basicAttributes[i];
        for (Item item: equipment){
            switch (item.getAttribute()){
                case "Ability": stats[0][0] = basicStats[0][0] + item.getAttributeValue();
                                break;
                case "Strength": stats[1][0] = basicStats[1][0] + item.getAttributeValue();
                                break;
                case "Dexterity": stats[2][0] = basicStats[2][0] + item.getAttributeValue();
                                break;
                case "Constitution": stats[3][0] = basicStats[3][0] + item.getAttributeValue();
                                break;
                case "Intelligence": stats[4][0] = basicStats[4][0] + item.getAttributeValue();
                                break;
                case "Wisdom": stats[5][0] = basicStats[5][0] + item.getAttributeValue();
                                break;
                case "Charisma": stats[6][0] = basicStats[6][0] + item.getAttributeValue();
                                break;
                case "Hit Point": attributes[1] = basicAttributes[1] + item.getAttributeValue();
                                break;
                case "Armor Class": attributes[2] = basicAttributes[2] + item.getAttributeValue();
                                break;
                case "Attack Bonus": attributes[3] = basicAttributes[3] + item.getAttributeValue();
                                break;
                case "Damage Bonus": attributes[4] = basicAttributes[4] + item.getAttributeValue();
                                break;
            }

            for (int i = 0; i < 7; i++) {
                //ModifierValue
                stats[i][1] = stats[i][0] / 2 - 5;
            }

        }
        if (attributes[3] > 6)
            attributes[5] = 1;
        else
            attributes[5]=0;

    }

    //isSaved
    public void setIsSaved(boolean flag){
        this.isSaved = flag;
    }

    public boolean getIsSaved(){
        return this.isSaved;
    }

    //id
    public String getId(){return this.id;}

    //Name
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //Equipment
    public void setEquipment(Item equipment) {
        this.equipment.add(equipment);
    }

    public ArrayList<Item> getEquipment() {
        return this.equipment;
    }

    public void deleteEquipment(Item equipment) {
        this.equipment.remove(equipment);
    }

    //Backpack
    public void setBackpack(Item backpack) {
        this.backpack.add(backpack);
    }

    public ArrayList<Item> getBackpack() {
        return this.backpack;
    }

    public void removeBackpack(Item backpack) {
        this.backpack.remove(backpack);
    }


    //Stats
    public void setStats(int[][] stats) {
        for (int i=0; i<7; i++)
            for (int j=0; j<2; j++)
                this.stats[i][j] = stats[i][j];
    }

    public void setBasicStats(int[][] basicStats) {
        for (int i=0; i<7; i++)
            for (int j=0; j<2; j++)
                this.basicStats[i][j] = basicStats[i][j];
    }

    public int[][] getStats() {
        return stats;
    }

    //Attributes
    public void setAttributes(int[] attributes) {
        for (int i=0; i<6; i++)
            this.attributes[i] = attributes[i];
    }

    public void setBasicAttributes(int[] basicAttributes) {
        for (int i=0; i<6; i++)
            this.basicAttributes[i] = basicAttributes[i];
    }

    public int[] getAttributes() {
        return attributes;
    }
}
