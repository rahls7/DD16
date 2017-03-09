package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Read and write the item file.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */
public class ItemIO {

    /**
     * Save item to the file.
     *
     * @param item Item to be saved.
     * @return True if saving successfully, otherwise false.
     */
    public boolean saveItem(Item item) {
        int id = 0;
        String content = readItemFile();
        JSONObject json_content = new JSONObject(content);
        JSONArray json_items = json_content.getJSONArray("items");
        if (!item.getIsSaved()) {
            for (int i = 0; i < json_items.length(); i++) {
                int item_id = json_items.getJSONObject(i).getInt("id");
                System.out.println(i + ": " + json_items.getJSONObject(i).getInt("id"));
                if (id < item_id)
                    id = item_id;
            }
            id += 1;
            JSONObject json = generateJSON(id, item);
            json_items.put(json);
        } else {
            id = item.getSaveId();
            JSONObject json = generateJSON(id, item);
            for (int i = 0; i < json_items.length(); i++) {
                int map_id = json_items.getJSONObject(i).getInt("id");
                if (id == map_id) {
                    json_items.remove(i);
                    break;
                }
            }
            json_items.put(json);
        }

        writeItemFile(json_content);
        item.setIsSaved(true);
        item.setSaveId(id);
        return true;
    }

    /**
     * Generate JSON for an item.
     *
     * @param id   Id of the item.
     * @param item Object of the item.
     * @return JSON of the item.
     */
    private JSONObject generateJSON(int id, Item item) {
        JSONObject json_item = new JSONObject();
        json_item.put("type", item.getType());
        json_item.put("id", id);
        json_item.put("attribute", item.getAttribute());
        json_item.put("attribute_value", item.getAttributeValue());

        System.out.println(json_item);
        return json_item;
    }

    /**
     * Read the item file.
     *
     * @return Content in the file.
     */
    private String readItemFile() {
        String content = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/files/item.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                content += line;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    /**
     * Write to the item file.
     *
     * @param json_content Content to be written.
     */
    public void writeItemFile(JSONObject json_content) {
        try {
            PrintWriter writer = new PrintWriter("src/files/item.txt", "UTF-8");
            writer.println(json_content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get item list from the file.
     *
     * @return List of items.
     */
    public JSONArray getItemList() {
        String content = readItemFile();
        JSONObject json_content = new JSONObject(content);
        JSONArray json_items = json_content.getJSONArray("items");

        return json_items;
    }

    /**
     * Get an item from the file.
     *
     * @param item_id Id of the item.
     * @return Object of the item.
     */
    public Item getItem(int item_id) {
        Item item;
        JSONArray json_items = getItemList();
        for (int i = 0; i < json_items.length(); i++) {
            JSONObject json_item = json_items.getJSONObject(i);
            int id = json_item.getInt("id");
            if (id == item_id) {
                String type = json_item.getString("type");
                String attribute = json_item.getString("attribute");
                int attribute_value = json_item.getInt("attribute_value");
                item = new Item(type, attribute, attribute_value);
                item.setIsSaved(true);
                item.setSaveId(item_id);
                return item;
            }
        }
        return null;
    }

    /**
     * Get an pitem from the file.
     *
     * @param item_id Id of the item.
     * @return Object of the item.
     */
    public PItem getPItem(int item_id) {
        PItem item;
        JSONArray json_items = getItemList();
        for (int i = 0; i < json_items.length(); i++) {
            JSONObject json_item = json_items.getJSONObject(i);
            int id = json_item.getInt("id");
            if (id == item_id) {
                String type = json_item.getString("type");
                String attribute = json_item.getString("attribute");
                int attribute_value = json_item.getInt("attribute_value");
                item = new PItem(item_id, type, attribute, attribute_value);
                return item;
            }
        }
        return null;
    }
}
