package view;

import controller.PlayController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alleria on 2017/3/11.
 */
public class PInformationPanel extends JPanel {
    private JLabel text;
    private PlayController play_controller;

    public PInformationPanel(PlayController play_controller) {
        super();
        this.play_controller = play_controller;
        text = new JLabel("Information");
        add(text);
    }

    public void showInformation(PCellPanel cell, boolean isAdjacent) {
        removeAll();
        String[] info = cell.content.split(" ");
        if(isAdjacent) {
            text = new JLabel(cell.content);
            add(text);
            if(info[0].equals("CHEST")) {
                JButton button_loot = new JButton("Loot Chest");
                button_loot.addActionListener(new lootChest(cell.x, cell.y));
                add(button_loot);
            }
            else if(info[0].equals("CHARACTER") && info[2].equals("0")) {
                JButton button_exchange = new JButton("Exchange Item");
            }
            else if(info[0].equals("CHARACTER") && info[2].equals("1")) {
                JButton button_attack = new JButton("Attack");

            }
            else if(info[0].equals("WALL")) {

            }
            else if(info[0].equals("EXIT")) {

            }
            else if(info[0].equals("PLAYER")) {

            }
        }
        else {
            text = new JLabel(info[0]);
            add(text);
        }
        revalidate();
        repaint();

    }

    class lootChest implements ActionListener {
        int x, y;
        lootChest(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void actionPerformed(ActionEvent arg0) {
            play_controller.lootChest(x, y);
        }
    }
}
