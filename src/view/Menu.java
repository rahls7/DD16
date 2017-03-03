package view;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by rahls7 on 2017/02/14
 * Creates a JPanel with Buttons and Background to provide UI for User.
 * Its instance is used in Main view to display the Interactive Menu
 */

public class Menu extends JPanel {

    private Map mapPanel;
    private Character charPanel;
    private Campaign camPanel;
    private About aboutPanel;
    private JButton buttonPlay, buttonMap, buttonCharacter, buttonCampaign, buttonAbout, buttonExit;


    /**
     * Constructor. Needs to be called where JPanel needs to be added.
     */
    public Menu() {

        setBorder(BorderFactory.createLineBorder(Color.black));
        //setSize(getPreferredSize());
        setLayout(new GridLayout(10,1));
        addButtons();
    }

    /**
     * Getter function. Specifies the dimensions of the JPanel window.
     * @return Dimension.
     */

    public Dimension getPreferredSize() {
        return new Dimension(960, 600);
    }

    /**
     * Adds the Background image on the JPanel.
     * @param g Graphics Object
     */

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw Image
        try {
            BufferedImage image = ImageIO.read(new File("src/images/MenuBackground.jpg"));
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Adds Buttons which creates new instance of other JPanels. Overrides ActionListener method to switch between JPanel.
     */
    public void addButtons() {
        buttonPlay = new JButton("P l a y");
        buttonPlay.setForeground(Color.BLUE);
        buttonPlay.setOpaque(false);
        buttonPlay.setContentAreaFilled(false);
        buttonPlay.setBorderPainted(false);
        buttonPlay.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPlay.setFont(new Font("Times New Roman", Font.BOLD, 40));
        add(buttonPlay);

        buttonMap = new JButton("M a p E d i t o r");
        buttonMap.setForeground(Color.BLUE);
        buttonMap.setOpaque(false);
        buttonMap.setContentAreaFilled(false);
        buttonMap.setBorderPainted(false);
        buttonMap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonMap.setFont(new Font("Times New Roman", Font.BOLD,40));
        add(buttonMap);


        buttonCharacter = new JButton("C h a r a c t e r E d i t o r");
        buttonCharacter.setForeground(Color.BLUE);
        buttonCharacter.setOpaque(false);
        buttonCharacter.setContentAreaFilled(false);
        buttonCharacter.setBorderPainted(false);
        buttonCharacter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonCharacter.setFont(new Font("Times New Roman", Font.BOLD,40));
        add(buttonCharacter);

        buttonCampaign = new JButton("C a m p a i g n E d i t o r");
        buttonCampaign.setForeground(Color.BLUE);
        buttonCampaign.setOpaque(false);
        buttonCampaign.setContentAreaFilled(false);
        buttonCampaign.setBorderPainted(false);
        buttonCampaign.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonCampaign.setFont(new Font("Times New Roman", Font.BOLD,40));
        add(buttonCampaign);

        buttonAbout = new JButton("A b o u t");
        buttonAbout.setForeground(Color.BLUE);
        buttonAbout.setOpaque(false);
        buttonAbout.setContentAreaFilled(false);
        buttonAbout.setBorderPainted(false);
        buttonAbout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonAbout.setFont(new Font("Times New Roman", Font.BOLD,40));
        add(buttonAbout);


        buttonExit = new JButton("E x i t");
        buttonExit.setForeground(Color.BLUE);
        buttonExit.setOpaque(false);
        buttonExit.setContentAreaFilled(false);
        buttonExit.setBorderPainted(false);
        buttonExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonExit.setFont(new Font("Times New Roman", Font.BOLD,40));
        add(buttonExit);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                if(cmd.equals("M a p E d i t o r")) {
                    mapPanel = new Map();
                    menuAction(mapPanel);
                    //setLayout(new GridLayout(1,0));
                }else if(cmd.equals("E x i t")) {
                    System.exit(0);
                }else if(cmd.equals("C h a r a c t e r E d i t o r")) {
                    charPanel = new Character();
                    menuAction(charPanel);
                }else if(cmd.equals("C a m p a i g n E d i t o r")) {
                    camPanel = new Campaign();
                    menuAction(camPanel);
                }else if(cmd.equals("A b o u t")) {
                    aboutPanel = new About();
                    menuAction(aboutPanel);
                }
            }
        };

        buttonMap.addActionListener(actionListener);
        buttonExit.addActionListener(actionListener);
        buttonCharacter.addActionListener(actionListener);
        buttonAbout.addActionListener(actionListener);
        buttonCampaign.addActionListener(actionListener);
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
        removeAll();
        System.out.println("Menu MenuAction Caled");
        add(panel);
        doLayout();
        repaint();
        validate();
    }


}


