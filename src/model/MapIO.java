package model;

/**
 * Created by Alleria on 2017/2/11.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Map;

public class MapIO {
    public MapIO() {

    }

    public void saveMap(Map map) {
        int id = 0;
        String content = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("d://map.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                content += line;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject json_content = new JSONObject(content);
        JSONArray json_maps = json_content.getJSONArray("maps");
        if (!map.isSaved) {
            for (int i = 0; i < json_maps.length(); i++) {
                int map_id = json_maps.getJSONObject(i).getInt("id");
                System.out.println(i + ": " + json_maps.getJSONObject(i).getInt("id"));
                if (id < map_id)
                    id = map_id;
            }
            id += 1;
            JSONObject json = generateJSON(id, map);
            json_maps.put(json);
        } else {
            id = map.save_id;
            JSONObject json = generateJSON(id, map);
            for (int i = 0; i < json_maps.length(); i++) {
                int map_id = json_maps.getJSONObject(i).getInt("id");
                if (id == map_id) {
                    json_maps.remove(i);
                    break;
                }

            }

            json_maps.put(json);
        }

        try {
            PrintWriter writer = new PrintWriter("d://map.txt", "UTF-8");
            writer.println(json_content);
            writer.close();
            map.isSaved = true;
            map.save_id = id;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject generateJSON(int id, Map map) {
        JSONObject json_map = new JSONObject();
        json_map.put("id", id);
        json_map.put("width", map.map_width);
        json_map.put("height", map.map_height);

        JSONArray json_cells = new JSONArray();
        for (int i = 0; i < map.map_width; i++) {
            for (int j = 0; j < map.map_height; j++) {
                JSONObject json_cell = new JSONObject();
                json_cell.put("cell_x", map.cells[i][j].x);
                json_cell.put("cell_y", map.cells[i][j].y);
                json_cell.put("cell_content", map.cells[i][j].content);
                json_cells.put(json_cell);
            }
        }
        json_map.put("cells", json_cells);
        System.out.println(json_map);
        return json_map;
    }

    public JSONObject readMap(int map_id) {
        String content = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("d://map.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                content += line;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject json_content = new JSONObject(content);
        JSONArray json_maps = json_content.getJSONArray("maps");
        JSONObject json_map = new JSONObject();
        for (int i = 0; i < json_maps.length(); i++) {
            int id = json_maps.getJSONObject(i).getInt("id");
            if (id == map_id) {
                json_map = json_maps.getJSONObject(i);
                break;
            }
        }
        return json_map;
    }
}
