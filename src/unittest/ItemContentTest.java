package unittest;

import model.Item;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

/**
 * Test functions for item content manipulation.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */
public class ItemContentTest {
    Item item;

    /**
     * Initiate an item object.
     */
    @Before
    public void before() {
        item = new Item("Weapon", "Damage Bonus", 5);
    }

    /**
     * Test function of setting type of the item.
     */
    @Test
    public void testSetType() {
        item.setType("Helmet");
        assertEquals("Helmet", item.getType());
    }

    /**
     * Test function of setting attribute of the item.
     */
    @Test
    public void testSetAttribute() {
        item.setAttribute("Attack Bonus");
        assertEquals("Attack Bonus", item.getAttribute());
    }

    /**
     * Test function of setting attribute value of the item.
     */
    @Test
    public void testSetAttributeValue() {
        item.setAttributeValue(3);
        assertEquals(3, item.getAttributeValue());
    }

    /**
     * Test function of setting is saved status of the item.
     */
    @Test
    public void testSetIsSaved() {
        item.setIsSaved(false);
        assertFalse(item.getIsSaved());
    }

    /**
     * Test function of setting id of the item.
     */
    @Test
    public void testSetSaveId() {
        item.setSaveId(6);
        assertEquals(6, item.getSaveId());
    }
}
