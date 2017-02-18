package model;

/**
 * Created by Alleria on 2017/2/17.
 */
public class Item {

    private String type;
    private String attribute;
    private int attribute_value;
    private boolean isSaved;
    private int save_id;

    public Item() {
        type = null;
        attribute = null;
        attribute_value = 0;
    }

    public Item(String type, String attribute, int attribute_value) {
        this.type = type;
        this.attribute = attribute;
        this.attribute_value = attribute_value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        String type = this.type;
        return type;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        String attribute = this.attribute;
        return attribute;
    }

    public void setAttributeValue(int attribute_value) {
        this.attribute_value = attribute_value;
    }

    public int getAttributeValue() {
        int attribute_value = this.attribute_value;
        return attribute_value;
    }

    public void setIsSaved(boolean is_Saved) {
        this.isSaved = is_Saved;
    }

    public boolean getIsSaved() {
        boolean is_Saved = this.isSaved;
        return is_Saved;
    }
    public void setSaveId(int save_id) {
        this.save_id = save_id;
    }

    public int getSaveId() {
        int save_id = this.save_id;
        return save_id;
    }
}
