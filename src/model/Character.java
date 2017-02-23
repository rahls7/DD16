package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Silas on 2017/2/10.
 */
public class Character {
    private String name;
    private ArrayList<String> equipment;
    private ArrayList<String> backpack;
    private ArrayList<String> item;
    private int[][] stats;
    private int[] attributes;

    public Character(String name) {
        this.name = new String(name);
        equipment = new ArrayList<String>();
        backpack = new ArrayList<String>();
        item = new ArrayList<String>();
        stats = new int[7][2];
        attributes = new int[6];
        initiateStats();
        backpack.add("Helmet:gold");
        equipment.add("Helmet");
        item.add("Helmet:shit");
    }

    private void initiateStats() {
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
            stats[i][1] = modifierValue;
            dice = 0;
            modifierValue = 0;
        }

        attributes[0] = 1;
    }

    //Name
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //Equipment
    public void setEquipment(String equipment) {
        this.equipment.add(equipment);
    }

    public ArrayList<String> getEquipment() {
        return this.equipment;
    }

    public void deleteEquipment(String equipment) {
        this.equipment.remove(equipment);
    }

    //Backpack
    public void setBackpack(String backpack) {
        this.backpack.add(backpack);
    }

    public ArrayList<String> getBackpack() {
        return this.backpack;
    }

    public void removeBackpack(String backpack) {
        this.backpack.remove(backpack);
    }

    //Item
    public void setItem(ArrayList<String> item) {
        this.item = (ArrayList) item.clone();
    }

    //Stats
    public void setStats() {

    }

    public int[][] getStats() {
        return stats;
    }

    //Atributes
    public void setAttributes() {

    }

    public int[] getAttributes() {
        return attributes;
    }
}
