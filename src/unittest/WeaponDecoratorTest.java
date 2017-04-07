package unittest;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests for Weapon Decorator.
 *
 * @author Jiayao
 */
public class WeaponDecoratorTest {
    PItem item;
    PWeapon weapon;

    @Before
    public void before() {
        item = new PItem(0, "Ranged Weapon", "Attack Bonus, Freezing Burning Pacifying", 4);
    }

    @Test
    public void testDecorator() {
        weapon = new PRangedWeapon(item);
        weapon = new PFreezing(weapon);
        weapon = new PBurning(weapon);
        weapon = new PPacifying(weapon);

        List<String> expected = new ArrayList<String>();
        expected.add("Freezing");
        expected.add("Burning");
        expected.add("Pacifying");

        assertEquals(expected, weapon.getEnchantments());
    }
}
