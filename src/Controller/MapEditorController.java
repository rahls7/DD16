package Controller;

/**
 * Created by Alleria on 2017/2/11.
 */

import org.json.JSONObject;

import model.MapIO;
import model.Cell;
import model.Map;

public class MapEditorController {
    private Map map;
    private Cell [][] cells;
    private MapIO mapio;

    public MapEditorController() {
        //map = new Map(width, height);
        mapio = new MapIO();
        //cells = map.cells;
    }

    public void createMap(int width, int height) {
        map = new Map(width, height);
        cells = map.cells;
    }

    public void setContent(int x, int y, String content) {
        map.setContent(x, y, content);
    }

    public void removeContent(int x, int y) {
        map.removeContent(x, y);
    }

    public boolean validateMap() {

        return map.validation();
    }

    public void saveMap() {
        mapio.saveMap(map);
    }

    public JSONObject readMap(int map_id) {

        return mapio.readMap(map_id);
    }
}

