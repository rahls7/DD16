package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Fish on 2017/4/8.
 */
public class PlayIO {

    public void savePlayer(PCharacter player){

        String id = player.getId();
        String content = readPlayFile();
        JSONObject json_content = new JSONObject(content);
        JSONArray json_items = json_content.getJSONArray("player");
        JSONObject json = generateJSON(id, player);
        json_items.put(json);
        writePlayFile(json_content);
    }

    /**
     * Generate JSON for an item.
     *
     * @param id        Id of the item.
     * @param character Object of the item.
     * @return JSON of the item.
     */
    private JSONObject generateJSON(String id, PCharacter character) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", character.getId());
        jsonObject.put("name", character.getName());
        ArrayList<Integer> equipment = new ArrayList<Integer>();
        for (PItem item : character.getEquipment()) {
            equipment.add(item.getId());
        }
        jsonObject.put("equipment", equipment);
        ArrayList<Integer> backpack = new ArrayList<Integer>();
        for (PItem item : character.getBackpack()) {
            backpack.add(item.getId());
        }
        jsonObject.put("backpack", backpack);
        jsonObject.put("basicStats", character.getBasicStats());
        jsonObject.put("basicAttributes", character.getBasicAttributes());
        jsonObject.put("stats", character.getStats());
        jsonObject.put("attributes", character.getAttributes());
        jsonObject.put("isSaved", character.isSaved());

        System.out.println(jsonObject);
        return jsonObject;
    }

    /**
     * Read the item file.
     *
     * @return Content in the file.
     */
    private String readPlayFile() {
        String content = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/files/play.txt"));
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
    public void writePlayFile(JSONObject json_content) {
        try {
            PrintWriter writer = new PrintWriter("src/files/play.txt", "UTF-8");
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
        String content = readPlayFile();
        JSONObject json_content = new JSONObject(content);
        JSONArray json_items = json_content.getJSONArray("player");

        return json_items;
    }

    /**
     * Get an item from the file.
     *
     * @param item_id Id of the item.
     * @return Object of the item.
     */
    public Character getCharacter(String item_id) {
        Character character = new Character(item_id);
        JSONArray json_items = getItemList();
        String id;
        JSONObject json_item = new JSONObject();
        JSONArray data = new JSONArray();
        for (int i = 0; i < json_items.length(); i++) {
            json_item = json_items.getJSONObject(i);
            id = json_item.getString("id");
            if (id.equals(item_id)) {
                String name = json_item.getString("name");

                ItemIO itemIO = new ItemIO();
                int[] equipment = null;
                data = json_item.getJSONArray("equipment");
                equipment = new int[data.length()];
                for (int j = 0; j < data.length(); j++) {
                    equipment[j] = data.getInt(j);
                    character.setEquipment(itemIO.getItem(equipment[j]));
                }

                int[] backpack = null;
                data = json_item.getJSONArray("backpack");
                backpack = new int[data.length()];
                for (int j = 0; j < data.length(); j++) {
                    backpack[j] = data.getInt(j);
                    character.setBackpack(itemIO.getItem(backpack[j]));
                }

                int[][] stats = new int[7][2];
                data = json_item.getJSONArray("stats");
                for (int j = 0; j < 6; j++) {
                    JSONArray data2 = data.getJSONArray(j);
                    for (int k = 0; k < 2; k++)
                        stats[j][k] = data2.getInt(k);
                }
                int[][] basicStats = new int[7][2];
                data = json_item.getJSONArray("basicStats");
                for (int j = 0; j < 6; j++) {
                    JSONArray data2 = data.getJSONArray(j);
                    for (int k = 0; k < 2; k++)
                        basicStats[j][k] = data2.getInt(k);
                }

                int[] attributes = new int[6];
                data = json_item.getJSONArray("attributes");
                for (int j = 0; j < 6; j++)
                    attributes[j] = data.getInt(j);
                int[] basicAttribute = new int[6];
                data = json_item.getJSONArray("basicAttributes");
                for (int j = 0; j < 6; j++)
                    basicAttribute[j] = data.getInt(j);

                character.setAttributes(attributes);
                character.setBasicAttributes(basicAttribute);
                character.setBasicStats(basicStats);
                character.setStats(stats);
                character.setIsSaved(true);
                character.setName(name);
                return character;
            }
        }
        return null;
    }

//    public void saveCampaign(PCampaign campaign){
//
//    }
}
