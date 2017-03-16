package model;

import org.json.JSONArray;
import org.json.JSONObject;
import view.MapPanel;

import java.util.ArrayList;
import java.util.List;

public class PCampaign {

    private MapIO mapio;
    private int campaign_id;
    private List<PMap> maps;
    private int current_mapindex;

    public PCampaign(JSONObject json_campaign) {
        mapio = new MapIO();
        campaign_id = json_campaign.getInt("id");
        current_mapindex = 0;

        maps = new ArrayList<PMap>();
        JSONArray json_maps_id = json_campaign.getJSONArray("maps");
        for (int i = 0; i < json_maps_id.length(); i++) {
            int map_id = json_maps_id.getJSONObject(i).getInt("id");
            JSONObject json_map = mapio.readMap(map_id);
            PMap map = new PMap(json_map, i);
            maps.add(map);
        }
    }
    public List<PMap> getMapsList(){
        return this.maps;
    }

    public int getCurrentMapId(int current_mapindex) {
        return maps.get(current_mapindex).getId();
    }

    public JSONObject readCurrentMap() {
        int map_id = getCurrentMapId(current_mapindex);
        return mapio.readMap(map_id);
    }

    public void adaptMapToLevel(int level){
        PMap map = maps.get(current_mapindex);
        map.adaptMapToLevel(level);
    }
    public void setPlayer(PCharacter player) {
        maps.get(current_mapindex).setPlayer(player);
    }

    public void setPlayer(int previous_x, int previous_y, int current_x, int current_y, PCharacter player) {
        maps.get(current_mapindex).setPlayer(previous_x, previous_y, current_x, current_y, player);
    }

    public PItem getChestItem(int x, int y) {
        return maps.get(current_mapindex).getChestItem(x, y);
    }

}
