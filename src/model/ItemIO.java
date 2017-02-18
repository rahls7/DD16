package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Alleria on 2017/2/17.
 */
public class ItemIO {
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

    private JSONObject generateJSON(int id, Item item) {
        JSONObject json_item = new JSONObject();
        json_item.put("type", item.getType());
        json_item.put("id", id);
        json_item.put("attribute", item.getAttribute());
        json_item.put("attribute_value", item.getAttributeValue());

        System.out.println(json_item);
        return json_item;
    }

    private String readItemFile() {
        String content = "";
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("src/files/item.txt"));
            String line;
            while ((line = reader.readLine()) != null)
            {
                content += line;
            }
            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return content;
    }

    public void writeItemFile(JSONObject json_content){
        try {
            PrintWriter writer = new PrintWriter("src/files/item.txt", "UTF-8");
            writer.println(json_content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
