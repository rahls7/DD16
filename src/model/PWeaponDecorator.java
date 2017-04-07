package model;

import java.util.List;

/**
 * Created by Alleria on 2017/3/30.
 */
public abstract class PWeaponDecorator extends PWeapon {
    protected final PWeapon weapon;

    public PWeaponDecorator(PWeapon weapon) {
        this.weapon = weapon;
    }

    public List<String> getEnchantments(){
        return weapon.getEnchantments();
    }

    public String getType() { return weapon.getType();}
}
