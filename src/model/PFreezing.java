package model;

import java.util.List;

/**
 * Freezing enchantment using Decorator pattern.
 *
 * @author Jiayao
 */
public class PFreezing extends PWeaponDecorator {

    public PFreezing(PWeapon weapon) {
        super(weapon);
    }

    @Override
    public List<String> getEnchantments() {
        List<String> e = super.getEnchantments();
        e.add("Freezing");
        return e;
    }

    public String getType() { return super.getType();}
}
