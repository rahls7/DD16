package display;

/**
 * Created by Alleria on 2017/2/11.
 */

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Records and manipulate properties of the panel of a cell.
 */
public class CellPanel extends JPanel{
    public int x;
    public int y;
    private boolean isSelected = false;
    public String content = "";

    /**
     * Initiate a cell panel.
     *
     * @param x X Coordinate of the cell.
     * @param y Y Coordinate of the cell.
     */
    public CellPanel(int x, int y) {
        this.x = x;
        this.y = y;
        setBackground(Color.white);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        setBorder(blackline);
    }

    /**
     * Set the status of the cell panel to selected.
     */
    public void select() {
        this.setBorder(BorderFactory.createLineBorder(Color.red,6));
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
        if(content.equals("WALL")) {
            setBackground(Color.ORANGE);
        }
        else if(content.equals("ENTRY")) {
            setBackground(Color.GRAY);
        }
        else if(content.equals("EXIT")) {
            setBackground(Color.darkGray);
        }
    }

    /**
     * Remove the content of the cell panel.
     */
    public void removeContent() {
        this.content = "";
        setBackground(Color.white);
    }

}
