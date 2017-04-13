package view;

import controller.CampaignEditController;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Create a campaign edit panel
 *
 * @author Ruijia Yang
 */
public class EditCampaign extends JPanel implements MouseListener {
    private CampaignEditController campaign_controller;
    private JPanel campaign_panel, setting_panel;
    private JButton button_add, button_replace, button_save, button_remove;
    private ArrayList<MapPanel> map_panel;
    private JComboBox<Integer> maps;
    private MapPanel current_map_panel, previous_map_panel;

    /**
     * Initialize a edit campaign panel
     *
     * @param campaign_id id of the campaign
     */
    public EditCampaign(int campaign_id) {
        super(new GridLayout(1, 0));
        campaign_controller = new CampaignEditController();
        JSONObject json_campaign = new JSONObject();
        json_campaign = campaign_controller.readCampaign(campaign_id);
        campaign_controller.createExistedCampaign(json_campaign, true);
        campaign_controller.setID(campaign_id);

        campaign_panel = new JPanel();
        drawMapList(json_campaign);

        JLabel map_list_label = new JLabel("Map List:");
        setting_panel = new JPanel();
        setting_panel.add(map_list_label);
        maps = getMapList();
        setting_panel.add(maps);
        button_add = new JButton("Add the map");
        button_add.addActionListener(new addMap());
        button_replace = new JButton("Replace the map");
        button_replace.addActionListener(new replaceMap());
        button_save = new JButton("Save campaign");
        button_save.addActionListener(new saveCampaign());
        button_remove = new JButton("Remove the map");
        button_remove.addActionListener(new removeMap());

        setting_panel.add(button_add);
        setting_panel.add(button_replace);
        setting_panel.add(button_save);
        setting_panel.add(button_remove);

        add(campaign_panel);
        add(setting_panel);
    }

    /**
     * Remove a map from a campaign
     */
    class removeMap implements ActionListener {
        public void actionPerformed(ActionEvent argu0) {
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
                updateMapList(map_panel);
                campaign_panel.revalidate();
                campaign_panel.repaint();
                current_map_panel = null;
                previous_map_panel = null;
            }
        }
    }

    /**
     * save the campaign to the file
     */
    class saveCampaign implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            campaign_controller.saveCompaign();
        }
    }

    @Override
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

    /**
     * Add a map to a campaign
     */
    class addMap implements ActionListener, MouseListener {
        public void actionPerformed(ActionEvent arg0) {
            String selected_map_id = Integer.toString((int) maps.getSelectedItem());
            campaign_controller.addMap(selected_map_id);
            MapPanel temp_map_panel = new MapPanel(selected_map_id);
            temp_map_panel.addMouseListener(this);
            temp_map_panel.setIndex(map_panel.size());
            map_panel.add(temp_map_panel);
            updateMapList(map_panel);
            campaign_panel.revalidate();
            campaign_panel.repaint();
        }

        @Override
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
     * Replace to map with a selected one
     */
    class replaceMap implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if (current_map_panel != null) {
                String selected_map_id = Integer.toString((int) maps.getSelectedItem());
                String remove_map_id = current_map_panel.getMapId();
                campaign_controller.replaceMap(selected_map_id, current_map_panel.getIndex());
                for (int i = 0; i < map_panel.size(); i++) {
                    if (map_panel.get(i).getMapId().equals(remove_map_id) && map_panel.get(i).getIndex() == current_map_panel.getIndex()) {
                        map_panel.get(i).setMapId(selected_map_id);
                        break;
                    }
                }
                updateMapList(map_panel);
                campaign_panel.revalidate();
                campaign_panel.repaint();
            }
        }
    }

    /**
     * Update the map panel list
     *
     * @param list list of map
     */
    public void updateMapList(ArrayList<MapPanel> list) {
        campaign_panel.removeAll();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIndex(i);
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
     * Get all the existing maps
     *
     * @return maps list
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
     * Draw the maps of a campaign when loading
     *
     * @param json_campaign // Add Description for all the warnings.
     */
    public void drawMapList(JSONObject json_campaign) {
        map_panel = new ArrayList<MapPanel>();
        JSONArray maps = json_campaign.getJSONArray("maps");
        for (int i = 0; i < maps.length(); i++) {
            int map_id = maps.getJSONObject(i).getInt("id");
            MapPanel m = new MapPanel(Integer.toString(map_id));
            m.setIndex(i);
            map_panel.add(m);
        }
        for (int i = 0; i < map_panel.size(); i++) {
            map_panel.get(i).addMouseListener(this);
            if (map_panel.get(i) != null) {
                campaign_panel.add(map_panel.get(i));
            }
            if (i != map_panel.size() - 1) {
                JLabel arrow = new JLabel("==>");
                campaign_panel.add(arrow);
            }
        }
    }
}