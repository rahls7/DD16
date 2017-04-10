package model;

import java.util.List;

/**
 * The abstract class of weapon
 * @author Jiayao, Ruijia
 */
public abstract class PWeapon {
    /**
     * The abstract function of getting enchantments
     * @return List<String> weapons
     */
    public abstract List<String> getEnchantments();

    /**
     * The abstract function of getting type
     * @return String weapon type
     */
    public abstract String getType();
}
