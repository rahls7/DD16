package unittest;

import controller.PlayController;
import model.PCell;
import model.PCharacter;
import model.PMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the functions of character movements.
 *
 * @author Jiayao Zhou
 */
public class CharacterMovementTest {
    PlayController play_controller;

    @Before
    /**
     * Initialize the test.
     */
    public void before() {
        play_controller = new PlayController("999", 6);
    }

    /**
     * Test the function of moving character.
     */
    @Test
    public void testCharacterMovement() {

        PCharacter player = play_controller.getPlayer();
        PMap map = play_controller.getCampaign().getMaps().get(0);
        PCell[][] cells = map.getCells();
        play_controller.setPlayer(1,2,0,1);
        int x = -1;
        int y = -1;

        for(int i = 0; i < map.getWidth(); i++)
            for(int j = 0; j < map.getHeight(); j++)
                if(cells[i][j].getType().equals("PLAYER")) {
                    PCharacter c = (PCharacter) cells[i][j].getContent();
                    if(c.getCategory() == 2) {
                        x = i;
                        y = j;
                        break;
                    }
                }
        assertEquals(0, x);
        assertEquals(1, y);
    }
}
