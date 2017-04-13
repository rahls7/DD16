package view;

import controller.CampaignEditController;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Create a panel for the creating campaign
 *
 * @author Ruijia Yang
 */
public class CreateCampaign extends JPanel {
    private CampaignEditController campaign_controller;
    private JPanel campaign_panel, setting_panel, map_view_panel;
    private JButton button_add, button_remove, button_save;
    private ArrayList<MapPanel> map_panel;
    private JComboBox<Integer> maps;
    private MapPanel current_map_panel, previous_map_panel;

    /**
     * Initiate a panel for campaign creation.
     */
    public CreateCampaign() {
        super(new GridLayout(1, 0));
        campaign_controller = new CampaignEditController();
        campaign_controller.createCampaign(false);
        map_panel = new ArrayList<MapPanel>();

        JLabel map_list_label = new JLabel("Map List:");
        setting_panel = new JPanel();
        setting_panel.add(map_list_label);
        maps = getMapList();
        setting_panel.add(maps);
        button_add = new JButton("Add the map");
        button_add.addActionListener(new addMap());
        button_remove = new JButton("Remove the map");
        button_remove.addActionListener(new removeMap());
        button_save = new JButton("Save compaign");
        button_save.addActionListener(new saveCampaign());
        setting_panel.add(button_add);
        setting_panel.add(button_remove);
        setting_panel.add(button_save);

        campaign_panel = new JPanel();

        add(campaign_panel);
        add(setting_panel);
    }

    /**
     * Get items from the file.
     *
     * @return
     */
    private JComboBox<Integer> getMapList() {

        JSONArray json_maps = campaign_controller.getMapList();
        JComboBox<Integer> maps = new JComboBox<Integer>();

        for (int i = 0; i < json_maps.length(); i++) {
            int map_id = json_maps.getJSONObject(i).getInt("id");
            maps.addItem(map_id);
        }
        return maps;
    }

    /**
     * Add all the map panels to the main panel
     *
     * @param list map panel list
     */
    public void drawMapList(ArrayList<MapPanel> list) {
        campaign_panel.removeAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                campaign_panel.add(list.get(i));
            }
            if (i != list.size() - 1) {
                JLabel arrow = new JLabel("==>");
                campaign_panel.add(arrow);
            }

        }
    }

    /**
     * Update the map panel list after modifying it
     */
    public void updateMapPanelIndex() {
        for (int i = 0; i < map_panel.size(); i++) {
            map_panel.get(i).setIndex(i);
        }
    }

    /**
     * Add a map to a campaign
     */
    class addMap implements ActionListener, MouseListener {
        /**
         * @param arg0
         */
        public void actionPerformed(ActionEvent arg0) {
            String selected_map_id = Integer.toString((int) maps.getSelectedItem());
            campaign_controller.addMap(selected_map_id);
            MapPanel temp_map_panel = new MapPanel(selected_map_id);
            temp_map_panel.setIndex(map_panel.size());
            temp_map_panel.addMouseListener(this);
            map_panel.add(temp_map_panel);
            drawMapList(map_panel);
            campaign_panel.revalidate();
            campaign_panel.repaint();
        }

        @Override
        /**
         * Actions after clicking mouse
         */
        public void mouseClicked(MouseEvent e) {
            previous_map_panel = current_map_panel;
            current_map_panel = (MapPanel) e.getSource();
            if (previous_map_panel == null) {
                current_map_panel.select();
            } else {
                previous_map_panel.deselect();
                current_map_panel.select();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * Remove a map from a campaign
     */
    class removeMap implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if (current_map_panel != null) {
                String remove_map_id = current_map_panel.getMapId();
                int remove_index = current_map_panel.getIndex();
                campaign_controller.removeMap(remove_map_id, remove_index);
                for (int i = 0; i < map_panel.size(); i++) {
                    if (map_panel.get(i).getMapId().equals(remove_map_id) && map_panel.get(i).getIndex() == current_map_panel.getIndex()) {
                        map_panel.remove(i);
                        break;
                    }
                }
                updateMapPanelIndex();
                drawMapList(map_panel);
                campaign_panel.revalidate();
                campaign_panel.repaint();
                current_map_panel = null;
                previous_map_panel = null;
            }

        }
    }

    /**
     * Save the campaign to the file
     */
    class saveCampaign implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            campaign_controller.saveCompaign();
        }
    }
}
