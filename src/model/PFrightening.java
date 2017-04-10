package model;

import java.util.List;

/**
 * Frightening enchantment using Decorator pattern.
 *
 * @author Jiayao,Ruijia
 */
public class PFrightening extends PWeaponDecorator {
    /**
     * Initialize a frightening
     * @param weapon
     */
    public PFrightening(PWeapon weapon) {
        super(weapon);
    }

    @Override
    /**
     * Get enchantments
     * @return enchantments
     */
    public List<String> getEnchantments() {
        List<String> e = super.getEnchantments();
        e.add("Frightening");
        return e;
    }

    /**
     * Get type
     * @return type
     */
    public String getType() { return super.getType();}
}
