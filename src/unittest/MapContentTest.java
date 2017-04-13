package unittest;

import model.Map;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the correctness of changing map content.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */
public class MapContentTest {

    Map map;

    /**
     * Initiate the map object.
     */
    @Before
    public void before() {
        map = new Map(8, 8, false);
    }

    /**
     * Test the function of setting content of a cell.
     */
    @Test
    public void testSetContent() {
        map.setContent(5, 6, "WALL");
        map.setContent(2, 4, "EXIT");
        map.setContent(7, 1, "ENTRY");

        assertEquals("WALL", map.cells[5][6].content);
        assertEquals("EXIT", map.cells[2][4].content);
        assertEquals("ENTRY", map.cells[7][1].content);
    }

    /**
     * Test the function of removing content of a cell.
     */
    @Test
    public void testRemoveContent() {
        map.setContent(5, 6, "WALL");
        map.removeContent(5, 6);

        assertEquals("", map.cells[5][6].content);
    }
}
