package model;

import org.json.JSONObject;

import java.util.ArrayList;
import org.json.JSONArray;

/**
 * Created by Ruijia on 2017/2/19.
 */
public class Campaign {
    ArrayList<String> map_list;
    public boolean isSaved = false;
    public int save_id = 0;

    public Campaign(boolean is_saved){
        isSaved=is_saved;
        map_list=new ArrayList<String>();
    }
    public Campaign(JSONObject campaign, boolean is_saved){
        isSaved=is_saved;
        map_list=new ArrayList<String>();
        JSONArray maps = campaign.getJSONArray("maps");
        for(int i=0;i<maps.length();i++){
            int map_id = maps.getJSONObject(i).getInt("id");
            map_list.add(Integer.toString(map_id));
        }
    }
    public void setID(int id){
        this.save_id=id;
    }
    public void addMap(String map_id){
        this.map_list.add(map_id);
    }
    public ArrayList<String> getMapList(){
        return map_list;
    }
    public void removeMap(String map_id){
        for(int i=0;i<map_list.size();i++){
            if(map_list.get(i).equals(map_id)){
                map_list.remove(i);
            }
        }
    }
    public void replaceMap(String selected,int index){
        map_list.set(index, selected);
    }
}
