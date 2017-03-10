package model;

public class PChest extends PCellContent{
    private int item_id, x, y;
    private PItem item;
    private ItemIO itemio;
    public PChest(String id) {
        item_id = Integer.parseInt(id);
        itemio = new ItemIO();
        item = itemio.getPItem(item_id);
        type = "CHEST";
    }
}
