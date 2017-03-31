package view;

import controller.PlayController;
import model.PCharacter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Panel for playing.
 *
 * @author Jiayao
 * @version 1.0.0
 */
public class Play extends JPanel implements MouseListener {
    private JPanel map_panel, action_panel;
    private PInformationPanel information_panel;
    private PCharacteristicPanel characteristic_panel;
    private PInventoryPanel inventory_panel;
    private PCellPanel[][] cells;
    private PCellPanel current_cell, previous_cell;
    private PlayController play_controller;
    private JPanel battleInfo_panel;
    private static JTextArea battleInfo_area;
    private JScrollPane scrollPane;
    private JSONObject json_map;
    private int width, height;

    /**
     * Initiate the play panel.
     *
     * @param character_id Id of the player.
     * @param campaign_id Id of the campaign.
     */
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

        cells = new PCellPanel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new PCellPanel(i, j);
                cells[i][j].addMouseListener(this);
                String content = getJSONContent(json_cells, i, j);
                cells[i][j].setContent(content);
                map_panel.add(cells[i][j]);
            }
        }
        inventory_panel = new PInventoryPanel();
        information_panel = new PInformationPanel(play_controller);
        characteristic_panel = new PCharacteristicPanel();
        play_controller.setInventoryObserver(inventory_panel);
        play_controller.setCharacterObserver(characteristic_panel);
        inventory_panel.setPlayController(play_controller);

        battleInfo_panel = new JPanel();
        battleInfo_area = new JTextArea();
        battleInfo_area.setEditable(false);
        battleInfo_area.setText("Battle Information Display \n");
        scrollPane = new JScrollPane(battleInfo_area);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        battleInfo_panel.add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(450,120));

        action_panel = new JPanel(new GridLayout(4, 0));
        action_panel.setBorder(BorderFactory.createTitledBorder(null, "Actions", TitledBorder.TOP, TitledBorder.CENTER, new Font("Lucida Calligraphy", Font.PLAIN, 20), Color.BLACK));
        action_panel.add(information_panel);
        action_panel.add(battleInfo_panel);
        action_panel.add(characteristic_panel);
        action_panel.add(inventory_panel);

        add(map_panel);
        add(action_panel);

    }

    /**
     * Display the real-time battle information.
     *
     * @param infoToDisplay The information that is to be displayed during the battle.
     */
    public static void displayInfo(String infoToDisplay){
        battleInfo_area.append(infoToDisplay+"\n");
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

        current_cell = (PCellPanel) arg0.getSource();
        if (previous_cell == null) {
            current_cell.select();
            previous_cell = current_cell;
            information_panel.showInformation(previous_cell, isAdjacent(previous_cell.x, previous_cell.y));
        } else {
            if (current_cell.x == previous_cell.x && current_cell.y == previous_cell.y) {
                current_cell.deselect();
                previous_cell = null;
            } else {
                inventory_panel.clean();
                previous_cell.deselect();
                current_cell.select();

                if (previous_cell.content.equals("PLAYER") && current_cell.content.equals("")) {
                    previous_cell.removeContent();
                    current_cell.setContent("PLAYER");
                    play_controller.setPlayer(previous_cell.x, previous_cell.y, current_cell.x, current_cell.y);
                } else if (previous_cell.content.equals("PLAYER") && current_cell.content.equals("EXIT")) {
                    if (play_controller.isFulfilled()) {
                        if (play_controller.exit()) {
                            JOptionPane.showMessageDialog(Main.mainFrame, "Level Up! Go to Next Map!");
                            //remove(map_panel);
                            map_panel.removeAll();
                            json_map = play_controller.readCurrentMap();

                            width = json_map.getInt("width");
                            height = json_map.getInt("height");

                            JSONArray json_cells = json_map.getJSONArray("cells");
                            map_panel.setLayout(new GridLayout(width, height));
                            map_panel.setBorder(BorderFactory.createTitledBorder(null, "Map", TitledBorder.TOP, TitledBorder.CENTER, new Font("Lucida Calligraphy", Font.PLAIN, 20), Color.BLACK));

                            cells = new PCellPanel[width][height];
                            for (int i = 0; i < width; i++) {
                                for (int j = 0; j < height; j++) {
                                    cells[i][j] = new PCellPanel(i, j);
                                    cells[i][j].addMouseListener(this);
                                    String content = getJSONContent(json_cells, i, j);
                                    cells[i][j].setContent(content);
                                    map_panel.add(cells[i][j]);
                                }
                            }
                            previous_cell = null;
                            current_cell = null;
                            play_controller.readCharacter();
                            inventory_panel.clean();
                            characteristic_panel.clean();
                            map_panel.revalidate();
                            map_panel.repaint();
                        } else {
                            JOptionPane.showMessageDialog(Main.mainFrame, "Complete!");
                            Main.mainFrame.setVisible(false);
                            Main.mainFrame.dispose();
                            Main.mainFrame = new Main();
                        }
                    }
                }
                if (current_cell != null) {
                    previous_cell = current_cell;
                    information_panel.showInformation(previous_cell, isAdjacent(previous_cell.x, previous_cell.y));
                }
            }
        }

        // Character view
        if (current_cell != null) {
            if (current_cell.getContent().length() < 10) {
                if (current_cell.getContent().equals("PLAYER"))
                    play_controller.characterView();
                else
                    characteristic_panel.clean();
            } else if (current_cell.getContent().substring(0, 9).equals("CHARACTER")) {
                play_controller.characterView(current_cell.x, current_cell.y);
            }
        }

        // Inventory view
        if (current_cell != null) {
            if (current_cell.getContent().length() < 10) {
                if (current_cell.getContent().equals("PLAYER"))
                    play_controller.inventoryView();
                else
                    inventory_panel.clean();
            } else if (current_cell.getContent().substring(0, 9).equals("CHARACTER")) {
                play_controller.inventoryView(current_cell.x, current_cell.y);
            }

        }
    }

    /**
     * Check if the player is near the selected cell.
     *
     * @param x X coordinate of the selected cell.
     * @param y Y coordinate of the selected cell.
     * @return True if the player is near the selected cell, otherwise false.
     */
    private boolean isAdjacent(int x, int y) {
        int player_x = -1;
        int player_y = -1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (cells[i][j].content.equals("PLAYER")) {
                    player_x = i;
                    player_y = j;
                    break;
                }
            }
        }
        if (player_x == x - 1 && player_y == y)
            return true;
        else if (player_x == x + 1 && player_y == y)
            return true;
        else if (player_x == x && player_y == y - 1)
            return true;
        else if (player_x == x && player_y == y + 1)
            return true;
        return false;
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
