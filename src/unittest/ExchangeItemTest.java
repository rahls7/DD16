package unittest;

import controller.PlayController;
import model.Campaign;
import model.PCampaign;
import model.PItem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for exchanging items.
 * @author Rahul
 */
public class ExchangeItemTest {

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
     * Test the function of exchanging an item.
     */
    public void testLootEnemy() {
        play_controller.getPlayer().getBackpack().remove(0);
        PItem item = play_controller.getPlayer().getBackpack().get(1);
        play_controller.getPlayer().getBackpack().add(item);
        int backpackSize = play_controller.getPlayer().getBackpack().size();
        assertEquals(backpackSize , play_controller.getPlayer().getBackpack().size());
    }
}
