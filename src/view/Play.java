package view;

import controller.MapEditorController;
import controller.PlayController;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Play  extends JPanel implements MouseListener {
    private JPanel map_panel, action_panel;
    private CellPanel[][] cells;
    private CellPanel current_cell, previous_cell;
    private PlayController play_controller;
    private JSONObject json_map;
    private int width, height;

    public Play(String character_id, int campaign_id) {
        super(new GridLayout(1, 0));

        play_controller = new PlayController(character_id, campaign_id);

        json_map = new JSONObject();
        json_map = play_controller.readCurrentMap();

        width = json_map.getInt("width");
        height = json_map.getInt("height");

        JSONArray json_cells = json_map.getJSONArray("cells");

        map_panel = new JPanel(new GridLayout(width, height));
        map_panel.setBorder(BorderFactory.createTitledBorder(null, "Map", TitledBorder.TOP, TitledBorder.CENTER, new Font("Lucida Calligraphy", Font.PLAIN, 20), Color.BLACK));

        cells = new CellPanel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new CellPanel(i, j);
                cells[i][j].addMouseListener(this);
                String content = getJSONContent(json_cells, i, j);
                cells[i][j].setContent(content);
                map_panel.add(cells[i][j]);
            }
        }

        action_panel = new JPanel(new GridLayout(3, 2));
        action_panel.setBorder(BorderFactory.createTitledBorder(null, "Actions", TitledBorder.TOP, TitledBorder.CENTER, new Font("Lucida Calligraphy", Font.PLAIN, 20), Color.BLACK));

        add(map_panel);
        add(action_panel);

    }

    /**
     * Get content of a cell from the data formatted in JSON from the file.
     *
     * @param json_cells Cell information in JSON format.
     * @param x          X Coordinate of a cell.
     * @param y          Y Coordinate of a cell.
     * @return Content of the cell.
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
     * The action when the mouse is clicked.
     *
     * @param arg0 The mouse event.
     */
    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
        current_cell = (CellPanel) arg0.getSource();
        if (previous_cell == null) {
            current_cell.select();
            previous_cell = current_cell;
        } else {
            if (current_cell.x == previous_cell.x && current_cell.y == previous_cell.y) {
                current_cell.deselect();
                previous_cell = null;
            } else {
                previous_cell.deselect();
                current_cell.select();

                if(previous_cell.content.equals("PLAYER") && current_cell.content.equals("")){
                    previous_cell.removeContent();
                    current_cell.setContent("PLAYER");
                    play_controller.setPlayer(previous_cell.x, previous_cell.y, current_cell.x, current_cell.y);
                }

                previous_cell = current_cell;
            }
        }
    }

    /**
     * The action when the mouse is pressed.
     *
     * @param e The mouse event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * The action when the mouse is released.
     *
     * @param e The mouse event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * The action when the mouse enters.
     *
     * @param e The mouse event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * The action when the mouse exits.
     *
     * @param e The mouse event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }



}
