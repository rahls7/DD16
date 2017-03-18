package view;

import controller.PlayController;
import model.PCharacter;
import model.PItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Alleria on 2017/3/11.
 */
public class PInformationPanel extends JPanel {
    private JLabel text;
    private PlayController play_controller;
    private JButton button_exchange;
    private JComboBox exchangeItBox;
    private JButton button_attack;
    private JButton loot_enemy;
    private JButton button_loot;
    private JComboBox lootEnemyItemBox;
    private PCellPanel cell;

    public PInformationPanel(PlayController play_controller) {
        super();
        this.play_controller = play_controller;
        text = new JLabel("Information");
        add(text);
    }

    public void showInformation(PCellPanel cell, boolean isAdjacent) {
        this.cell = cell;
        removeAll();
        String[] info = cell.content.split(" ");

        if(isAdjacent) {
            text = new JLabel(info[0]);
            add(text);
            if(info[0].equals("CHEST")) {
                button_loot = new JButton("Loot Chest");
                button_loot.addActionListener(new lootChest(cell.x, cell.y));
                add(button_loot);
            }
            else if(info[0].equals("CHARACTER") && info[2].equals("0")) {
                button_exchange = new JButton("Exchange Item");
                button_exchange.addActionListener(new exchangeItem(cell.x, cell.y));
                ArrayList<PItem> it = play_controller.getPlayerItem();
                exchangeItBox = new JComboBox();
                for(PItem item: it) {
                    exchangeItBox.addItem(item.getType()+ " "+ item.getAttribute() + " " + item.getAttributeValue());
                }
                add(exchangeItBox);
                add(button_exchange);

            }
            else if(info[0].equals("CHARACTER") && info[2].equals("1")) {
                if(play_controller.getEnemyHitPoint(cell.x, cell.y)==0) {
                    loot_enemy = new JButton("Loot Enemy");
                    loot_enemy.addActionListener(new lootEnemy(cell.x,cell.y));
                    ArrayList<PItem> enemyItem = play_controller.getEnemyItem(cell.x, cell.y);
                    lootEnemyItemBox = new JComboBox();
                    for(PItem item : enemyItem) {
                        lootEnemyItemBox.addItem(item.getType() + " " + item.getAttribute() + " " + item.getAttributeValue());
                    }
                    add(lootEnemyItemBox);
                    add(loot_enemy);
                }else {
                    button_attack = new JButton("Attack");
                    button_attack.addActionListener(new attackEnemy(cell.x,cell.y));
                    add(button_attack);
                }


            } else if (info[0].equals("WALL")) {

            }


            else if(info[0].equals("EXIT")) {

            } else if (info[0].equals("PLAYER")) {

            }
        } else {
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
            JOptionPane.showMessageDialog(button_loot,"Chest Looted");
        }
    }

    class exchangeItem implements ActionListener{
        int x, y;
        exchangeItem(int x, int y) {
            this.x = x;
            this.y = y;
        }


        public void actionPerformed(ActionEvent event) {
            if(event.getSource()== button_exchange) {
                int index = exchangeItBox.getSelectedIndex();
                play_controller.exchangeItem(x,y,index);
                JOptionPane.showMessageDialog(button_exchange,"Item Exchanged Successfully");
                showInformation(cell, true);
                revalidate();
                repaint();
            }

        }
    }

    class attackEnemy implements ActionListener {
        int x, y;
        attackEnemy(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void actionPerformed(ActionEvent event) {
            play_controller.attackEnemy(x,y);
            JOptionPane.showMessageDialog(button_attack,"Enemy Dead");
            showInformation(cell, true);
            revalidate();
            repaint();

        }
    }

    class lootEnemy implements ActionListener {
        int x, y;
        lootEnemy(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void actionPerformed(ActionEvent event) {
            int selIndex = lootEnemyItemBox.getSelectedIndex();
            play_controller.lootEnemy(x,y,selIndex );
            JOptionPane.showMessageDialog(loot_enemy, "Enemy Looted");
            showInformation(cell, true);
            revalidate();
            repaint();
        }
    }
}
