package model;

import java.util.List;

/**
 * Pacifying enchantment using Decorator pattern.
 *
 * @author Jiayao, Ruijia
 */
public class PPacifying extends PWeaponDecorator {
    /**
     * Initialize a pacifying
     * @param weapon weapon instance
     */
    public PPacifying(PWeapon weapon) {
        super(weapon);
    }

    @Override
    /**
     * Get enchantments
     * @return enchantments
     */
    public List<String> getEnchantments() {
        List<String> e = super.getEnchantments();
        e.add("Pacifying");
        return e;
    }

    /**
     * Get type
     * @return type
     */
    public String getType() { return super.getType();}
}
