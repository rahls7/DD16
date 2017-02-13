package Controller;

/**
 * Created by Alleria on 2017/2/11.
 */

import org.json.JSONObject;

import model.MapIO;
import model.Cell;
import model.Map;

/**
 * Controller class to mediate the communication between models and views.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */

public class MapEditorController {
    private Map map;
    private Cell [][] cells;
    private MapIO mapio;

    /**
     * Initiate an instance of MapIO.
     */
    public MapEditorController() {
        //map = new Map(width, height);
        mapio = new MapIO();
        //cells = map.cells;
    }

    /**
     * Initiate an instance of Map.
     *
     * @param width The width of the Map instance.
     * @param height The height of the Map instance.
     */
    public void createMap(int width, int height) {
        map = new Map(width, height);
        cells = map.cells;
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
}

