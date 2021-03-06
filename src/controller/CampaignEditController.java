package controller;

import model.Campaign;
import model.CampaignIO;
import model.MapIO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * controller class to mediate the communication between models and views.
 *
 * @author Ruijia Yang
 */
public class CampaignEditController {
    private CampaignIO campaignio;
    private MapIO mapio;
    private Campaign campaign;

    /**
     * Initiate a MapIO and a CampaignIO
     */
    public CampaignEditController() {
        mapio = new MapIO();
        campaignio = new CampaignIO();
    }

    /**
     * Create an instance of Campaign
     *
     * @param isSaved boolean to determine saved parameter
     */
    public void createCampaign(boolean isSaved) {
        campaign = new Campaign(isSaved);

    }

    /**
     * Create an instance of Campaign which has already existed in the file
     *
     * @param json_campaign json for campaign.txt
     * @param isSaved boolean for saved state
     */
    public void createExistedCampaign(JSONObject json_campaign, boolean isSaved) {
        campaign = new Campaign(json_campaign, isSaved);
    }

    /**
     * Get the list of the existed campaigns
     *
     * @return List of campaigns
     */
    public JSONArray getCompaignList() {
        return campaignio.getCampaignList();
    }

    /**
     * Add a map to the list when creating a campaign
     *
     * @param map_id Id of the map
     */
    public void addMap(String map_id) {
        campaign.addMap(map_id);
    }

    /**
     * Set an ID to make the campaign unique
     *
     * @param campaign_id campaign id
     */
    public void setID(int campaign_id) {
        campaign.setID(campaign_id);
    }

    /**
     * Remove the map from the list when creating or editing a campaign
     *
     * @param map_id id of the map
     * @param index index on the map
     */
    public void removeMap(String map_id, int index) {
        campaign.removeMap(map_id, index);
    }

    /**
     * Get the list of all the existing maps
     *
     * @return list of maps
     */
    public JSONArray getMapList() {
        return mapio.getMapList();
    }

    /**
     * Save the campaign to the file
     */
    public void saveCompaign() {
        campaignio.saveCompaign(campaign);
    }

    /**
     * Get a campaign from the all the campaigns
     *
     * @param campaign_id id of the campaign
     * @return json object for campaign
     */
    public JSONObject readCampaign(int campaign_id) {
        return campaignio.readCampaign(campaign_id);
    }

    /**
     * Replace a map with a selected one when editing a campaign
     *
     * @param selected_map_id map id selected
     * @param index location of the cell
     */
    public void replaceMap(String selected_map_id, int index) {
        campaign.replaceMap(selected_map_id, index);
    }
}
