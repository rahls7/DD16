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
        play_controller = new PlayController("999", 6);
        campaign = play_controller.getCampaign();
    }

    @Test
    /**
     * Test the function of looting a enemy.
     */
    public void testLootEnemy() {
        int enemyBackpackSize = campaign.getEnemy(4, 4).getBackpack().size();
        campaign.getEnemy(4, 4).getBackpack().remove(0);
        assertEquals(enemyBackpackSize - 1, campaign.getEnemy(4, 4).getBackpack().size());
    }
}
