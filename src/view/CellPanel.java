package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Records and manipulate properties of the panel of a cell.
 *
 * @author Jiayao Zhou
 * @version 1.0.1
 */
public class CellPanel extends JPanel {
    public int x;
    public int y;
    public String content = "";
    private boolean isSelected = false;
    private Image img;

    /**
     * Initiate a cell panel.
     *
     * @param x X Coordinate of the cell.
     * @param y Y Coordinate of the cell.
     */
    public CellPanel(int x, int y) {
        this.x = x;
        this.y = y;
        Border blackline = BorderFactory.createLineBorder(Color.black);
        setBorder(blackline);
        ImageIcon pic = new ImageIcon("src/images/floor.jpg");
        img = pic.getImage();
        repaint();
    }

    /**
     * Set the status of the cell panel to selected.
     */
    public void select() {
        this.setBorder(BorderFactory.createLineBorder(Color.red));
        this.isSelected = true;
    }

    /**
     * Check the status of the cell panel.
     *
     * @return True if the cell panel is selected, otherwise false.
     */
    public boolean isselected() {
        return this.isSelected;
    }

    /**
     * Set the status of the cell panel to not selected.
     */
    public void deselect() {
        Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);
        this.isSelected = false;
    }

    /**
     * Set the content of the cell panel.
     *
     * @param content The content of the cell panel.
     */
    public void setContent(String content) {
        this.content = content;
        if (content.equals("WALL")) {
            ImageIcon pic = new ImageIcon("src/images/wall.png");
            img = pic.getImage();
            repaint();
        } else if (content.equals("ENTRY")) {
            ImageIcon pic = new ImageIcon("src/images/entry.png");
            img = pic.getImage();
            repaint();
        } else if (content.equals("EXIT")) {
            ImageIcon pic = new ImageIcon("src/images/exit.png");
            img = pic.getImage();
            repaint();
        } else if (content.equals("CHEST") || (content.length() > 5 && content.substring(0, 5).equals("CHEST"))) {
            ImageIcon pic = new ImageIcon("src/images/chest.jpg");
            img = pic.getImage();
            repaint();
        } else if (content.length() > 9 && content.substring(0, 9).equals("CHARACTER")) {
            ImageIcon pic;
            String[] parts = content.split(" ");
            if (parts[2].equals("0"))
                pic = new ImageIcon("src/images/friend.png");
            else
                pic = new ImageIcon("src/images/enemy.png");
            img = pic.getImage();
            repaint();
        }
    }

    /**
     * Remove the content of the cell panel.
     */
    public void removeContent() {
        content = "";
        ImageIcon pic = new ImageIcon("src/images/floor.jpg");
        img = pic.getImage();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }
}
