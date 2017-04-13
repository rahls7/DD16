package model;

import java.util.List;

/**
 * Slaying enchantment using Decorator pattern.
 *
 * @author Jiayao, Ruijia
 */
public class PSlaying extends PWeaponDecorator {

    /**
     * Initialize a slaying
     * @param weapon Weapon instance
     */
    public PSlaying(PWeapon weapon) {
        super(weapon);
    }

    @Override
    /**
     * Get enchantments
     * @return List<String> enchantments
     */
    public List<String> getEnchantments() {
        List<String> e = super.getEnchantments();
        e.add("Slaying");
        return e;
    }

    /**
     * Get type
     * @return String type
     */
    public String getType() { return super.getType();}

    public int getAttributeValue() {
        return super.getAttributeValue();
    }
}
