package view;

/**
 * Created by Alleria on 2017/2/11.
 */

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{
    private About about_panel;
    private Map map_panel;
    private Item item_panel;
    private Campaign campaign_panel;
    public static Main mainFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mainFrame = new Main();
            }
        });
    }

    public Main() {
        super("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new MainPanel());
        setJMenuBar(createMenuBar("Main Menu"));
        pack();
        setVisible(true);
    }


    private JMenuBar createMenuBar(String name) {
        JMenuBar bar = new JMenuBar();
        bar.add(createMenu(name));
        return bar;
    }

    private JMenu createMenu(String name) {
        JMenu menu = new JMenu(name);

        JMenuItem item_play = new JMenuItem("Play");
        item_play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JMenuItem item_map = new JMenuItem("Map Editor");
        item_map.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map_panel = new Map();
                menuAction(map_panel);
            }
        });

        JMenuItem item_compaign = new JMenuItem("Campaign Editor");
        item_compaign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campaign_panel = new Campaign();
                menuAction(campaign_panel);
            }
        });

        JMenuItem item_character = new JMenuItem("Character Editor");
        item_character.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JMenuItem item_item = new JMenuItem("Item Editor");
        item_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item_panel = new Item();
                menuAction(item_panel);
            }
        });

        JMenuItem item_about = new JMenuItem("About");
        item_about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                about_panel = new About();
                menuAction(about_panel);
            }
        });

        JMenuItem item_close = new JMenuItem("Close");
        item_close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
            }
        });

        menu.add(item_play);
        menu.add(item_map);
        menu.add(item_compaign);
        menu.add(item_character);
        menu.add(item_item);
        menu.add(item_about);
        menu.add(item_close);

        return menu;
    }

    private void menuAction(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().doLayout();
        repaint();
        validate();
    }
}


class MainPanel extends JPanel {

    public MainPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(960,600);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw Text
        g.drawString("Hello!",10,20);
    }
}


