package model;

import org.json.JSONArray;
import org.json.JSONObject;
import view.MapPanel;

import java.util.ArrayList;
import java.util.List;

public class PCampaign {

    int campaign_id;
    List<PMap> maps;

    public PCampaign(JSONObject json_campaign) {
        campaign_id = json_campaign.getInt("id");

        maps = new ArrayList<PMap>();
        JSONArray json_maps = json_campaign.getJSONArray("maps");
        for (int i = 0; i < json_maps.length(); i++) {
            int map_id = json_maps.getJSONObject(i).getInt("id");
            PMap map = new PMap(json_maps.getJSONObject(i), i);
            maps.add(map);
        }
    }
}
