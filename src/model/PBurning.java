package model;

import java.util.List;

/**
 * Burning enchantment using Decorator pattern.
 *
 * @author Jiayao
 */
public class PBurning extends PWeaponDecorator {

    public PBurning(PWeapon weapon) {
        super(weapon);
    }

    @Override
    public List<String> getEnchantments() {
        List<String> e = super.getEnchantments();
        e.add("Burning");
        return e;
    }

    public String getType() { return super.getType();}
}
