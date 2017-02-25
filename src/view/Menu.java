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
    private JButton buttonPlay, buttonMap, buttonCharacter, buttonCampaign, buttonAbout, buttonExit;


    /**
     * Constructor. Needs to be called where JPanel needs to be added.
     */
    public Menu() {

        setBorder(BorderFactory.createLineBorder(Color.black));
        //setSize(getPreferredSize());
        setLayout(new GridLayout(10,1));
        //addButtons();
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
        buttonPlay = new JButton("P L A Y");
        add(buttonPlay);

        buttonMap = new JButton("M A P E D I T O R");
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

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                if(cmd.equals("M A P E D I T O R")) {
                    mapPanel = new Map();
                    Main.mainFrame.getContentPane().add(mapPanel);
                    //setLayout(new GridLayout(1,0));
                }else if(cmd.equals("E X I T")) {
                    System.exit(0);
                }
            }
        };

        buttonMap.addActionListener(actionListener);
        buttonExit.addActionListener(actionListener);
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
        Main.mainFrame.getContentPane().removeAll();
        System.out.println("Menu MenuAction Caled");
        Main.mainFrame.getContentPane().add(panel);
        Main.mainFrame.getContentPane().doLayout();
        repaint();
        validate();
    }


}


