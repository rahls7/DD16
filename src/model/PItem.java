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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttributeValue() {
        return attribute_value;
    }

    public void setAttributeValue(int attribute_value) {
        this.attribute_value = attribute_value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
