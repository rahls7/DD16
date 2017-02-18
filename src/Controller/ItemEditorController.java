package Controller;

import model.Item;
import model.ItemIO;
import org.json.JSONArray;

import javax.swing.*;

/**
 * Created by Alleria on 2017/2/17.
 */
public class ItemEditorController {

    public Item item;
    public ItemIO itemio;

    public ItemEditorController(){
        itemio = new ItemIO();
    }

    public boolean saveItem(String item, String attribute, int attribute_value) {
        this.item = new Item(item, attribute, attribute_value);
        return itemio.saveItem(this.item);
    }

    public Item getItem(int item_id) {
        this.item = itemio.getItem(item_id);
        return this.item;
    }

    public boolean updateItem(String item, String attribute, int attribute_value) {
        this.item.setType(item);
        this.item.setAttribute(attribute);
        this.item.setAttributeValue(attribute_value);
        return itemio.saveItem(this.item);
    }

    public JSONArray getItemList() {
        return itemio.getItemList();
    }
}
