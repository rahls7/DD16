package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PCampaign {

    private MapIO mapio;
    private int campaign_id;
    private List<PMap> maps;
    private int current_mapindex;

    /**
     * Initialize a campaign model of play
     *
     * @param json_campaign Data of the campaign.
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
     *
     * @return the list of map models
     */
    public List<PMap> getMapsList() {
        return this.maps;
    }

    /**
     * get current map id
     *

     * @param current_mapindex current map index.
     * @return current map id
     */
    public int getCurrentMapId(int current_mapindex) {
        return maps.get(current_mapindex).getId();
    }

    /**
     * get the current map.
     *

     * @return current map.
     */
    public PMap getMap() {
        return maps.get(current_mapindex);
    }

    /**
     * read current map.
     *
     * @return JSON of the current map.
     */
    public JSONObject readCurrentMap() {
        int map_id = getCurrentMapId(current_mapindex);

        return mapio.readMap(map_id);
    }

    /**
     * Adapt the map to its level
     *
     * @param level level of the player.
     */
    public void adaptMapToLevel(int level) {
        PMap map = maps.get(current_mapindex);
        map.adaptMapToLevel(level);
    }

    /**
     * Set the current player
     *
     * @param player the player.
     */
    public void setPlayer(PCharacter player) {
        maps.get(current_mapindex).setPlayer(player);
    }

    /**
     * Set the player on a cell
     *
     * @param previous_x x coordinate of the previous cell
     * @param previous_y y coordinate of the previous cell
     * @param current_x  x coordinate of the current cell
     * @param current_y  y coordinate of the current cell
     * @param player     the player

     */
    public void setPlayer(int previous_x, int previous_y, int current_x, int current_y, PCharacter player) {
        maps.get(current_mapindex).setPlayer(previous_x, previous_y, current_x, current_y, player);
    }

    public void setCharacter(int previous_x, int previous_y, int current_x, int current_y, PCharacter pCharacter) {
        maps.get(current_mapindex).setCharacter(previous_x, previous_y, current_x, current_y, pCharacter);
    }

    public PCell getCell(int x, int y) {
        return maps.get(current_mapindex).getcell(x, y);
    }

    /**
     * Get the chest item of a specific cell
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return Item in the chest
     */
    public PItem getChestItem(int x, int y) {
        return maps.get(current_mapindex).getChestItem(x, y);
    }

    /*public ArrayList<PItem> getFriendsItem(int x , int y, Pitem item) {
        return maps.get(current_mapindex).giveFriendItem(x,y, item);
    }*/

    public PCharacter getPlayer(int x, int y){
        return maps.get(current_mapindex).getPlayer(x, y);
    }
    /**
     * Get the friend play of a specific cell
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return PCharacter
     */
    public PCharacter getFriend(int x, int y) {
        return maps.get(current_mapindex).getFriend(x, y);
    }

    /**
     * Get the enemy of a specific cell
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return PCharacter
     */
    public PCharacter getEnemy(int x, int y) {
        return maps.get(current_mapindex).getEnemy(x, y);
    }

    /**
     * Check whether the current map is full filled or not
     *
     * @return boolean
     */
    public boolean isFulfilled() {
        return maps.get(current_mapindex).isFulFilled();
    }

    /**
     * Check whether the campaign is finished or not
     *
     * @return boolean
     */
    public boolean exit() {
        current_mapindex++;
        System.out.println("index" + current_mapindex);
        if (current_mapindex < maps.size())
            return true;
        else
            return false;
    }

    /**
     * Get maps of this campaign
     *
     * @return maps
     */

    public List<PMap> getMaps() { return maps; }

}
