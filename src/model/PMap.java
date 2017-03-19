package model;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PMap {

    private int map_id, map_index, width, height;
    private PCell[][] cells;

    /**
     * Initialize a map model of play
     * @param json_map
     * @param index
     */
    public PMap(JSONObject json_map, int index) {
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

    /**
     * Get the width of the map
     * @return
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * Get the height of a map
     * @return
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Adapt the map to its level
     * @param level
     */
    public void adaptMapToLevel(int level){
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                if(cells[i][j].getType().equals("CHEST")){
                    PChest chest = (PChest)cells[i][j].getContent();
                    int new_value=adaptItemAttributeLevel(level);
                    chest.getItem().setAttributeValue(new_value);
                }else if(cells[i][j].getType().equals("CHARACTER")){
                    PCharacter character=(PCharacter)cells[i][j].getContent();
                    character.setLevel(level);
                    ArrayList<PItem> backpack= character.getBackpack();
                    ArrayList<PItem> equipment=character.getEquipment();
                    for(int k=0;k<backpack.size();k++){
                        PItem item=(PItem)backpack.get(k);
                        int new_value=adaptItemAttributeLevel(level);
                        backpack.get(k).setAttributeValue(new_value);
                    }
                    character.setBackpack(backpack);
                    for(int k=0;k<equipment.size();k++){
                        PItem item=(PItem)equipment.get(k);
                        int new_value=adaptItemAttributeLevel(level);
                        equipment.get(k).setAttributeValue(new_value);
                    }
                    character.setEquipment(equipment);
                    character.recalculateStats();
                }
            }
        }

    }

    /**
     * Adapt the item levels to the map
     * @param level
     * @return
     */
    public int adaptItemAttributeLevel(int level){
        int new_value=1;
        if(level>=1&&level<=4){
            new_value=1;
        }else if(level>=5 &&level<=8){
            new_value=2;
        }else if(level>=9&&level<=12){
            new_value=3;
        }else if(level>=13&&level<=16){
            new_value=4;
        }else if(level>=17){
            new_value=5;
        }
        return new_value;
    }

    /**
     * Get the object of a cell
     * @param json_cells
     * @param x
     * @param y
     * @return
     */
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

    /**
     * Get the cells of a map
     * @return
     */
    public PCell[][] getCells() {
        return cells;
    }

    /**
     * Get the map id
     * @return
     */
    public int getId() {
        return map_id;
    }

    /**
     * Set the width of a map
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Set the height of a map
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Set the player
     * @param player
     */
    public void setPlayer(PCharacter player) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                PCell cell = cells[i][j];
                if (cell.getType().equals("ENTRY")) {
                    cell.setPlayer(player);
                    cell.setType("PLAYER");
                }
            }
        }
    }

    /**
     * Set the player of a specific cell
     * @param previous_x
     * @param previous_y
     * @param current_x
     * @param current_y
     * @param player
     */
    public void setPlayer(int previous_x, int previous_y, int current_x, int current_y, PCharacter player) {
        cells[previous_x][previous_y].removePlayer();
        cells[current_x][current_y].setPlayer(player);
    }

    /**
     * Get the chest item of a specific cell
     * @param x
     * @param y
     * @return
     */
    public PItem getChestItem(int x, int y) {


        if(cells[x][y].getContent().type.equals("CHEST")) {
            PChest chest = (PChest)cells[x][y].getContent();
            PItem item = chest.getItem();
            if (item != null) {
                chest.removeItem();
                return item;
            }
            return null;
        }
        return null;
    }

    /**
     * Get the friend of a specific cell
     * @param x
     * @param y
     * @return
     */
    public PCharacter getFriend(int x, int y) {

        if(cells[x][y].getType().equals("CHARACTER")) {
            PCharacter friend = (PCharacter) cells[x][y].getContent();
            if(friend!=null&& friend.getCategory()==0) {
                System.out.println("Yayy!");
                return friend;
            }

        }
        return null;
    }

    /**
     * Get the enemy of a specific cell
     * @param x
     * @param y
     * @return
     */
    public PCharacter getEnemy(int x, int y) {
        if(cells[x][y].getType().equals("CHARACTER")) {
            PCharacter enemy = (PCharacter) cells[x][y].getContent();
            if(enemy!=null && enemy.getCategory()==1) {
                return enemy;
            }
        }
        return null;
    }

    /**
     * Check whether all the enemies are killed
     * @return
     */
    public boolean isFulFilled() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(cells[i][j].getType().equals("CHARACTER")) {
                    PCharacter c = (PCharacter) cells[i][j].getContent();
                    if(c.getCategory() == 1) {
                        if(c.getHitPoint() != 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
