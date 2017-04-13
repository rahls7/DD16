package model;

import java.util.List;

/**
 * Frightening enchantment using Decorator pattern.
 *
 * @author Jiayao
 */
public class PFrightening extends PWeaponDecorator {

    public PFrightening(PWeapon weapon) {
        super(weapon);
    }

    @Override
    public List<String> getEnchantments() {
        List<String> e = super.getEnchantments();
        e.add("Frightening");
        return e;
    }

    public String getType() { return super.getType();}

    public int getAttributeValue() {
        return super.getAttributeValue();
    }
}
