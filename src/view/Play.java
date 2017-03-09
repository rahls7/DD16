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
    private JPanel map_panel, setting_panel;
    private CellPanel[][] cells;
    private CellPanel current_cell, previous_cell;
    private PlayController play_controller;
    private JSONObject json_map;
    private int width, height;

    public Play(String character_id, int campaign_id) {
        super(new GridLayout(1, 0));

        play_controller = new PlayController(character_id, campaign_id);

        json_map = new JSONObject();
        json_map = play_controller.readMap(map_id);

        width = json_map.getInt("width");
        height = json_map.getInt("height");
        map_controller.createMap(width, height, true);
        map_controller.setId(map_id);
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
                map_controller.setContent(i, j, content);
                map_panel.add(cells[i][j]);
            }
        }

        setting_panel = new JPanel(new GridLayout(3, 2));
        setting_panel.setBorder(BorderFactory.createTitledBorder(null, "Details", TitledBorder.TOP, TitledBorder.CENTER, new Font("Lucida Calligraphy", Font.PLAIN, 20), Color.BLACK));



        add(map_panel);
        add(setting_panel);

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
