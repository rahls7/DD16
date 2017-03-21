package unittest;

import model.Campaign;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the functions of adding a campaign, removing a campaign and replacing a campaign
 *
 * @author Ruijia Yang
 */
public class CampaignTest {
    Campaign campaign;

    @Before
    /**
     * Initialize a campaign model
     */
    public void before() {
        campaign = new Campaign(false);
        campaign.setID(1000);
        campaign.addMap("1");
        campaign.addMap("2");
    }

    @Test
    /**
     * Test the function of adding a campaign
     */
    public void testAddCampaign() {
        int before_add_length = campaign.getMapList().size();
        campaign.addMap("3");
        assertEquals(before_add_length + 1, campaign.getMapList().size());

    }

    /**
     * test the function of removing a campaign
     */
    @Test
    public void testRemoveCampaign() {
        int before_remove_length = campaign.getMapList().size();
        campaign.removeMap("1", 0);
        assertEquals(before_remove_length - 1, campaign.getMapList().size());
    }

    /**
     * test the function of replacing a campaign
     */
    @Test
    public void testReplaceCampaign() {
        int replaced_index = 0;
        campaign.replaceMap("666", 0);
        assertEquals(campaign.getMapList().get(0), Integer.toString(666));
    }
}
