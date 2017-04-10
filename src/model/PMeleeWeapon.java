package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Melee Weapon class
 * @author Jiayao, Ruijia
 */
public class PMeleeWeapon extends PWeapon {
    private String type;
    private String attribute;
    private List<String> enchantments;
    private int attribute_value;

    /**
     * Initialize a Melee Weapon
     * @param item
     */
    public PMeleeWeapon(PItem item) {
        type = item.getType();
        attribute_value = item.getAttributeValue();

        String parts[] = item.getAttribute().split(",");
        String atts[] = parts[1].split(" ");
        attribute = parts[0];
    }

    /**
     * Get enchantments
     * @return enchantments
     */
    public List<String> getEnchantments(){
        enchantments = new ArrayList<String>();
        return enchantments;
    }

    /**
     * Get type
     * @return type
     */
    public String getType() { return type; }
}
