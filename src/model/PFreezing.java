package model;

import java.util.List;

/**
 * Freezing enchantment using Decorator pattern.
 *
 * @author Jiayao,Ruijia
 */
public class PFreezing extends PWeaponDecorator {
    /**
     * Initialize a Freezing
     * @param weapon
     */
    public PFreezing(PWeapon weapon) {
        super(weapon);
    }

    @Override
    /**
     * Get enchantments
     * @return enchantments
     */
    public List<String> getEnchantments() {
        List<String> e = super.getEnchantments();
        e.add("Freezing");
        return e;
    }

    /**
     * Get type
     * @return type
     */
    public String getType() { return super.getType();}
}
