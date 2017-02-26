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
 * @author mo
 * @version 1.0.0
 */

public class characterContentClass {

    Character character;
    CharacterIO characterIO = new CharacterIO();
    Item item;

    @Before public void before(){
        character = characterIO.getCharacter("3120102");
        item = new Item("Weapon", "Damage Bonus", 5);
    }

    @Test public void testSetIsSaved(){
        assertEquals("true", character.getIsSaved());
    }

    @Test public void testGetID(){
        assertEquals("3120102", character.getId());
    }

    @Test public void testName(){
        String name = new String("Rahul");
        character.setName(name);
        assertEquals(name, character.getName());
    }

//    @Test public void testEquipment(){
//        ArrayList<Item> equipment = new ArrayList<Item>();
//        equipment = character.getEquipment();
//        character.setEquipment(item);
//        character.deleteEquipment(item);
////        assertEquals(equipment, character.getEquipment());
//    }
}
