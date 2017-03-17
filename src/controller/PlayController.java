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

    public PCharacter getPlayer() {
        return player;
    }

    public PCampaign getCampaign() {
        return campaign;
    }

    public void lootChest(int x, int y) {
        if(player.getBackpack().size() < 10){
            PItem item = campaign.getChestItem(x, y);
            player.addBackpack(item);
        }
    }


    public ArrayList<PItem> getPlayerItem() {
        ArrayList<PItem> playerItem = player.getBackpack();
        return playerItem;
    }

    public void exchangeItem(int x, int y, int index) {
        PCharacter friend = campaign.getFriend(x,y);
        if(friend.getBackpack().size()>0) {
            int size = friend.getBackpack().size();
            int itemIndex = rgen.nextInt(size-1);
            PItem playerItemSel = player.getBackpack().get(index);
            PItem friendItemSel = friend.getBackpack().get(itemIndex);
            player.getBackpack().remove(index);
            friend.getBackpack().remove(itemIndex);
            friend.addToBackpack(playerItemSel);// add to backpack

            player.addToBackpack(friendItemSel);//add to backpack from friends backpack
            // remove item from friend backpack
        }
    }

    public void attackEnemy(int x, int y) {
        PCharacter enemy = campaign.getEnemy(x,y);
        if(enemy!=null) {
            enemy.setHitPoint(0);
        }
    }

    public void lootEnemy(int x, int y, int index) {
        if(player.getBackpack().size()<10) {
            System.out.println("Inside Loot Loop");
            PCharacter enemy = campaign.getEnemy(x,y);
            ArrayList<PItem> enemyBack = enemy.getBackpack();
            ArrayList<PItem> enemyEquip = enemy.getEquipment();
            int equipSize = enemyEquip.size();
            PItem item;

            if(index>=equipSize) {
                item = enemyBack.get(index-equipSize);
                boolean removeBack = enemyBack.remove(item);
            }else {
                item = enemyEquip.get(index);
                boolean removeEqip = enemyEquip.remove(item);
                enemy.recalculateStats();

            }
            player.getBackpack().add(item);
        }

    }

    public int getEnemyHitPoint(int x, int y) {
        PCharacter enemy = campaign.getEnemy(x,y);
        return enemy.getHitPoint();
    }

    public ArrayList<PItem> getEnemyItem(int x , int y) {
        PCharacter enemy = campaign.getEnemy(x,y);
        ArrayList<PItem> backEnemy = enemy.getBackpack();
        ArrayList<PItem> equipEnemy = enemy.getEquipment();
        ArrayList<PItem> backEquipEnemy = new ArrayList<PItem>();
        for(PItem item : equipEnemy) {
            backEquipEnemy.add(item);
        }
        for(PItem item : backEnemy) {
            backEquipEnemy.add(item);
        }
        return backEquipEnemy;
    }



    public boolean isFulfilled() {
        //return campaign.isFulfilled();
        return true;
    }
    public boolean exit() {
        player.levelUp();
        return campaign.exit();
    }
}
