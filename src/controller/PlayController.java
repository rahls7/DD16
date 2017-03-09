package controller;


import model.*;
import org.json.JSONObject;

public class PlayController {
    private CampaignIO campaignio;
    private CharacterIO characterio;
    private MapIO mapio;
    private PCampaign campaign;
    private PCharacter player;
    private int current_mapindex;

    public PlayController(String character_id, int campaign_id) {
        mapio = new MapIO();
        campaignio = new CampaignIO();
        characterio = new CharacterIO();

        JSONObject json_campaign = readCampaign(campaign_id);
        campaign = new PCampaign(json_campaign);
        current_mapindex = 0;

        JSONObject json_player = readCharacter(character_id);


    }

    private JSONObject readCharacter(String character_id) {
    }

    public JSONObject readCampaign(int campaign_id) {
        return campaignio.readCampaign(campaign_id);
    }

    public JSONObject readMap() {
        int map_id = getCurrentMapId(current_mapindex);
        return mapio.readMap(map_id);
    }

    private int getCurrentMapId(int current_mapindex) {

    }


}
