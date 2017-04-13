package view;

import controller.CampaignEditController;
import controller.MapEditorController;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Start page for entering the game.
 *
 * @author Wenyu Gu
 * @version 1.0.0
 *          <p>
 *          Created by Fish on 2017/3/11.
 */
public class Start extends JPanel {
    private JPanel start_panel;
    private CampaignEditController campaign_controller;
    private MapEditorController map_controller;

    /**
     * Create a start panel. The panel includes campaign options, character options and start game button.
     */

    public Start() {
        super(new GridLayout(1, 3));
        campaign_controller = new CampaignEditController();
        map_controller = new MapEditorController();
        start_panel = new JPanel();

        /*
        Making the panel transparent.
         */
        //start_panel.setBackground(new Color(0,0,0,0));

        JComboBox<Integer> campaign = getCampaignList();
        campaign.setPreferredSize(new Dimension(200, 50));
        campaign.setBorder(BorderFactory.createTitledBorder("Campaign"));
        start_panel.add(campaign);

        JComboBox<String> character = getCharacterList();
        character.setPreferredSize(new Dimension(200, 50));
        character.setBorder(BorderFactory.createTitledBorder("Character:"));
        start_panel.add(character);

        Checkbox checkbox = new Checkbox();
        start_panel.add(checkbox);

        JLabel label = new JLabel("Computer");
        start_panel.add(label);

        JButton start_game = new JButton("Start Game");
        start_game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int campaign_id = (int) campaign.getSelectedItem();
                String character_id = (String) character.getSelectedItem();

                Main.mainFrame.getContentPane().removeAll();
                Main.mainFrame.getContentPane().add(new Play(character_id, campaign_id), BorderLayout.CENTER);
                Main.mainFrame.getContentPane().doLayout();
                repaint();
                validate();
                Main.mainFrame.setVisible(true);
            }
        });
        start_panel.add(start_game);

        add(start_panel);
    }

    /**
     * Get the existing campaign list from the file.
     *
     * @return List of the campaigns.
     */
    public JComboBox<Integer> getCampaignList() {
        JComboBox<Integer> campaigns = new JComboBox<Integer>();
        JSONArray json_campaigns = campaign_controller.getCompaignList();
        for (int i = 0; i < json_campaigns.length(); i++) {
            int map_id = json_campaigns.getJSONObject(i).getInt("id");
            campaigns.addItem(map_id);
        }
        return campaigns;
    }

    /**
     * Get the existing character lsit from the file.
     *
     * @return List of the characters.
     */
    public JComboBox<String> getCharacterList() {
        JComboBox<String> characters = new JComboBox<String>();
        JSONArray json_items = map_controller.getCharacterList();

        for (int i = 0; i < json_items.length(); i++) {
            String character_id = json_items.getJSONObject(i).getString("id");
            characters.addItem(character_id);
        }
        return characters;
    }
}
