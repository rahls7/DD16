package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import model.Character;
import model.CharacterIO;
import model.ItemIO;
import model.Item;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Silas on 2017/2/10.
 * This class is a controller that controls actions about Character.
 * It contains an object of Character model and uses awt library.
 */
public class CharacterEditorController{

    private Character characterModel;
    private ItemIO itemIO;
    private CharacterIO characterIO;
    private ArrayList<Item> items;

    /**
     *Constructor of character controller, create a controller, called by the character view
     * @param id
     */
    public CharacterEditorController(String id){
        characterModel = new Character(id);
        items = new ArrayList<Item>();
        itemIO = new ItemIO();
        characterIO = new CharacterIO();
        Item item;
        JSONArray jsonArray = itemIO.getItemList();
        int itemId=0, itemAttributeValue;
        String itemType, itemAttribute ;
        for (int i =0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            itemId = jsonObject.getInt("id");
            itemType = jsonObject.getString("type");
            itemAttribute  = jsonObject.getString("attribute");
            itemAttributeValue= jsonObject.getInt("attribute_value");
            item = new Item(itemType, itemAttribute, itemAttributeValue);
            item.setIsSaved(true);
            item.setSaveId(itemId);
            items.add(item);
        }
    }

    /**
     * recalculate the attributes and stats of Character when euipments or levels are changed
     */
    public void recalculate(){
        characterModel.recalculateStats();
    }

    /**
     * get a Character from file by its id
     * @param id
     */
    public void getCharacter(String id){
        characterModel = characterIO.getCharacter(id);
    }

    //saveCharacter
    public void saveCharacter(){
        characterIO.saveCharacter(characterModel);
    }

    //initiate
    public void initiateStats(){characterModel.initiateStats();}

    //name
    public void setName(String name){characterModel.setName(name);}

    public String getName(){return characterModel.getName();}

    //Equipment
    public void setEquipment(int equipment) {
        characterModel.setEquipment(characterModel.getBackpack().get(equipment));
        characterModel.recalculateStats();
    }

    public ArrayList<String> getEquipment() {
        return itemToString(characterModel.getEquipment());
    }

    public void deleteEquipment(int equipment) {
        characterModel.deleteEquipment(characterModel.getEquipment().get(equipment));
        characterModel.recalculateStats();
    }

    //Backpack
    public void setBackpack(int backpack) {
        characterModel.setBackpack(items.get(backpack));
    }

    public void setEquipmentBackpack(int equipmentBackpack){
        characterModel.setBackpack(characterModel.getEquipment().get(equipmentBackpack));
    }

    public ArrayList<String > getBackpack() {
        return itemToString(characterModel.getBackpack());
    }

    public void removeBackpack(int backpack) {
        characterModel.removeBackpack(characterModel.getBackpack().get(backpack));
    }

    // Items
    public ArrayList<String> getItem(){

        return itemToString(items);
    }

    //Stats
    public void setStats(int[][] stats) {
        characterModel.setStats(stats);
    }

    public int[][] getStats() {
        return characterModel.getStats();
    }

    //Atributes
    public void setAttributes(int[] attributes) {
        characterModel.setAttributes(attributes);
    }

    public int[] getAttributes() {
        return characterModel.getAttributes();
    }

    //items to string
    public ArrayList<String> itemToString(ArrayList<Item> items){
        ArrayList<String> strings = new ArrayList<String>();
        String s;
        for (Item item: items){
            s = item.getType()+":"+item.getAttribute()+"+"+item.getAttributeValue();
            strings.add(s);
        }
        return strings;
    }
}