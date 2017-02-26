package model;

import org.json.JSONObject;

import java.util.ArrayList;
import org.json.JSONArray;

/**
 * Record information of a campaign.Change properties of a campaign.
 * @author Ruijia Yang
 */
public class Campaign {
    ArrayList<String> map_list;
    public boolean isSaved = false;
    public int save_id = 0;

    /**
     * Initialize a campaign model
     * @param is_saved
     */
    public Campaign(boolean is_saved){
        isSaved=is_saved;
        map_list=new ArrayList<String>();
    }

    /**
     * Initialize a campaign model if it is already existed
     * @param campaign
     * @param is_saved
     */
    public Campaign(JSONObject campaign, boolean is_saved){
        isSaved=is_saved;
        map_list=new ArrayList<String>();
        JSONArray maps = campaign.getJSONArray("maps");
        for(int i=0;i<maps.length();i++){
            int map_id = maps.getJSONObject(i).getInt("id");
            map_list.add(Integer.toString(map_id));
        }
    }

    /**
     * Set an ID to a campaign
     * @param id
     */
    public void setID(int id){
        this.save_id=id;
    }

    /**
     * Add a map to a campaign
     * @param map_id
     */
    public void addMap(String map_id){
        this.map_list.add(map_id);
    }

    /**
     * Get all the existing maps
     * @return map list
     */
    public ArrayList<String> getMapList(){
        return map_list;
    }

    /**
     * Remove a map from a campaign
     * @param map_id
     */
    public void removeMap(String map_id){
        for(int i=0;i<map_list.size();i++){
            if(map_list.get(i).equals(map_id)){
                map_list.remove(i);
            }
        }
    }

    /**
     * Replace the map with a selected one
     * @param selected
     * @param index
     */
    public void replaceMap(String selected,int index){
        map_list.set(index, selected);
    }
}
