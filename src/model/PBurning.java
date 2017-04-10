package model;

import java.util.List;

/**
 * Burning enchantment using Decorator pattern.
 *
 * @author Jiayao, Ruijia
 */
public class PBurning extends PWeaponDecorator {
    /**
     * Initialize burning
     * @param weapon
     */
    public PBurning(PWeapon weapon) {
        super(weapon);
    }

    @Override
    /**
     * Get enchantments
     * @return enchantments
     */
    public List<String> getEnchantments() {
        List<String> e = super.getEnchantments();
        e.add("Burning");
        return e;
    }

    /**
     * Get type
     * @return type
     */
    public String getType() { return super.getType();}
}
