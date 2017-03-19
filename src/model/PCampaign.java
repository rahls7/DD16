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

    /**
     * Initialize a campaign model of play
     * @param json_campaign
     */
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

    /**
     * Get the list of map models
     * @return the list of map models
     */
    public List<PMap> getMapsList(){
        return this.maps;
    }

    public int getCurrentMapId(int current_mapindex) {
        return maps.get(current_mapindex).getId();
    }

    public PMap getMap() {
        return maps.get(current_mapindex);
    }

    public JSONObject readCurrentMap() {
        int map_id = getCurrentMapId(current_mapindex);

        return mapio.readMap(map_id);
    }

    /**
     * Adapt the map to its level
     * @param level
     */
    public void adaptMapToLevel(int level){
        PMap map = maps.get(current_mapindex);
        map.adaptMapToLevel(level);
    }

    /**
     * Set the current player
     * @param player
     */
    public void setPlayer(PCharacter player) {
        maps.get(current_mapindex).setPlayer(player);
    }

    /**
     * Set the specfic player of a cell
     *
     * @param previous_x
     * @param previous_y
     * @param current_x
     * @param current_y
     * @param player
     */
    public void setPlayer(int previous_x, int previous_y, int current_x, int current_y, PCharacter player) {
        maps.get(current_mapindex).setPlayer(previous_x, previous_y, current_x, current_y, player);
    }

    /**
     * Get the chest item of a specific cell
     * @param x
     * @param y
     * @return
     */
    public PItem getChestItem(int x, int y) {
        return maps.get(current_mapindex).getChestItem(x, y);
    }

    /*public ArrayList<PItem> getFriendsItem(int x , int y, Pitem item) {
        return maps.get(current_mapindex).giveFriendItem(x,y, item);
    }*/

    /**
     * Get the friend play of a specific cell
     * @param x
     * @param y
     * @return
     */
    public PCharacter getFriend(int x, int y) {
        return maps.get(current_mapindex).getFriend(x,y);
    }

    /**
     * Get the enemy of a specific cell
     * @param x
     * @param y
     * @return
     */
    public PCharacter getEnemy(int x, int y) {
        return maps.get(current_mapindex).getEnemy(x,y);
    }

    /**
     * Check whether the current map is full filled or not
     * @return
     */
    public boolean isFulfilled() {
        return maps.get(current_mapindex).isFulFilled();
    }

    /**
     * Check whether the campaign is finished or not
     * @return
     */
    public boolean exit() {
        current_mapindex++;
        System.out.println("index" + current_mapindex);
        if(current_mapindex < maps.size())
            return true;
        else
            return false;
    }

    public List<PMap> getMaps() { return maps; }

}
