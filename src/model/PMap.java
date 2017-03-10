package model;


import org.json.JSONArray;
import org.json.JSONObject;

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
        System.out.println("------------------------");
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(cells[i][j].getContent() != null)
                    System.out.println(i + " " + j + " " + cells[i][j].getContent().getType());
            }
        }
    }
}
