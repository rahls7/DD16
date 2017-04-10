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

    /**
     * Save the current map
     *
     * @param pMap the map to be saved
     * @param campaign_id the id of the campaign
     * @param current_mapindex the index of the current map
     */
    public void savePlay(PMap pMap, int campaign_id, int current_mapindex) {

        String content = readPlayFile();
        JSONObject json_content = new JSONObject(content);
        JSONArray json_items = json_content.getJSONArray("play");
        JSONObject json = generateJSON(pMap, campaign_id, current_mapindex);
        json_items.put(json);
        writePlayFile(json_content);
    }

    /**
     * Generate JSON for the current map.
     *
     * @param pMap Current map.
     * @param campaign_id Current campaign ID.
     * @param current_mapindex Current map index.
     *
     * @return JSON of the map.
     */
    private JSONObject generateJSON(PMap pMap, int campaign_id, int current_mapindex) {

        JSONObject jsonObject = new JSONObject();

        JSONArray json_cells = new JSONArray();
        PCell[][] cells = pMap.getCells();
        for (int i = 0; i < pMap.getWidth(); i++){
            for (int j = 0; j < pMap.getHeight(); j++){
                JSONObject json_cell = new JSONObject();
                json_cell.put("cell_x", i);
                json_cell.put("cell_y", j);

                String type = cells[i][j].getContent().getType();
                if(type.equals("FRIEND")||type.equals("ENEMY")||type.equals("PLAYER")){
                    saveCharacter((PCharacter) cells[i][j].getContent(), i, j);
                }
                else if(type.equals("CHEST")){
                    PChest c = (PChest) cells[i][j].getContent();
                    type = type + " " + c.getItem_id();
                }
                json_cell.put("cell_content", type);

                json_cells.put(json_cell);
            }
        }
        jsonObject.put("cells", json_cells);

        jsonObject.put("campaignId", campaign_id);
        jsonObject.put("mapIndex", current_mapindex);

        System.out.println(jsonObject);
        return jsonObject;
    }

    /**
     * Save all the characters in the current map
     *
     * @param character the character to be saved
     * @param x the x index of the character
     * @param y the y index of the character
     */

    private void saveCharacter(PCharacter character, int x, int y){

        String character_content = readPlayCharacterFile();
        JSONObject json_character_content = new JSONObject(character_content);
        JSONArray json_character_items = json_character_content.getJSONArray("character");
        JSONObject json_character = generateCharacterJSON(character, x, y);
        json_character_items.put(json_character);
        writePlayCharacterFile(json_character_content);
    }

    /**
     * Generate JSON for all characters in the map.
     *
     * @param character the character to be generated
     * @param x x index of the character
     * @param y y index of the character
     * @return JSON of the character
     */
    private JSONObject generateCharacterJSON(PCharacter character, int x, int y){
        JSONObject jsonCharacterObject = new JSONObject();

        ArrayList<Integer> equipment = new ArrayList<Integer>();
        for (PItem item : character.getEquipment()) {
            equipment.add(item.getId());
        }
        jsonCharacterObject.put("equipment", equipment);

        ArrayList<Integer> backpack = new ArrayList<Integer>();
        for (PItem item : character.getBackpack()) {
            backpack.add(item.getId());
        }
        jsonCharacterObject.put("backpack", backpack);

        jsonCharacterObject.put("name", character.getName());
        jsonCharacterObject.put("basicStats", character.getBasicStats());
        jsonCharacterObject.put("basicAttributes", character.getBasicAttributes());
        jsonCharacterObject.put("stats", character.getStats());
        jsonCharacterObject.put("attributes", character.getAttributes());
        jsonCharacterObject.put("isSaved", character.isSaved());

        jsonCharacterObject.put("x", x);
        jsonCharacterObject.put("y", y);

        return jsonCharacterObject;
    }

    /**
     * Read the play file.
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
     * Read the playCharacter file
     *
     * @return Content in the file
     */
    private String readPlayCharacterFile() {
        String content = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/files/playCharacter.txt"));
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
     * Write to the play file.
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
     * Write to the playCharacter file
     *
     * @param json_content Content to be written
     */
    public void writePlayCharacterFile(JSONObject json_content) {
        try {
            PrintWriter writer = new PrintWriter("src/files/playCharacter.txt", "UTF-8");
            writer.println(json_content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Get play list from the file.
     *
     * @return List of items.
     */
    public JSONArray getPlayList() {
        String content = readPlayFile();
        JSONObject json_content = new JSONObject(content);
        JSONArray json_items = json_content.getJSONArray("play");

        return json_items;
    }

    public JSONArray getPlayCharacterList() {
        String content = readPlayFile();
        JSONObject json_content = new JSONObject(content);
        JSONArray json_items = json_content.getJSONArray("character");

        return json_items;
    }
    /**
     * Get a play from the file.
     *
     * @param player_id Id of the player.
     * @return Object of the player.
     */
    public Character getPlay(String player_id) {
        Character player = new Character(player_id);
        JSONArray json_items = getPlayList();
        String id;
        JSONObject json_item = new JSONObject();
        JSONArray data = new JSONArray();
        for (int i = 0; i < json_items.length(); i++) {
            json_item = json_items.getJSONObject(i);
            id = json_item.getString("id");
            if (id.equals(player_id)) {
                String name = json_item.getString("name");

                ItemIO itemIO = new ItemIO();
                int[] equipment = null;
                data = json_item.getJSONArray("equipment");
                equipment = new int[data.length()];
                for (int j = 0; j < data.length(); j++) {
                    equipment[j] = data.getInt(j);
                    player.setEquipment(itemIO.getItem(equipment[j]));
                }

                int[] backpack = null;
                data = json_item.getJSONArray("backpack");
                backpack = new int[data.length()];
                for (int j = 0; j < data.length(); j++) {
                    backpack[j] = data.getInt(j);
                    player.setBackpack(itemIO.getItem(backpack[j]));
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

                player.setAttributes(attributes);
                player.setBasicAttributes(basicAttribute);
                player.setBasicStats(basicStats);
                player.setStats(stats);
                player.setIsSaved(true);
                player.setName(name);
                return player;
            }
        }
        return null;
    }
}
