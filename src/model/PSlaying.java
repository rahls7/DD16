package model;

import java.util.List;

/**
 * Slaying enchantment using Decorator pattern.
 *
 * @author Jiayao
 */
public class PSlaying extends PWeaponDecorator {

    public PSlaying(PWeapon weapon) {
        super(weapon);
    }

    @Override
    public List<String> getEnchantments() {
        List<String> e = super.getEnchantments();
        e.add("Slaying");
        return e;
    }

    public String getType() { return super.getType();}

    public int getAttributeValue() {
        return super.getAttributeValue();
    }
}
