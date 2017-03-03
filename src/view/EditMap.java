package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import controller.MapEditorController;

/**
 * Create a panel for map editing.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */
public class EditMap extends JPanel implements MouseListener {

    private JPanel map_panel, setting_panel, panel_character, panel_chest;
    private CellPanel[][] cells;
    private CellPanel current_cell, previous_cell;
    private int width, height;
    private JComboBox<Integer> items;
    private JComboBox<String> characters;
    private JButton button_wall, button_entry, button_exit, button_remove, button_validate, button_save, button_character, button_chest;
    private JCheckBox checkbox_hostile;
    private JLabel label_enemy;
    private MapEditorController map_controller;
    private JSONObject json_map;

    /**
     * Initiate a panel for map editing.
     *
     * @param map_id Id of the map.
     */
    public EditMap(int map_id) {
        super(new GridLayout(1, 0));

        map_controller = new MapEditorController();
        json_map = new JSONObject();
        json_map = map_controller.readMap(map_id);

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
        setting_panel.setBorder(BorderFactory.createTitledBorder(null, "Setting", TitledBorder.TOP, TitledBorder.CENTER, new Font("Lucida Calligraphy", Font.PLAIN, 20), Color.BLACK));

        button_wall = new JButton("Add Wall");
        button_wall.addActionListener(new setContent("WALL"));
        button_entry = new JButton("Add Entry");
        button_entry.addActionListener(new setContent("ENTRY"));
        button_exit = new JButton("Add Exit");
        button_exit.addActionListener(new setContent("EXIT"));
        button_remove = new JButton("Remove");
        button_remove.addActionListener(new removeContent());
        button_validate = new JButton("Validate Map");
        button_validate.addActionListener(new validateMap());
        button_save = new JButton("Save Map");
        button_save.addActionListener(new saveMap());

        panel_character = new JPanel();
        characters = getCharacterList();
        characters.setSelectedIndex(0);
        characters.setPreferredSize(new Dimension(100, 30));
        button_character = new JButton("Add Character");
        checkbox_hostile = new JCheckBox();
        label_enemy = new JLabel("Is Hostile");
        button_character.addActionListener(new setContent("CHARACTER"));
        panel_character.add(characters);
        panel_character.add(label_enemy);
        panel_character.add(checkbox_hostile);
        panel_character.add(button_character);

        panel_chest = new JPanel();
        items = getItemList();
        items.setSelectedIndex(0);
        items.setPreferredSize(new Dimension(100, 30));
        button_chest = new JButton("Add Chest");
        button_chest.addActionListener(new setContent("CHEST"));
        panel_chest.add(items);
        panel_chest.add(button_chest);

        setting_panel.add(button_wall);
        setting_panel.add(button_entry);
        setting_panel.add(button_exit);
        setting_panel.add(panel_character);
        setting_panel.add(panel_chest);
        setting_panel.add(button_remove);
        setting_panel.add(button_validate);
        setting_panel.add(button_save);

        add(map_panel);
        add(setting_panel);
    }

    /**
     * Get items from the file.
     *
     * @return List of the items.
     */
    private JComboBox<Integer> getItemList() {
        JSONArray json_items = map_controller.getItemList();
        JComboBox<Integer> items = new JComboBox<Integer>();

        for (int i = 0; i < json_items.length(); i++) {
            int item_id = json_items.getJSONObject(i).getInt("id");
            items.addItem(item_id);
        }
        return items;
    }

    /**
     * Get character from the file.
     *
     * @return List of the characters.
     */
    private JComboBox<String> getCharacterList() {
        // TODO Auto-generated method stub
        JComboBox<String> characters = new JComboBox<String>();
        JSONArray json_items = map_controller.getCharacterList();

        for (int i = 0; i < json_items.length(); i++) {
            String character_id = json_items.getJSONObject(i).getString("id");
            characters.addItem(character_id);
        }
        return characters;
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

    /**
     * Set the content of a cell.
     */
    class setContent implements ActionListener {
        private String content = "";

        setContent(String c) {
            content = c;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void actionPerformed(ActionEvent arg0) {
            String co = "";
            if (content == "CHEST") {
                int item_id;
                if (items.getSelectedItem() != null) {
                    item_id = (int) items.getSelectedItem();
                    co = content + " " + Integer.toString(item_id);
                } else
                    co = content;
            } else if (content == "CHARACTER") {
                String character_id;
                int isHostile = 0;
                if (checkbox_hostile.isSelected())
                    isHostile = 1;
                if (characters.getSelectedItem() != null) {
                    character_id = (String) characters.getSelectedItem();
                    co = content + " " + characters.getSelectedItem() + " " + isHostile;
                }
            } else
                co = content;
            map_controller.setContent(previous_cell.x, previous_cell.y, co);
            if (co.equals("ENTRY") || co.equals("EXIT")) {
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        if (cells[i][j].content.equals(co)) {
                            cells[i][j].removeContent();
                            break;
                        }
                    }
                }
            }
            cells[previous_cell.x][previous_cell.y].setContent(co);
        }
    }

    /**
     * Remove the content of a cell.
     */
    class removeContent implements ActionListener {

        @SuppressWarnings("deprecation")
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (previous_cell != null) {
                map_controller.removeContent(previous_cell.x, previous_cell.y);
                cells[previous_cell.x][previous_cell.y].removeContent();
            }
        }
    }

    /**
     * Validate the map.
     */
    class validateMap implements ActionListener {
        @SuppressWarnings("deprecation")
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (map_controller.validateMap())
                JOptionPane.showMessageDialog(Main.mainFrame, "Success");
            else
                JOptionPane.showMessageDialog(Main.mainFrame, "Fail");
        }
    }

    /**
     * Save the map to the file.
     */
    class saveMap implements ActionListener {
        @SuppressWarnings("deprecation")
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (map_controller.validateMap()) {
                map_controller.saveMap();
                JOptionPane.showMessageDialog(Main.mainFrame, "Success");
            } else
                JOptionPane.showMessageDialog(Main.mainFrame, "The map is not valid.");
        }
    }
}
