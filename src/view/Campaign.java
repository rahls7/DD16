package view;

import Controller.CampaignEditController;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ruijia on 2017/2/18.
 */
public class Campaign extends JPanel {
    private JPanel create_panel, edit_panel;
    private CampaignEditController campaign_controller;

    /**
     *
     */
    public Campaign(){
        super(new GridLayout(1,0));
        campaign_controller= new CampaignEditController();
        JButton button_create = new JButton("Create Campaign");
        button_create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.mainFrame.getContentPane().removeAll();
                Main.mainFrame.getContentPane().add(new CreateCampaign(), BorderLayout.CENTER);
                Main.mainFrame.getContentPane().doLayout();
                repaint();
                validate();
                Main.mainFrame.setVisible(true);

            }
        });

        create_panel = new JPanel();
        create_panel.add(button_create);

        edit_panel=new JPanel();
        JComboBox<Integer> campaigns = getCampaignList();
        edit_panel.add(campaigns);
        JButton button_edit = new JButton("Edit Campaign");
        button_edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int compaign_id = (int)campaigns.getSelectedItem();
                Main.mainFrame.getContentPane().removeAll();
                Main.mainFrame.getContentPane().add(new EditCampaign(compaign_id), BorderLayout.CENTER);
                Main.mainFrame.getContentPane().doLayout();
                repaint();
                validate();
                Main.mainFrame.setVisible(true);
            }
        });

        edit_panel.add(button_edit);
        add(create_panel);
        add(edit_panel);
    }

    /**
     *
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
