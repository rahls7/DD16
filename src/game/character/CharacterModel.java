package build1.character;

import java.util.ArrayList;

/**
 * Created by Silas on 2017/2/10.
 */
public class CharacterModel {
    private String name;
    private ArrayList<String> equipment;
    private ArrayList<String> backpack;
    private ArrayList<String> item;
    private int[] stats;
    private int[] attributes;

    CharacterModel(){
        equipment = new ArrayList<String>();
        backpack = new ArrayList<String>();
        item = new ArrayList<String>();
        name = new String();
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    //Equipment
    public void setEquipment(String equipment){
        this.equipment.add(equipment);
    }

    public ArrayList<String> getEquipment(){
        return this.equipment;
    }

    public void deleteEquipment(String equipment){
        this.equipment.remove(equipment);
    }

    //Backpack
    public void setBackpack(String backpack){
        this.backpack.add(backpack);
    }

    public ArrayList<String> getBackpack(){
        return this.backpack;
    }

    public void removeBackpack(String backpack){
        this.backpack.remove(backpack);
    }

    //Item
    public void setItem(ArrayList<String> item){
        this.item = (ArrayList) item.clone();
    }




}
