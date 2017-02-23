package view;

/**
 * Created by rahls7 on 2017/2/11.
 */

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame {
    private About about_panel;
    private Map map_panel;
    private Item item_panel;
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

    /*private JPanel createJpanel() {
        JPanel pan =new MainPanel();
        //setLayout(new GridLayout(10,2));
        addButtons();
        return pan;

    }*/


    private JMenuBar createMenuBar(String name) {
        JMenuBar bar = new JMenuBar();
        bar.add(createMenu(name));
        return bar;
    }

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
                if(cmd.equals("Map Editor")) {
                    map_panel = new Map();
                    menuAction(map_panel);
                }else if(cmd.equals("Close")) {
                    System.exit(0);
                }else if(cmd.equals("Item Editor")) {
                    item_panel = new Item();
                    menuAction(item_panel);
                }else if(cmd.equals("About")) {
                    about_panel = new About();
                    menuAction(about_panel);
                }
            }
        };
        item_map.addActionListener(actionListener);
        item_item.addActionListener(actionListener);
        item_about.addActionListener(actionListener);



        return menu;
    }

    public void menuAction(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().doLayout();
        repaint();
        validate();
    }


}


class MainPanel extends JPanel {

    private Map mapPanel;
    private JButton buttonPlay, buttonMap, buttonCharacter, buttonCampaign, buttonAbout, buttonExit;



    public MainPanel() {

        setBorder(BorderFactory.createLineBorder(Color.black));
        //setSize(getPreferredSize());
        setLayout(new GridLayout(10,1));
    }

    public Dimension getPreferredSize() {
        return new Dimension(960, 600);
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw Image
        try {
            BufferedImage image = ImageIO.read(new File("src/images/MenuBackground.jpg"));
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

        } catch (IOException e) {
            e.printStackTrace();
        }
        addButtons();

    }




    public void addButtons() {
            buttonPlay = new JButton("P L A Y");
            add(buttonPlay);

            buttonMap = new JButton("M A P E D I T O R");
            buttonMap.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mapPanel = new Map();
                    menuAction(mapPanel);
                }
            });
            buttonMap.setForeground(Color.BLUE);
            buttonMap.setOpaque(false);
            buttonMap.setContentAreaFilled(false);
            buttonMap.setBorderPainted(false);
            add(buttonMap);


            JButton buttonCharacter = new JButton("C H A R A C T E R E D I T O R");

            add(buttonCharacter);

            buttonCampaign = new JButton("C A M P A I G N E D I T O R");

            add(buttonCampaign);

            buttonAbout = new JButton("A B O U T");
            add(buttonAbout);


            buttonExit = new JButton("E X I T");
            add(buttonExit);
            /*ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String cmd = e.getActionCommand();
                    if(cmd.equals("M A P E D I T O R")) {
                        mapPanel = new Map();
                        menuAction(mapPanel);
                        setLayout(new GridLayout(1,0));
                    }else if(cmd.equals("E X I T")) {
                        System.exit(0);
                    }
                }
            };

            buttonMap.addActionListener(actionListener); */
        }


    public void menuAction(JPanel panel) {
        Main.mainFrame.getContentPane().removeAll();
        Main.mainFrame.getContentPane().add(panel, BorderLayout.CENTER);
        Main.mainFrame.getContentPane().doLayout();
        repaint();
        validate();
    }






}



