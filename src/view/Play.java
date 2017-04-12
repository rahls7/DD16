package view;

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
    private JButton save_play;
    private JSONObject json_map;
    private int width, height;
    public static boolean moved;

    /**
     * Initiate the play panel.
     *
     * @param character_id Id of the player.
     * @param campaign_id  Id of the campaign.
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
        inventory_panel.setCells(cells);

        battleInfo_panel = new JPanel();
        battleInfo_panel.setLayout(null);
        battleInfo_area = new JTextArea();
        battleInfo_area.setEditable(false);
        battleInfo_area.setText("Battle Information Display \n");
        scrollPane = new JScrollPane(battleInfo_area);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(20,20,450, 120);
        save_play = new JButton("Save Game");
        save_play.setBounds(500, 60, 100, 30);
        save_play.addActionListener(new savePlay());
        battleInfo_panel.add(scrollPane);
        battleInfo_panel.add(save_play);


        action_panel = new JPanel(new GridLayout(4, 0));
        action_panel.setBorder(BorderFactory.createTitledBorder(null, "Actions", TitledBorder.TOP, TitledBorder.CENTER, new Font("Lucida Calligraphy", Font.PLAIN, 20), Color.BLACK));
        action_panel.add(information_panel);
        action_panel.add(battleInfo_panel);
        action_panel.add(characteristic_panel);
        action_panel.add(inventory_panel);

        add(map_panel);
        add(action_panel);

        play_controller.setCellPanel(cells);


        play_controller.beforePlayer();
        moved=false;

    }

    /**
     * Initiate the play panel through load game.
     *
     * @param play_id Id of the play file.
     */
    public Play(int play_id) {
        super(new GridLayout(1, 0));

        play_controller = new PlayController(play_id);

        json_map = new JSONObject();
        json_map = play_controller.readPlayMap(play_id);

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
        inventory_panel.setCells(cells);

        battleInfo_panel = new JPanel();
        battleInfo_panel.setLayout(null);
        battleInfo_area = new JTextArea();
        battleInfo_area.setEditable(false);
        battleInfo_area.setText("Battle Information Display \n");
        scrollPane = new JScrollPane(battleInfo_area);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(20,20,450, 120);
        save_play = new JButton("Save Game");
        save_play.setBounds(500, 60, 100, 30);
        save_play.addActionListener(new savePlay());
        battleInfo_panel.add(scrollPane);
        battleInfo_panel.add(save_play);


        action_panel = new JPanel(new GridLayout(4, 0));
        action_panel.setBorder(BorderFactory.createTitledBorder(null, "Actions", TitledBorder.TOP, TitledBorder.CENTER, new Font("Lucida Calligraphy", Font.PLAIN, 20), Color.BLACK));
        action_panel.add(information_panel);
        action_panel.add(battleInfo_panel);
        action_panel.add(characteristic_panel);
        action_panel.add(inventory_panel);

        add(map_panel);
        add(action_panel);

        play_controller.setCellPanel(cells);


        play_controller.beforePlayer();
        moved=false;

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
            information_panel.showInformation(previous_cell, isAdjacent(previous_cell.x, previous_cell.y),false);
            if(current_cell.content.equals("PLAYER")) {
                showAttackRange(current_cell.x, current_cell.y);
            }
        } else {
            if (current_cell.x == previous_cell.x && current_cell.y == previous_cell.y) {
                current_cell.deselect();
                removeAttackRange();
                previous_cell = null;
            } else {
                inventory_panel.clean();
                previous_cell.deselect();
                removeAttackRange();
                current_cell.select();

                if (previous_cell.content.equals("PLAYER") && current_cell.content.equals("") && !moved && isMoveRange(previous_cell, current_cell)) {
                    previous_cell.removeContent();
                    showAttackRange(current_cell.x, current_cell.y);
                    current_cell.setContent("PLAYER");
                    play_controller.setPlayer(previous_cell.x, previous_cell.y, current_cell.x, current_cell.y);
                    current_cell.select();
                    moved = true;
                }
                else if (!previous_cell.content.equals("PLAYER") && current_cell.content.equals("PLAYER")) {
                    showAttackRange(current_cell.x, current_cell.y);
                }

                else if (previous_cell.content.equals("PLAYER") && current_cell.content.equals("EXIT") && !moved && isMoveRange(previous_cell, current_cell)) {
                    System.out.println("!!!!!" + play_controller.isFulfilled());
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
                            play_controller.setPlayer();
                            play_controller.readCharacter();
                            inventory_panel.clean();
                            characteristic_panel.clean();

                            inventory_panel.setCells(cells);
                            map_panel.revalidate();
                            map_panel.repaint();
                            play_controller.setCellPanel(cells);
                           play_controller.beforePlayer();
                            moved = false;
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
                    information_panel.showInformation(previous_cell, isAdjacent(previous_cell.x, previous_cell.y), isInRange(previous_cell.x, previous_cell.y));
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

    private void removeAttackRange() {
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++) {
                if(cells[i][j].isAttackRang == true) {
                    cells[i][j].removeAttackRange();
                }
            }
    }

    private void showAttackRange(int x, int y) {
        int[] ranged_x = {x-2, x, x, x+2, x-1, x, x, x+1, x-1, x+1, x-1, x+1};
        int[] ranged_y = {y, y-2, y+2, y, y, y-1, y+1, y, y-1, y+1, y+1, y-1};

        int[] melee_x = {x-1, x, x, x+1};
        int[] melee_y = {y, y-1, y+1, y};

        String weapon_type = play_controller.getWeaponType();

        if(weapon_type != null && weapon_type.equals("Ranged Weapon")) {
            for(int i = 0; i < ranged_x.length; i++){
                int cell_x = ranged_x[i];
                int cell_y = ranged_y[i];

                if(cell_x >= 0 && cell_y >= 0 && cell_x < cells.length && cell_y < cells[0].length){
                    cells[cell_x][cell_y].setAttackRange();
                }
            }
        }
        else {
            for(int i = 0; i < melee_x.length; i++){
                int cell_x = melee_x[i];
                int cell_y = melee_y[i];

                if(cell_x >= 0 && cell_y >= 0 && cell_x < cells.length && cell_y < cells[0].length){
                    cells[cell_x][cell_y].setAttackRange();
                }
            }
        }
    }

    public boolean isMoveRange(PCellPanel previous_cell, PCellPanel current_cell){
        if(Math.abs(previous_cell.x-current_cell.x)+Math.abs(previous_cell.y-current_cell.y)<=3){
            return true;
        }else{
            return false;
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

    private boolean isInRange(int check_x, int check_y) {
        int x = -1;
        int y = -1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (cells[i][j].content.equals("PLAYER")) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        int[] ranged_x = {x-2, x, x, x+2, x-1, x, x, x+1, x-1, x+1, x-1, x+1};
        int[] ranged_y = {y, y-2, y+2, y, y, y-1, y+1, y, y-1, y+1, y+1, y-1};

        int[] melee_x = {x-1, x, x, x+1};
        int[] melee_y = {y, y-1, y+1, y};

        String weapon_type = play_controller.getWeaponType();

        if(weapon_type != null && weapon_type.equals("Ranged Weapon")) {
            for(int i = 0; i < ranged_x.length; i++) {
                if(ranged_x[i] == check_x && ranged_y[i] == check_y)
                    return true;
            }
        }
        else {
            for(int i = 0; i < melee_x.length; i++) {
                if(melee_x[i] == check_x && melee_y[i] == check_y)
                    return true;
            }
        }
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
    class savePlay implements ActionListener {
        @SuppressWarnings("deprecation")
        @Override
        public void actionPerformed(ActionEvent arg0) {
                play_controller.savePlay();
                JOptionPane.showMessageDialog(Main.mainFrame, "Success");
        }
    }
}
