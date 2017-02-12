package display;

/**
 * Created by Alleria on 2017/2/11.
 */

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CellPanel extends JPanel{
    public int x;
    public int y;
    private boolean isSelected = false;
    public String content = "";

    public CellPanel(int x, int y) {
        this.x = x;
        this.y = y;
        setBackground(Color.white);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        setBorder(blackline);
    }

    public void select() {
        this.setBorder(BorderFactory.createLineBorder(Color.red,6));
        this.isSelected=true;
    }

    public boolean isselected() {
        return this.isSelected;
    }

    public void deselect() {
        Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);
        this.isSelected = false;
    }

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

    public void removeContent() {
        this.content = "";
        setBackground(Color.white);
    }

}
