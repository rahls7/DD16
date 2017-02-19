package Controller;

import model.Item;
import model.ItemIO;
import org.json.JSONArray;

import javax.swing.*;

/**
 * Mediate interaction between views and models of items.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */
public class ItemEditorController {

    public Item item;
    public ItemIO itemio;

    /**
     * Initiate an instance of ItemEditorController.
     */
    public ItemEditorController(){
        itemio = new ItemIO();
    }

    /**
     * Save item into the file.
     *
     * @param item Item type.
     * @param attribute Item attribute.
     * @param attribute_value Value of item attribute.
     * @return True if saving successfully, otherwise false.
     */
    public boolean saveItem(String item, String attribute, int attribute_value) {
        this.item = new Item(item, attribute, attribute_value);
        return itemio.saveItem(this.item);
    }

    /**
     * Get item from item file.
     *
     * @param item_id Id of the item.
     * @return The object of the item.
     */
    public Item getItem(int item_id) {
        this.item = itemio.getItem(item_id);
        return this.item;
    }

    /**
     * Update item information in item file.
     *
     * @param item Item type.
     * @param attribute Item attribute.
     * @param attribute_value Value of item attribute.
     * @return True if updating is successfully, otherwise false.
     */
    public boolean updateItem(String item, String attribute, int attribute_value) {
        this.item.setType(item);
        this.item.setAttribute(attribute);
        this.item.setAttributeValue(attribute_value);
        return itemio.saveItem(this.item);
    }

    /**
     * Get item list from item file.
     *
     * @return List of items.
     */
    public JSONArray getItemList() {
        return itemio.getItemList();
    }
}
