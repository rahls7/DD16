package model;

/**
 * Created by Alleria on 2017/2/11.
 */

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Cell {
    public int x;
    public int y;
    private boolean isSelected = false;
    public String content = "";

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void select() {
        this.isSelected=true;
    }

    public boolean isselected() {
        return this.isSelected;
    }

    public void deselect() {
        this.isSelected = false;
    }

    public void setContent(String content) {
        this.content = content;
        if(content.equals("WALL")) {

        }
        else if(content.equals("ENTRY")) {

        }
        else if(content.equals("EXIT")) {

        }
    }

    public void removeContent() {
        this.content = "";
    }

}
