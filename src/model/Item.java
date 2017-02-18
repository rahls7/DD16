package model;

/**
 * Item model to set values of properties of items.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */
public class Item {

    private String type;
    private String attribute;
    private int attribute_value;
    private boolean isSaved;
    private int save_id;

    /**
     * Initiate an object of Item.
     */
    public Item() {
        type = null;
        attribute = null;
        attribute_value = 0;
    }

    /**
     * Initiate an object of Item.
     */
    public Item(String type, String attribute, int attribute_value) {
        this.type = type;
        this.attribute = attribute;
        this.attribute_value = attribute_value;
    }

    /**
     * Set type of the item.
     *
     * @param type Type of the item.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get type of the item.
     *
     * @return Type of the item.
     */
    public String getType() {
        String type = this.type;
        return type;
    }

    /**
     * Set attribute of the item.
     *
     * @param attribute Attribute of the item.
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * Get attribute of the item.
     *
     * @return Attribute of the item.
     */
    public String getAttribute() {
        String attribute = this.attribute;
        return attribute;
    }

    /**
     * Set value of the attribute.
     *
     * @param attribute_value Value of the attribute.
     */
    public void setAttributeValue(int attribute_value) {
        this.attribute_value = attribute_value;
    }

    /**
     * Get value of the attribute.
     *
     * @return Value of the attribute.
     */
    public int getAttributeValue() {
        int attribute_value = this.attribute_value;
        return attribute_value;
    }

    /**
     * Set the status showing if the item had been saved in the file.
     *
     * @param is_Saved True if the has been saved, otherwise false.
     */
    public void setIsSaved(boolean is_Saved) {
        this.isSaved = is_Saved;
    }

    /**
     * Get the status showing if the item had been saved in the file.
     *
     * @return True if the has been saved, otherwise false.
     */
    public boolean getIsSaved() {
        boolean is_Saved = this.isSaved;
        return is_Saved;
    }

    /**
     * Set id of the item if it's been saved.
     *
     * @param save_id Id of the item.
     */
    public void setSaveId(int save_id) {
        this.save_id = save_id;
    }

    /**
     * Get id of the item if it's been saved.
     *
     * @return Id of the item.
     */
    public int getSaveId() {
        int save_id = this.save_id;
        return save_id;
    }
}
