package controller;


import model.*;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.*;

public class PlayController {
    private CampaignIO campaignio;
    private PCampaign campaign;
    private PCharacter player;
    private Random rgen = new Random();

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


    public ArrayList<PItem> getPlayerItem() {
        ArrayList<PItem> playerItem = player.getBackpack();
        return playerItem;
    }

    public void exchangeItem(int x, int y, int index, PItem item) {
        PCharacter friend = campaign.getFriend(x,y);
        if(friend.getBackpack().size()<10) {
            friend.addEquipment(item);
            player.getBackpack().remove(index);
            int size = player.getBackpack().size();
            int itemIndex = rgen.nextInt(size-1);
            System.out.println(size);
            player.addEquipment(getPlayerItem().get(itemIndex));
        }
    }

    public void attackEnemy(int x, int y) {
        PCharacter enemy = campaign.getEnemy(x,y);
        if(enemy!=null) {
            enemy.setHitPoint(0);
        }
    }

    public void lootEnemy(int x, int y) {
        PCharacter enemy = campaign.getEnemy(x,y);
        ArrayList<PItem> enemyItem = enemy.getBackpack();

        if(player.getBackpack().size()<10) {
            for(PItem item : enemyItem) {
                if(player.getBackpack().size()<10) {
                    player.addEquipment(item);
                }
            }
        }
    }

    /*public ArrayList<PItem> getExchangeItem(int x, int y) {
        if(player.getEquipment().size()>0) {
            ArrayList<PItem> friendsItem = campaign.getFriendsItem(x,y);
            return friendsItem;
        }
        return null;
    }
    public String[] getExchangeString(int x, int y) {
        ArrayList<PItem> exchangeIt = campaign.getFriendsItem(x,y);
        String[] exchangeString = exchangeIt.toArray();
        return exchangeString;
    }
    */

}
