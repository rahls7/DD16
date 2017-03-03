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
public class CharacterEditorController {

    private Character characterModel;
    private ItemIO itemIO;
    private CharacterIO characterIO;
    private ArrayList<Item> items;

    /**
     * Constructor of character controller, create a controller, called by the character view
     *
     * @param id ID of the character
     */
    public CharacterEditorController(String id) {
        characterModel = new Character(id);
        items = new ArrayList<Item>();
        itemIO = new ItemIO();
        characterIO = new CharacterIO();
        Item item;
        JSONArray jsonArray = itemIO.getItemList();
        int itemId = 0, itemAttributeValue;
        String itemType, itemAttribute;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            itemId = jsonObject.getInt("id");
            itemType = jsonObject.getString("type");
            itemAttribute = jsonObject.getString("attribute");
            itemAttributeValue = jsonObject.getInt("attribute_value");
            item = new Item(itemType, itemAttribute, itemAttributeValue);
            item.setIsSaved(true);
            item.setSaveId(itemId);
            items.add(item);
        }
    }

    /**
     * recalculate the attributes and stats of Character when euipments or levels are changed
     */
    public void recalculate() {
        characterModel.recalculateStats();
    }

    /**
     * get a Character from file by its id
     *
     * @param id ID of the character
     */
    public void getCharacter(String id) {
        characterModel = characterIO.getCharacter(id);
    }

    /**
     * Save this character into character file.
     */
    public void saveCharacter() {
        characterIO.saveCharacter(characterModel);
    }

    /**
     * initiate the stats and attributes of character
     */
    public void initiateStats() {
        characterModel.initiateStats();
    }

    /**
     * set the character model's name
     *
     * @param name Name of the character
     */
    public void setName(String name) {
        characterModel.setName(name);
    }

    /**
     * get the character's name from character model
     *
     * @return
     */
    public String getName() {
        return characterModel.getName();
    }

    /**
     * set the model character's euipment,
     *
     * @param equipment items dressed
     */
    public void setEquipment(int equipment) {
        characterModel.setEquipment(characterModel.getBackpack().get(equipment));
        characterModel.recalculateStats();
    }

    /**
     * get the model character's equipment
     *
     * @return euipment, model character's equipment
     */
    public ArrayList<String> getEquipment() {
        return itemToString(characterModel.getEquipment());
    }

    /**
     * delete the model's item
     *
     * @param equipment
     */
    public void deleteEquipment(int equipment) {
        characterModel.deleteEquipment(characterModel.getEquipment().get(equipment));
        characterModel.recalculateStats();
    }

    /**
     * set the model's backpack
     *
     * @param backpack backpack of the character model
     */
    public void setBackpack(int backpack) {
        if (characterModel.getBackpack().size() < 10)
            characterModel.setBackpack(items.get(backpack));
    }

    /**
     * put one item on the model's backpack
     *
     * @param equipmentBackpack
     */
    public void setEquipmentBackpack(int equipmentBackpack) {
        if (characterModel.getBackpack().size() < 10) {
            characterModel.setBackpack(characterModel.getEquipment().get(equipmentBackpack));
            deleteEquipment(equipmentBackpack);
        }
    }

    /**
     * get all items in model's backpack
     *
     * @return
     */
    public ArrayList<String> getBackpack() {
        return itemToString(characterModel.getBackpack());
    }

    /**
     * delete item from backpack
     *
     * @param backpack
     */
    public void removeBackpack(int backpack) {
        characterModel.removeBackpack(characterModel.getBackpack().get(backpack));
    }

    /**
     * get the items from database
     *
     * @return
     */
    public ArrayList<String> getItem() {

        return itemToString(items);
    }

    /**
     * set character model's stats
     *
     * @param stats
     */
    public void setStats(int[][] stats) {
        characterModel.setStats(stats);
    }

    /**
     * get character model's stats
     *
     * @return stats, its model's stats
     */
    public int[][] getStats() {
        return characterModel.getStats();
    }

    /**
     * set character model's attributes
     *
     * @param attributes
     */
    public void setAttributes(int[] attributes) {
        characterModel.setAttributes(attributes);
    }

    /**
     * get character model's attributes
     *
     * @return attributes
     */
    public int[] getAttributes() {
        return characterModel.getAttributes();
    }

    /**
     * @param items
     * @return
     */
    public ArrayList<String> itemToString(ArrayList<Item> items) {
        ArrayList<String> strings = new ArrayList<String>();
        String s;
        for (Item item : items) {
            s = item.getType() + ":" + item.getAttribute() + "+" + item.getAttributeValue();
            strings.add(s);
        }
        return strings;
    }
}