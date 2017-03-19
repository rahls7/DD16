package model;

public class PChest extends PCellContent {
    private int item_id, x, y;
    private PItem item;
    private ItemIO itemio;

    /**
     * Initiallize a chest model of play
     * @param id
     */
    public PChest(String id) {
        item_id = Integer.parseInt(id);
        itemio = new ItemIO();
        item = itemio.getPItem(item_id);
        type = "CHEST";
    }

    /**
     * Get the item of a chest
     * @return
     */
    public PItem getItem() {
        return item;
    }

    /**
     * Remove the item from the chest
     */
    public void removeItem() {
        item = null;
    }
}
