package Controller;

import model.Item;
import model.ItemIO;
/**
 * Created by Alleria on 2017/2/17.
 */
public class ItemEditorController {

    public Item item;
    public ItemIO itemio;

    public ItemEditorController(){
        itemio = new ItemIO();
    }

    public boolean saveMap(String item, String attribute, int attribute_value) {
        this.item = new Item(item, attribute, attribute_value);
        return itemio.saveItem(this.item);
    }
}
