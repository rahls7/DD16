package model;

import java.util.List;

/**
 * Pacifying enchantment using Decorator pattern.
 *
 * @author Jiayao
 */
public class PPacifying extends PWeaponDecorator {

    public PPacifying(PWeapon weapon) {
        super(weapon);
    }

    @Override
    public List<String> getEnchantments() {
        List<String> e = super.getEnchantments();
        e.add("Pacifying");
        return e;
    }

    public String getType() { return super.getType();}
}
