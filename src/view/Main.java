package view;
//import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rahls7 on 2017/2/11.
 * This class extends JFrame and displays the UI for creating Maps, Characters, Items and Campaign. It uses Swing
 * Library of Java and creates a JMenu.
 */

public class Main extends JFrame {
    private About about_panel;
    private Map map_panel;
    private Item item_panel;
    private Character character_panel;
    private Campaign campaign_panel;
    public static Main mainFrame;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mainFrame = new Main();
            }
        });
    }

    /**
     * Constructor for Main class. Creates an instance of Main, should be called whenever there is a need to display the
     * view.
     */

    public Main() {
        super("Dragon and Dungeons");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Menu());
        setJMenuBar(createMenuBar("Main Menu"));
        pack();
        setVisible(true);
    }

    /**
     * Creates a JmenuBar for the frame.
     * @param name Name for the Menu Bar.
     * @return bar JMenuBar
     */


    private JMenuBar createMenuBar(String name) {
        JMenuBar bar = new JMenuBar();
        bar.add(createMenu(name));
        return bar;
    }

    /**
     * Creates the menu for the JMenuBar. Over rides the Action Listener methods of JButton.
     * @param name
     * @return menu Menu with options to create Map, Character, Campaign.
     */

    private JMenu createMenu(String name) {
        JMenu menu = new JMenu(name);

        JMenuItem item_play = new JMenuItem("Play");


        JMenuItem item_map = new JMenuItem("Map Editor");


        JMenuItem item_compaign = new JMenuItem("Campaign Editor");


        JMenuItem item_character = new JMenuItem("Character Editor");


        JMenuItem item_item = new JMenuItem("Item Editor");


        JMenuItem item_about = new JMenuItem("About");


        JMenuItem item_close = new JMenuItem("Close");


        menu.add(item_play);
        menu.add(item_map);
        menu.add(item_compaign);
        menu.add(item_character);
        menu.add(item_item);
        menu.add(item_about);
        menu.add(item_close);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                System.out.println(cmd);
                if(cmd.equals("Map Editor")) {
                    map_panel = new Map();
                    menuAction(map_panel);
                }else if(cmd.equals("Item Editor")) {
                    item_panel = new Item();
                    menuAction(item_panel);
                }else if(cmd.equals("About")) {
                    about_panel = new About();
                    menuAction(about_panel);
                }else if(cmd.equals("Close")) {
                    System.exit(0);
                }else if(cmd.equals("Character Editor")) {
                    character_panel = new Character();
                    System.out.println(cmd);
                    menuAction(character_panel);
                }else if(cmd.equals("Campaign Editor")) {
                    campaign_panel = new Campaign();
                    System.out.println(cmd);
                    menuAction(campaign_panel);
                }
            }
        };
        item_map.addActionListener(actionListener);
        item_item.addActionListener(actionListener);
        item_about.addActionListener(actionListener);
        item_character.addActionListener(actionListener);
        item_compaign.addActionListener(actionListener);
        item_close.addActionListener(actionListener);
        
        return menu;
    }

    /**
     * Resets all the content of the JFrame to switch between different JPanel
     * @param panel Panel that needs to be displayed. For eg: Map Panel can be created as:
     *              <code>
     *              map_panel = new Map();
     *              menuAction(map_panel);
     *              </code>
     */

    public void menuAction(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().doLayout();
        repaint();
        validate();
    }


}

