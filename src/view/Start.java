package view;

import controller.CampaignEditController;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Fish on 2017/3/11.
 */
public class Start extends JPanel {
    private JPanel start_panel;
    private CampaignEditController campaign_controller;

    /**
     * Create a start panel
     */
    public Start (){
        super(new GridLayout(1, 0));
        campaign_controller = new CampaignEditController();

        start_panel = new JPanel();
        JComboBox<Integer> campaign = getCampaignList();
        start_panel.add(campaign);

        JButton start_game = new JButton("Start Game");
        start_panel.add(start_game);

        add(start_panel);
    }

    /**
     *Get the existing campaign list
     * @return
     */
    public JComboBox<Integer> getCampaignList(){
        JComboBox<Integer> campaigns = new JComboBox<Integer>();
        JSONArray json_campaigns = campaign_controller.getCompaignList();
        for (int i = 0; i < json_campaigns.length(); i++) {
            int map_id = json_campaigns.getJSONObject(i).getInt("id");
            campaigns.addItem(map_id);
        }
        return campaigns;
    }
}
