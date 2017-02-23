package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

/**
 * Created by Ruijia on 2017/2/22.
 */
public class CampaignIO {

    /**
     *
     * @param campaign
     */
    public void saveCompaign(Campaign campaign){
        String content= readCampaignFile();
        JSONObject json_content = new JSONObject(content);
        JSONArray json_campaigns = json_content.getJSONArray("campaigns");
        int id=0;
        if(!campaign.isSaved){
            for (int i = 0; i < json_campaigns.length(); i++) {
                int campaign_id = json_campaigns.getJSONObject(i).getInt("id");
                System.out.println(i + ": " + json_campaigns.getJSONObject(i).getInt("id"));
                if (id < campaign_id)
                    id = campaign_id;
            }
            id += 1;
            JSONObject json = generateJSON(id, campaign);
            json_campaigns.put(json);
        }else{
            id = campaign.save_id;
            JSONObject json = generateJSON(id, campaign);
            for (int i = 0; i < json_campaigns.length(); i++) {
                int campaign_id = json_campaigns.getJSONObject(i).getInt("id");
                if (id==campaign_id) {
                    json_campaigns.remove(i);
                    //break;
                }
            }
            json_campaigns.put(json);
        }
        System.out.println(json_content);
        writeCampaignFile(json_content);
        campaign.isSaved = true;
        campaign.save_id = id;
    }

    public void writeCampaignFile(JSONObject content){
        try {
            PrintWriter writer = new PrintWriter("src/files/campaign.txt", "UTF-8");
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject generateJSON(int id, Campaign campaign){
        JSONObject json_campaign=new JSONObject();
        json_campaign.put("id",id);

        JSONArray list=new JSONArray();
        for(int i=0;i<campaign.getMapList().size();i++){
            JSONObject map=new JSONObject();
            map.put("id",campaign.getMapList().get(i));
            list.put(map);
            //System.out.println(campaign.getMapList().get(i) + " ");
        }
        json_campaign.put("maps",list);

        return json_campaign;
    }

    public String readCampaignFile(){
        String content="";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/files/campaign.txt"));
            String line;
            while ((line = reader.readLine()) != null)
            {
                content += line;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    public JSONArray getCampaignList() {
        String content = readCampaignFile();
        JSONObject json_content = new JSONObject(content);
        JSONArray json_compaigns = json_content.getJSONArray("campaigns");

        return json_compaigns;
    }
    public JSONObject readCampaign(int campaign_id){
        String content = readCampaignFile();
        JSONObject json_content = new JSONObject(content);
        JSONArray json_campaigns = json_content.getJSONArray("campaigns");
        JSONObject json_campaign = new JSONObject();
        for (int i = 0; i < json_campaigns.length(); i++) {
            int id = json_campaigns.getJSONObject(i).getInt("id");
            if (id == campaign_id) {
                json_campaign = json_campaigns.getJSONObject(i);
                break;
            }
        }
        return json_campaign;
    }
}
