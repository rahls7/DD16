package unittest;

import controller.PlayController;
import model.PCampaign;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for looting enemy.
 *
 * @author Rahul
 */
public class LootEnemyTest {

    PlayController play_controller;
    PCampaign campaign;

    @Before
    /**
     * Initialize the test.
     */
    public void before() {
        play_controller = new PlayController("555", 14);
        campaign = play_controller.getCampaign();
    }

    @Test
    /**
     * Test the function of looting a enemy.
     */
    public void testLootEnemy() {
        int enemyBackpackSize = play_controller.getPlayer().getBackpack().size();
        play_controller.getPlayer().getBackpack().remove(0);
        assertEquals(enemyBackpackSize - 1, play_controller.getPlayer().getBackpack().size());
    }
}
