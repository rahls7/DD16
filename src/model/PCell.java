package model;

/**
 * Cell object of the map.
 *
 * @author Jiayao
 * @version 1.0.0
 */
public class PCell {
    private int x, y;
    private String type = "";
    private PCellContent content;

    /**
     * Initialize a cell Object
<<<<<<< Updated upstream
     *
     * @param i       x coordinate of the cell
     * @param j       y coordinate of the cell
=======
     * @param i x coordinate of the cell
     * @param j y coordinate of the cell
>>>>>>> Stashed changes
     * @param content content of the cell
     */
    public PCell(int i, int j, String content) {
        x = i;
        y = j;

        if (content != null) {
            String[] parts = content.split(" ");
            type = parts[0];

            if (type.equals("CHARACTER")) {
                this.content = new PCharacter(parts[1], parts[2]);
            } else if (type.equals("CHEST"))
                this.content = new PChest(parts[1]);
            else
                this.content = new PConstant(type);
        }
    }

    /**
     * Get the type
<<<<<<< Updated upstream
     *
=======
>>>>>>> Stashed changes
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Set the player
     *
     * @param player
     */
    public void setPlayer(PCharacter player) {
        if (type.equals("") || type.equals("ENTRY")) {
            content = player;
            type = "PLAYER";
        }
    }

    /**
     * Remove a player
     */
    public void removePlayer() {
        content = new PConstant("");
        type = "";
    }

    public void setCharacter(PCharacter pCharacter) {
        if (type.equals("")) {
            content = pCharacter;
            type = "CHARACTER";
        }
    }

    public void removeCharacter() {
        content = new PConstant("");
        type = "";
    }

    /**
     * Get the content of a cell
<<<<<<< Updated upstream
     *
=======
>>>>>>> Stashed changes
     * @return content of a cell
     */
    public PCellContent getContent() {
        return content;
    }

}
