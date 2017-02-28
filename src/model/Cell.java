package model;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Record information of a cell in a map. Change properties of a cell according to messages from MapEditorController
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */
public class Cell {
    public int x;
    public int y;
    private boolean isSelected = false;
    public String content = "";

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the status of a cell to selected if it is selected by users.
     */
    public void select() {
        this.isSelected=true;
    }

    /**
     * Check the status of a cell.
     *
     * @return True if the cell is selected, otherwise false.
     */
    public boolean isselected() {
        return this.isSelected;
    }

    /**
     * Set the status of a cell to not selected.
     */
    public void deselect() {
        this.isSelected = false;
    }

    /**
     * Set the content of a cell.
     *
     * @param content The content of the cell.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Remove the content of a cell.
     */
    public void removeContent() {
        this.content = "";
    }

}
