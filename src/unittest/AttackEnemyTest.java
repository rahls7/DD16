package unittest;

import controller.PlayController;
import model.PCampaign;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by rahls7 on 18/03/17.
 */
public class AttackEnemyTest {
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
     * Test the function of attacking a enemy.
     */
    public void testAttackEnemy() {
        campaign.getEnemy(4, 4).setHitPoint(0);
        assertEquals(0, campaign.getEnemy(4, 4).getHitPoint());
    }
}
