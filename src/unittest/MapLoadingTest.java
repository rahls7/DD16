package unittest;

import controller.PlayController;
import model.PCell;
import model.PCharacter;
import model.PMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the functions of map loading.
 *
 * @author Jiayao Zhou
 */
public class MapLoadingTest {
    PlayController play_controller;
    PCharacter player;
    PMap map;
    PCell[][] cells;

    @Before
    /**
     * Initialize the test.
     */
    public void before() {
        play_controller = new PlayController("999", 6);
        player = play_controller.getPlayer();
        map = play_controller.getCampaign().getMaps().get(0);
        cells = map.getCells();
    }


    /**
     * Test the function of character loading..
     */
    @Test
    public void testCharacterLoaded() {

        int x = -1;
        int y = -1;

        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getHeight(); j++)
                if (cells[i][j].getType().equals("CHARACTER")) {
                    PCharacter c = (PCharacter) cells[i][j].getContent();
                    if (c.getCategory() == 0) {
                        x = i;
                        y = j;
                        break;
                    }
                }
        assertEquals(2, x);
        assertEquals(6, y);
    }


    /**
     * Test the function of chest loading..
     */
    @Test
    public void testChestLoaded() {

        int x = -1;
        int y = -1;

        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getHeight(); j++)
                if (cells[i][j].getType().equals("CHEST")) {
                    x = i;
                    y = j;
                    break;
                }
        assertEquals(3, x);
        assertEquals(2, y);
    }
}
