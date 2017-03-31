package unittest;

import model.Character;
import model.CharacterIO;
import model.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by mo on 2017-02-25.
 * This is a test for character content manipulation.
 *
 * @author mo
 * @version 1.0.0
 */

public class CharacterContentClass {

    Character character;
    CharacterIO characterIO = new CharacterIO();
    Item item;

    /**
     * Set the test context
     */
    @Before
    public void before() {
        character = new Character("123");
        character.initiateStats("Bully");
        character.setIsSaved(true);
        item = new Item("Ring", "Damage Bonus", 5);
    }

    /**
     * Test if the character is saved
     */
    @Test
    public void testSetIsSaved() {
        assertEquals("true", character.getIsSaved() + "");
    }

    /**
     * Test if character's ID is correct
     */
    @Test
    public void testGetID() {
        assertEquals("123", character.getId());
    }

    /**
     * Test set name function and get name function
     */
    @Test
    public void testName() {
        String name = new String("Rahul");
        character.setName(name);
        assertEquals(name, character.getName());
    }

    /**
     * Test that after character wears an equipment, whether the attributes would change as expected
     * test setEquipment, getAttributes together.
     */
    @Test
    public void testAttribute() {
        int[] attributesBefore, attributesAfter;
        attributesBefore = new int[6];
        attributesAfter = new int[6];
        attributesBefore = character.getAttributes();
        int damageBonus = attributesBefore[4];
        character.setEquipment(item);
        character.recalculateStats();
        attributesAfter = character.getAttributes();
        assertEquals(attributesAfter[4], damageBonus);
        character.deleteEquipment(item);
        character.recalculateStats();
    }

    /**
     * Test setEquipment and deleteEquipment
     */
    @Test
    public void testEquipment() {
        ArrayList<Item> equipment = new ArrayList<Item>();
        equipment = character.getEquipment();
        character.setEquipment(item);
        character.deleteEquipment(item);
        assertEquals(equipment, character.getEquipment());
    }

    /**
     * Test if the operation to backpack works correctly
     */
    @Test
    public void testBackpack() {
        ArrayList<Item> backpack = new ArrayList<Item>();
        backpack = character.getBackpack();
        character.setBackpack(item);
        character.removeBackpack(item);
        assertEquals(backpack, character.getBackpack());
    }

    /**
     * Test that man could only wear one equipment of the same kind
     */
    @Test
    public void testWearEquipment() {
        Item item_Ring = new Item("Ring", "Damage Bonus", 3);
        int[] attributesBefore, attributesAfter;
        attributesBefore = new int[6];
        attributesAfter = new int[6];
        attributesBefore = character.getAttributes();
        int damageBonus = attributesBefore[4];
        character.setEquipment(item);
        character.setEquipment(item_Ring);
        character.recalculateStats();
        attributesAfter = character.getAttributes();
        assertEquals(attributesAfter[4], damageBonus);
        character.deleteEquipment(item);
        character.recalculateStats();
    }
}
