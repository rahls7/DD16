package controller;


import model.*;
import org.json.JSONObject;

public class PlayController {
    private CampaignIO campaignio;
    private PCampaign campaign;
    private PCharacter player;

    public PlayController(String character_id, int campaign_id) {
        campaignio = new CampaignIO();

        JSONObject json_campaign = readCampaign(campaign_id);
        campaign = new PCampaign(json_campaign);

        player = new PCharacter(character_id, "2");
        campaign.setPlayer(player);
    }

    private JSONObject readCampaign(int campaign_id) {
        return campaignio.readCampaign(campaign_id);
    }

    public JSONObject readCurrentMap() {
        return campaign.readCurrentMap();
    }

    public void setPlayer(int previous_x, int previous_y, int current_x, int current_y) {
        campaign.setPlayer(previous_x, previous_y, current_x, current_y, player);
    }

    public void lootChest(int x, int y) {
        if(player.getEquipment().size() < 10){
            PItem item = campaign.getChestItem(x, y);
            player.addEquipment(item);
        }
    }

}
