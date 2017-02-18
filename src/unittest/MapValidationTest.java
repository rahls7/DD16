package unittest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;
import model.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test the correctness of map validation.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */
public class MapValidationTest {

    Map map;

    /**
     * Initiate the instance of Map.
     */
    @Before public void before() {
        map = new Map(10, 10, false);
    }

    /**
     * Test the correctness of the map that doesn't have an exit.
     */
    @Test public void testMapWithoutExit() {
        map.cells[2][8].setContent("ENTRY");
        assertFalse(map.validation());
    }

    /**
     * Test the correctness of the map that doesn't have an entry.
     */
    @Test public void testMapWithoutEntry() {
        map.cells[4][7].setContent("EXIT");
        assertFalse(map.validation());
    }

    /**
     * Test the correctness of the map that doesn't have a viable path from entry to exit.
     */
    @Test public void testMapWithoutViablePath() {
        map.cells[0][0].setContent("ENTRY");
        map.cells[1][0].setContent("WALL");
        map.cells[0][1].setContent("WALL");
        map.cells[5][5].setContent("EXIT");
        assertFalse(map.validation());
    }

}
