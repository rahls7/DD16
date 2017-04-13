package model;

/**
 * Item object.
 *
 * @author Jiayao
 * @version 1.0.0
 */
public class PItem {
    private int id, attribute_value;
    private String type, attribute;

    /**
     * Initialize a item model of play
     *
     * @param item_id item id
     * @param type item id
     * @param attribute item attribute
     * @param attribute_value item attribute value
     */
    public PItem(int item_id, String type, String attribute, int attribute_value) {
        this.id = item_id;
        this.type = type;
        this.attribute = attribute;
        this.attribute_value = attribute_value;
    }

    /**
     * Get the id
     * @return item id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id
     *
     * @param id item id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the value of attribute
     * @return Attribute value
     */
    public int getAttributeValue() {
        return attribute_value;
    }

    /**
     * Set the value of attribute
     *
     * @param attribute_value attribute value
     */
    public void setAttributeValue(int attribute_value) {
        this.attribute_value = attribute_value;
    }

    /**
     * Get the type
     * @return type returns the type of Item
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type
     *
     * @param type item type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the attriute
     * @return attribute
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Set the attribute
     *
     * @param attribute attribute type
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
