package model;

public class PItem {
    private int id, attribute_value;
    private String type, attribute;

    public PItem(int item_id, String type, String attribute, int attribute_value) {
        this.id = item_id;
        this.type = type;
        this.attribute = attribute;
        this.attribute_value = attribute_value;
    }
}
