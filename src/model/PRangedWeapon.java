package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alleria on 2017/3/31.
 */
public class PRangedWeapon extends PWeapon {
    private String type;
    private String attribute;
    private List<String> enchantments;
    private int attribute_value;

    public PRangedWeapon(PItem item) {
        type = item.getType();
        attribute_value = item.getAttributeValue();

        String parts[] = item.getAttribute().split(",");
        String atts[] = parts[1].split(" ");
        attribute = parts[0];
    }

    public List<String> getEnchantments(){
        enchantments = new ArrayList<String>();
        return enchantments;
    }

    public String getType() { return type; }

    public int getAttributeValue() {
        return attribute_value;
    }
}
