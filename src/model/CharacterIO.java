package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by mo on 2017-02-24.
 */
public class CharacterIO {

    public boolean saveCharacter(Character character) {
        String id = character.getId();
        String content = readCharacterFile();
        JSONObject json_content = new JSONObject(content);
        JSONArray json_items = json_content.getJSONArray("character");
        String item_id;
        if (!character.getIsSaved()) {
            character.setIsSaved(true);
            JSONObject json = generateJSON(id, character);
            json_items.put(json);
        } else {
            JSONObject json = generateJSON(id, character);
            for (int i = 0; i < json_items.length(); i++) {
                item_id = json_items.getJSONObject(i).getString("id");
                if (id.compareTo(item_id)==0) {
                    json_items.remove(i);
                    break;
                }
            }
            json_items.put(json);
        }

        writeCharacterFile(json_content);
        return true;
    }

    /**
     * Generate JSON for an item.
     *
     * @param id Id of the item.
     * @param character Object of the item.
     * @return JSON of the item.
     */
    private JSONObject generateJSON(String id, Character character) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", character.getId());
        jsonObject.put("name", character.getName());
        ArrayList<Integer> equipment = new ArrayList<Integer>();
        for (Item item:character.getEquipment()){
            equipment.add(item.getSaveId());
        }
        jsonObject.put("equipment", equipment);
        ArrayList<Integer> backpack = new ArrayList<Integer>();
        for (Item item: character.getBackpack()){
            backpack.add(item.getSaveId());
        }
        jsonObject.put("backpack", backpack);
        jsonObject.put("basicStats", character.basicStats);
        jsonObject.put("basicAttributes", character.basicAttributes);
        jsonObject.put("stats", character.getStats());
        jsonObject.put("attributes", character.getAttributes());
        jsonObject.put("isSaved", character.getIsSaved());

        System.out.println(jsonObject);
        return jsonObject;
    }

    /**
     * Read the item file.
     *
     * @return Content in the file.
     */
    private String readCharacterFile() {
        String content = "";
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("src/files/character.txt"));
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

    /**
     * Write to the item file.
     *
     * @param json_content Content to be written.
     */
    public void writeCharacterFile(JSONObject json_content){
        try {
            PrintWriter writer = new PrintWriter("src/files/character.txt", "UTF-8");
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
        String content = readCharacterFile();
        JSONObject json_content = new JSONObject(content);
        JSONArray json_items = json_content.getJSONArray("character");

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
            if(id.equals(item_id)){
                String name = json_item.getString("name");

                ItemIO itemIO= new ItemIO();
                int [] equipment = null;
                data = json_item.getJSONArray("equipment");
                equipment = new int[data.length()];
                for (int j=0; j<data.length(); j++) {
                    equipment[j] = data.getInt(j);
                    character.setEquipment(itemIO.getItem(equipment[j]));
                }

                int [] backpack = null;
                data = json_item.getJSONArray("backpack");
                backpack = new int[data.length()];
                for (int j=0; j<data.length(); j++) {
                    backpack[j] = data.getInt(j);
                    character.setBackpack(itemIO.getItem(backpack[j]));
                }

                int [][] stats = new int[7][2];
                data = json_item.getJSONArray("stats");
                for (int j=0; j<6; j++){
                    JSONArray data2 = data.getJSONArray(j);
                    for (int k=0; k<2; k++)
                        stats[j][k] = data2.getInt(k);
                }
                int [][] basicStats = new int[7][2];
                data = json_item.getJSONArray("basicStats");
                for (int j=0; j<6; j++){
                    JSONArray data2 = data.getJSONArray(j);
                    for (int k=0; k<2; k++)
                        basicStats[j][k] = data2.getInt(k);
                }

                int[] attributes = new int[6];
                data = json_item.getJSONArray("attributes");
                for (int j=0; j<6; j++)
                    attributes[j] = data.getInt(j);
                int [] basicAttribute = new int[6];
                data = json_item.getJSONArray("basicAttributes");
                for (int j=0; j<6; j++)
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
}
