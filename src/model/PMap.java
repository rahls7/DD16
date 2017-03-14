package model;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PMap {

    private int map_id, map_index, width, height;
    private PCell[][] cells;

    public PMap(JSONObject json_map, int index){
        this.map_id = json_map.getInt("id");
        this.map_index = index;

        width = json_map.getInt("width");
        height = json_map.getInt("height");
        cells = new PCell[width][height];
        JSONArray json_cells = json_map.getJSONArray("cells");

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                String content = getJSONContent(json_cells, i, j);
                cells[i][j] = new PCell(i, j, content);
            }
        }
    }

    private String getJSONContent(JSONArray json_cells, int x, int y) {
        for (int i = 0; i < json_cells.length(); i++) {
            JSONObject json_cell = json_cells.getJSONObject(i);
            int cell_x = json_cell.getInt("cell_x");
            int cell_y = json_cell.getInt("cell_y");
            if (cell_x == x && cell_y == y)
                return json_cell.getString("cell_content");
        }
        return null;
    }

    public PCell[][] getCells() { return cells; }

    public int getId(){ return map_id;}

    public void setPlayer(PCharacter player) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                PCell cell = cells[i][j];
                if(cell.getType().equals("ENTRY")) {
                    cell.setPlayer(player);
                }
            }
        }
    }

    public void setPlayer(int previous_x, int previous_y, int current_x, int current_y, PCharacter player) {
        cells[previous_x][previous_y].removePlayer();
        cells[current_x][current_y].setPlayer(player);
    }

    public PItem getChestItem(int x, int y) {

        if(cells[x][y].getContent().type == "CHEST") {
            PChest chest = (PChest)cells[x][y].getContent();
            PItem item = chest.getItem();
            if(item != null) {
                chest.removeItem();
                return item;
            }
            return null;
        }
        return null;
    }

    public PCharacter getFriend(int x, int y) {

        if(cells[x][y].getType().equals("CHARACTER")) {
            System.out.println("Inside Loop");
            PCharacter friend = (PCharacter) cells[x][y].getContent();
            if(friend!=null&& friend.getCategory()==0) {
                System.out.println("Yayy!");
                return friend;
            }
            /*if(friend.getCategory()==0) {
                ArrayList<PItem> friendItems = friend.getBackpack();
                if(friendItems.size()<10) {
                    friend.addEquipment(item);
                }
            }*/
        }
        System.out.println("You are fucked");
        return null;
    }

    public PCharacter getEnemy(int x, int y) {
        if(cells[x][y].getType().equals("CHARACTER")) {
            PCharacter enemy = (PCharacter) cells[x][y].getContent();
            if(enemy!=null && enemy.getCategory()==1) {
                return enemy;
            }
        }
        return null;
    }

}
