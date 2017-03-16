package unittest;

import controller.PlayController;
import model.Campaign;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the functions of looting chests
 *
 * @author Jiayao Zhou
 */
public class LootChestTest {
    PlayController play_controller;

    @Before
    /**
     * Initialize the test.
     */
    public void before() {
        play_controller = new PlayController("123456", 5);
    }

    @Test
    /**
     * Test the function of looting a chest.
     */
    public void testLootChest() {
        int backpackSize = play_controller.getPlayer().getBackpack().size();
        play_controller.lootChest(3, 3);
        assertEquals(backpackSize + 1, play_controller.getPlayer().getBackpack().size());
    }
}
