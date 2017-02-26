package controller;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * controller class to mediate the communication between models and views.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */

public class MapEditorController {
    private Map map;
    private Cell [][] cells;
    private MapIO mapio;
    private ItemIO itemio;
    private CharacterIO characterio;

    /**
     * Initiate an instance of MapIO.
     */
    public MapEditorController() {
        //map = new Map(width, height);
        mapio = new MapIO();
        itemio = new ItemIO();
        characterio = new CharacterIO();
        //cells = map.cells;
    }

    /**
     * Initiate an instance of Map.
     *
     * @param width The width of the Map instance.
     * @param height The height of the Map instance.
     */
    public void createMap(int width, int height, boolean isSaved) {
        map = new Map(width, height, isSaved);
        cells = map.cells;
    }

    /**
     * Set the Id of the map.
     * @param map_id
     */
    public void setId(int map_id){
        map.setID(map_id);
    }
    /**
     * Set content of a cell.
     *
     * @param x X Coordinate of the cell.
     * @param y Y Coordinate of the cell.
     * @param content Content of the cell.
     */
    public void setContent(int x, int y, String content) {
        map.setContent(x, y, content);
    }

    /**
     * Remove content of a cell.
     *
     * @param x X Coordinate of the cell.
     * @param y Y Coordinate of the cell.
     */
    public void removeContent(int x, int y) {
        map.removeContent(x, y);
    }

    /**
     * Validate the correctness of the map.
     *
     * @return True if the map is correct, otherwise false.
     */
    public boolean validateMap() {

        return map.validation();
    }

    /**
     * Sve the map to the file.
     */
    public void saveMap() {
        mapio.saveMap(map);
    }

    /**
     * Read a map from the file.
     *
     * @param map_id Id of the map.
     * @return Information of the map.
     */
    public JSONObject readMap(int map_id) {
        return mapio.readMap(map_id);
    }

    /**
     * Get map list from the file.
     *
     * @return List of maps.
     */
    public JSONArray getMapList() {
        return mapio.getMapList();
    }

    /**
     * Get item list from the file.
     *
     * @return List of items.
     */
    public JSONArray getItemList() {
        return itemio.getItemList();
    }

    /**
     * Get character list from the file.
     *
     * @return List of characters.
     */
    public JSONArray getCharacterList() {
        return characterio.getItemList();
    }
}

