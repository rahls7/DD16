package controller;


import com.sun.org.apache.bcel.internal.classfile.PMGClass;
import model.*;
import org.json.JSONObject;
import view.PCharacteristicPanel;
import view.PInventoryPanel;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.*;
import java.util.ArrayList;
import java.util.*;

public class PlayController {
    private CampaignIO campaignio;
    private PCampaign campaign;
    private PCharacter player;
    PCharacteristicPanel pCharacteristicPanel;
    PInventoryPanel pInventoryPanel;
    private ArrayList<PCharacter> characters;
    private PCell[][] cell;
    private Random rgen = new Random();

    public PlayController(String character_id, int campaign_id) {
        campaignio = new CampaignIO();

        JSONObject json_campaign = readCampaign(campaign_id);
        campaign = new PCampaign(json_campaign);

        player = new PCharacter(character_id, "2");
        campaign.setPlayer(player);

        characters = new ArrayList<PCharacter>();
    }

    private JSONObject readCampaign(int campaign_id) {
        return campaignio.readCampaign(campaign_id);
    }

    public JSONObject readCurrentMap() {
        return campaign.readCurrentMap();
    }

    public void setCharacterObserver(PCharacteristicPanel pCharacteristicPanel) {
        this.pCharacteristicPanel = pCharacteristicPanel;
        readCharacter();
    }

    public void setInventoryObserver(PInventoryPanel pInventoryPanel) {
        this.pInventoryPanel = pInventoryPanel;
    }

    public void readCharacter() {
        PMap map = campaign.getMap();
        cell = map.getCells();
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getHeight(); j++) {
                if (cell[i][j].getType().equals("CHARACTER") || cell[i][j].getType().equals("PLAYER")) {
                    PCharacter pCharacter = (PCharacter) cell[i][j].getContent();
                    pCharacter.addObserver(pCharacteristicPanel);
                    characters.add(pCharacter);
                }
            }

        player.addObserver(pCharacteristicPanel);
        player.addObserver(pInventoryPanel);
    }

    public void characterView(int x, int y) {
        PCharacter pCharacter = (PCharacter) cell[x][y].getContent();
        pCharacter.addObserver(pCharacteristicPanel);
        pCharacter.characterView();
    }

    public void characterView()
    {
        player.characterView();
    }

    public void inventoryView(int x, int y) {
        PCharacter pCharacter = (PCharacter) cell[x][y].getContent();
        pCharacter.addObserver(pInventoryPanel);
        pCharacter.inventoryView();
    }

    public void inventoryView(){
        player.inventoryView();
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
            inventoryView(x,y);
            characterView(x,y);
            // remove item from friend backpack
        }
    }



    public void setEquipment(ArrayList<PItem> pItems){
        player.setEquipment(pItems);
    }

    public void setBackpack(ArrayList<PItem> pItems){
        player.setBackpack(pItems);
    }

    public void recalculateStats(){
        player.recalculateStats();
    }

    public void attackEnemy(int x, int y) {
        PCharacter enemy = campaign.getEnemy(x,y);
        if(enemy!=null) {
            enemy.setHitPoint(0);
            characterView(x,y);
        }
    }

    public void lootEnemy(int x, int y, int index) {
        if(player.getBackpack().size()<10) {
            System.out.println("Inside Loot Loop");
            PCharacter enemy = campaign.getEnemy(x,y);
            ArrayList<PItem> enemyBack = enemy.getBackpack();
            ArrayList<PItem> enemyEquip = enemy.getEquipment();
            int equipSize = enemyEquip.size();
            int backSize = enemyBack.size();
            PItem item;

            if(index>=equipSize) {
                item = enemyBack.get(index-equipSize);
                boolean removeBack = enemyBack.remove(item);
                System.out.println("");
            }else {
                item = enemyEquip.get(index);
                boolean removeEqip = enemyEquip.remove(item);
                System.out.println("Removing equipment");
                System.out.println(removeEqip);
                System.out.println(enemy.getEquipment().size());
            }
            player.getBackpack().add(item);
            inventoryView(x,y);
            characterView(x,y);
        }




        /*if(player.getBackpack().size()<10) {
            for(PItem item : enemyItem) {
                if(player.getBackpack().size()<10) {
                    player.addToBackpack(item); // add to backpack
                }
            }
        }*/
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

    public boolean isFulfilled() {
        return campaign.isFulfilled();
    }
    public boolean exit() {
        player.levelUp();
        return campaign.exit();
    }
}
