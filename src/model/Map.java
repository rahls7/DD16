package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Record information of a map.Change properties of a map.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */
public class Map {
    public int map_width;
    public int map_height;
    public Cell[][] cells;
    public boolean isSaved = false;
    public int save_id = 0;

    /**
     * Initiate an instance of Map.
     *
     * @param width   The width of the map instance.
     * @param height  The height of the map instance.
     * @param isSaved True if the map is saved in the file, otherwise false.
     */
    public Map(int width, int height, boolean isSaved) {
        map_width = width;
        map_height = height;
        this.isSaved = isSaved;
        cells = new Cell[map_width][map_height];

        for (int i = 0; i < map_width; i++) {
            for (int j = 0; j < map_height; j++) {
                Cell cell = new Cell(i, j);
                cells[i][j] = cell;
            }
        }
    }

    /**
     * Set the Id of the map.
     *
     * @param map_id Id of the map.
     */
    public void setID(int map_id) {
        save_id = map_id;
    }

    /**
     * Set content of a cell.
     *
     * @param x       X Coordinate of the cell.
     * @param y       Y Coordinate of the cell.
     * @param content Content of the cell.
     */
    public void setContent(int x, int y, String content) {
        if (content.equals("ENTRY") || content.equals("EXIT")) {
            for (int i = 0; i < map_width; i++) {
                for (int j = 0; j < map_height; j++) {
                    if (cells[i][j].content.equals(content)) {
                        cells[i][j].removeContent();
                        break;
                    }
                }
            }
        }
        cells[x][y].setContent(content);

    }

    /**
     * Remove content of a cell.
     *
     * @param x X Coordinate of the cell.
     * @param y Y Coordinate of the cell.
     */
    public void removeContent(int x, int y) {
        cells[x][y].removeContent();
    }

    /**
     * Validate the correctness of the map.
     *
     * @return True if the map is correct, otherwise false.
     */
    public boolean validation() {
        int entry_x = -1;
        int entry_y = -1;
        int exit_x = -1;
        int exit_y = -1;
        boolean hasEntry = false;
        boolean hasExit = false;
        for (int i = 0; i < map_width; i++) {
            for (int j = 0; j < map_height; j++) {
                if (cells[i][j].content.equals("ENTRY")) {
                    hasEntry = true;
                    entry_x = i;
                    entry_y = j;
                    break;
                }
            }
        }

        for (int i = 0; i < map_width; i++) {
            for (int j = 0; j < map_height; j++) {
                if (cells[i][j].content.equals("EXIT")) {
                    hasExit = true;
                    exit_x = i;
                    exit_y = j;
                    break;
                }
            }
        }

        if (!hasEntry || !hasExit)
            return false;

        List<String> possibleCells = new ArrayList<String>();
        List<String> subCells = new ArrayList<String>();
        int current_x = entry_x;
        int current_y = entry_y;
        int count = 0;

        possibleCells.add(current_x + " " + current_y + " " + count);
        subCells.add(current_x + " " + current_y + " " + count);
        return checkSubCells(subCells, possibleCells, exit_x, exit_y);

    }

    /**
     * Check cells that the character can move to in next step.
     *
     * @param subCells      The cells the character can move.
     * @param possibleCells All the cells the character can move to.
     * @param exit_x        X Coordinate of the exit.
     * @param exit_y        Y Coordinate of the exit.
     * @return True if the character can move to the exit, false if there are not more subCells the character can move to which means the character can't move to the exit.
     */
    private boolean checkSubCells(List<String> subCells, List<String> possibleCells, int exit_x, int exit_y) {
        if (subCells.size() == 0) {
            return false;
        }

        List<String> nextSubCells = new ArrayList<String>();
        for (int i = 0; i < subCells.size(); i++) {
            String[] cell_info = subCells.get(i).split(" ");
            int x = Integer.parseInt(cell_info[0]);
            int y = Integer.parseInt(cell_info[1]);
            int count = Integer.parseInt(cell_info[2]);
            count++;

            if (x == exit_x && y == exit_y) {
                return true;
            }

            checkAdjacentCells(nextSubCells, possibleCells, x, y, count);
        }
        return checkSubCells(nextSubCells, possibleCells, exit_x, exit_y);
    }

    /**
     * Check adjacent cells of a cell are possible for the character to move to.
     *
     * @param nextSubCells  The cells the character can move to in next step.
     * @param possibleCells All the cells the character can move to.
     * @param current_x     X Coordinate of the cell the character is on.
     * @param current_y     Y Coordinate of the cell the character is on.
     * @param current_count The number of the steps the character need to take in order to move to the current cell.
     */
    private void checkAdjacentCells(List<String> nextSubCells, List<String> possibleCells, int current_x, int current_y, int current_count) {
        int up_x = current_x - 1;
        int up_y = current_y;
        int bot_x = current_x + 1;
        int bot_y = current_y;
        int left_x = current_x;
        int left_y = current_y - 1;
        int right_x = current_x;
        int right_y = current_y + 1;

        int[] xs = {up_x, bot_x, left_x, right_x};
        int[] ys = {up_y, bot_y, left_y, right_y};

        for (int i = 0; i < 4; i++) {
            current_x = xs[i];
            current_y = ys[i];

            if (current_x >= 0 && current_x < map_width && current_y >= 0 && current_y < map_height) {

                if (!cells[current_x][current_y].content.equals("WALL")) {
                    isPossibleCells(nextSubCells, possibleCells, current_x, current_y, current_count);
                }
            }
        }
    }

    /**
     * Check if the current cell can be moved to in previous steps.
     *
     * @param nextSubCells  The cells the character can move to in next step.
     * @param possibleCells All the cells the character can move to.
     * @param current_x     X Coordinate of the cell the character is on.
     * @param current_y     Y Coordinate of the cell the character is on.
     * @param current_count The number of the steps the character need to take in order to move to the current cell.
     */
    private void isPossibleCells(List<String> nextSubCells, List<String> possibleCells, int current_x, int current_y, int current_count) {
        boolean inPossibleCells = false;
        for (int i = 0; i < possibleCells.size(); i++) {
            String[] cell_info = possibleCells.get(i).split(" ");
            int x = Integer.parseInt(cell_info[0]);
            int y = Integer.parseInt(cell_info[1]);
            int count = Integer.parseInt(cell_info[2]);
            //System.out.println(x + " " + y + " " + current_x + " " + current_y);
            if (x == current_x && y == current_y && current_count >= count) {
                inPossibleCells = true;
                break;
            }
        }

        if (!inPossibleCells) {
            possibleCells.add(current_x + " " + current_y + " " + current_count);
            nextSubCells.add(current_x + " " + current_y + " " + current_count);
            System.out.println(current_x + " " + current_y + " " + current_count);
        }
    }
}
