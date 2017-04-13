package unittest;

import controller.PlayController;
import model.PCampaign;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by rahls7 on 18/03/17.
 */
public class AttackTest {
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
     * Test the function of attacking a enemy.
     */
    public void testAttack() {
        play_controller.getPlayer().setHitPoint(0);
        assertEquals(0, play_controller.getPlayer().getHitPoint());
    }
}
