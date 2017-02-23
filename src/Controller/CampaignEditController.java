package Controller;

import model.CampaignIO;
import model.Campaign;
import model.MapIO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Controller class to mediate the communication between models and views.
 * @author Ruijia Yang
 */
public class CampaignEditController {
    private CampaignIO campaignio;
    private MapIO mapio;
    private Campaign campaign;

    /**
     * Initiate a MapIO and a CampaignIO
     */
    public CampaignEditController(){
        mapio =  new MapIO();
        campaignio=new CampaignIO();
    }

    /**
     * Create an instance of Campaign
     *
     * @param isSaved
     */
    public void createCampaign(boolean isSaved){
        campaign=new Campaign(isSaved);

    }

    /**
     * Create an instance of Campaign which has already existed in the file
     * @param json_campaign
     * @param isSaved
     */
    public void createExistedCampaign(JSONObject json_campaign,boolean isSaved){
        campaign=new Campaign(json_campaign,isSaved);
    }

    /**
     *
     * @return
     */
    public JSONArray getCompaignList(){return campaignio.getCampaignList();}

    /**
     *
     * @param map_id
     */
    public void addMap(String map_id){
        campaign.addMap(map_id);
    }

    /**
     *
     * @param campaign_id
     */
    public void setID(int campaign_id){campaign.setID(campaign_id);}

    /**
     *
     * @param map_id
     */
    public void removeMap(String map_id){
        campaign.removeMap(map_id);
    }

    /**
     *
     * @return
     */
    public JSONArray getMapList() {
        return mapio.getMapList();
    }
    /**
     *
     */
    public void saveCompaign(){
        campaignio.saveCompaign(campaign);
    }

    /**
     *
     * @param campaign_id
     * @return
     */
    public JSONObject readCampaign (int campaign_id){return campaignio.readCampaign(campaign_id);}

    /**
     *
     * @param selected_map_id
     * @param index
     */
    public void replaceMap( String selected_map_id,int index){
        campaign.replaceMap(selected_map_id,index);
    }
}
