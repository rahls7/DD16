package model;


public class PCell {
    private int x, y;
    private String type = "";
    private PCellContent content;

    /**
     * Initialize a cell Object
     * @param i
     * @param j
     * @param content
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
     * Set the type
     * @param type
     */
    public void setType(String type) { this.type = type; }

    /**
     * Get the type
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Set the player
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

    /**
     * Get the object of a cell
     * @return
     */
    public PCellContent getContent() {
        return content;
    }

}
