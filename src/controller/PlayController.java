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

/**
 * This class is the controller class.
 *
 * @author Jiayao
 * @version 1.0.0
 */
public class PlayController {
    private CampaignIO campaignio;
    private PCampaign campaign;
    private PCharacter player;
    PCharacteristicPanel pCharacteristicPanel;
    PInventoryPanel pInventoryPanel;
    private ArrayList<PCharacter> characters;
    private PCell[][] cell;
    private Random rgen = new Random();

    /**
     * Initialize a play controller
     * @param character_id
     * @param campaign_id
     */
    public PlayController(String character_id, int campaign_id) {
        campaignio = new CampaignIO();

        JSONObject json_campaign = readCampaign(campaign_id);
        campaign = new PCampaign(json_campaign);

        player = new PCharacter(character_id, "2");
        campaign.setPlayer(player);

        characters = new ArrayList<PCharacter>();
    }

    public void setPlayer() {
        campaign.setPlayer(player);
    }

    /**
     * Get the campaign model of player
     * @return
     */
    public PCampaign getCampaign(){
        return this.campaign;
    }

    /**
     * Get the player
     * @return
     */
    public PCharacter getPlayer(){
        return this.player;
    }

    /**
     * Get the campaign json based on the campaign id
     * @param campaign_id
     * @return
     */
    private JSONObject readCampaign(int campaign_id) {
        return campaignio.readCampaign(campaign_id);
    }

    /**
     * Get the json of current map
     * @return
     */
    public JSONObject readCurrentMap(){
        campaign.adaptMapToLevel(player.getLevel());
        return campaign.readCurrentMap();
    }

    /**
     * Set observer to the character panel
     * @param pCharacteristicPanel
     */
    public void setCharacterObserver(PCharacteristicPanel pCharacteristicPanel) {
        this.pCharacteristicPanel = pCharacteristicPanel;
        readCharacter();
    }

    /**
     * Set observer to the inventory panel
     * @param pInventoryPanel
     */
    public void setInventoryObserver(PInventoryPanel pInventoryPanel) {
        this.pInventoryPanel = pInventoryPanel;
    }

    /**
     * Set observers to characters and player
     */
    public void readCharacter() {
        PMap map = campaign.getMap();
        cell = map.getCells();
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getHeight(); j++) {
            System.out.println(cell[i][j].getType());
                if (cell[i][j].getType().equals("CHARACTER") || cell[i][j].getType().equals("PLAYER")) {
                    PCharacter pCharacter = (PCharacter) cell[i][j].getContent();
                    pCharacter.addObserver(pCharacteristicPanel);
                    characters.add(pCharacter);
                }
            }

        player.addObserver(pCharacteristicPanel);
        player.addObserver(pInventoryPanel);
    }

    /**
     * Call the function character view to notify observer the observable gets changed
     * @param x x coordinate
     * @param y y coordinate
     */
    public void characterView(int x, int y) {
        PCharacter pCharacter = (PCharacter) cell[x][y].getContent();
        pCharacter.addObserver(pCharacteristicPanel);
        pCharacter.characterView();
    }

    /**
     * Notify observers.
     */
    public void characterView()
    {
        player.characterView();
    }

    /**
     * Notify observers
     * @param x
     * @param y
     */
    public void inventoryView(int x, int y) {
        PCharacter pCharacter = (PCharacter) cell[x][y].getContent();
        pCharacter.addObserver(pInventoryPanel);
        pCharacter.inventoryView();
    }

    /**
     * Notify observers.
     */
    public void inventoryView(){
        player.inventoryView();
    }

    /**
     * Set player
     * @param previous_x
     * @param previous_y
     * @param current_x
     * @param current_y
     */
    public void setPlayer(int previous_x, int previous_y, int current_x, int current_y) {
        campaign.setPlayer(previous_x, previous_y, current_x, current_y, player);
    }


    /**
     * This function loots the chest NPC when player interacts with the chest.
     * @param x : Cell index for x cordinate of player in map
     * @param y : Cell index for y cordinate of player in map
     */
    public void lootChest(int x, int y) {
        if(player.getBackpack().size() < 10){
            PItem item = campaign.getChestItem(x, y);
            if(item != null)
                player.addBackpack(item);
        }
    }

    /**
     * This function gets the array list of items in players backpack
     *
     * @return: Arraylist of items in players backpack
     */
    public ArrayList<PItem> getPlayerItem() {
        ArrayList<PItem> playerItem = player.getBackpack();
        return playerItem;
    }

    /**
     * This function exchanges item with Friendly NPC. It takes the location of the friend as its input.
     * @param x : Cell index for x cordinate of friend NPC in map
     * @param y : Cell index for y cordinate of friend NPC in map
     * @param index: The index of the item in the array list, which the player wants to take from the friendly NPC(selected from JComboBox)
     */
    public void exchangeItem(int x, int y, int index) {
        PCharacter friend = campaign.getFriend(x,y);
        if(friend.getBackpack().size()>0) {
            int size = friend.getBackpack().size();
            int itemIndex = rgen.nextInt(size);
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

    /**
     * Set the equipments of the player.
     * @param pItems Items in the equipments.
     */
    public void setEquipment(ArrayList<PItem> pItems){
        player.setEquipment(pItems);
    }

    /**
     * Set the items in backpack.
     * @param pItems Items in the backpack.
     */
    public void setBackpack(ArrayList<PItem> pItems){
        player.setBackpack(pItems);
    }

    /**
     * Recalculate stats of the player.
     */
    public void recalculateStats(){
        player.recalculateStats();
    }


    /**
     * This function kills the enemy by making his hit points 0.
     * @param x : Cell index for x cordinate of enemy in map
     * @param y : Cell index for y cordinate of enemy in map
     */

    public void attackEnemy(int x, int y) {
        PCharacter enemy = campaign.getEnemy(x,y);
        if(enemy!=null) {
            enemy.setHitPoint(0);
            characterView(x,y);
        }
    }

    /**
     * This function takes the item from enemy backpack and puts it in the player backpack
     * @param x : Cell index for x cordinate of enemy in map
     * @param y : Cell index for y cordinate of enemy in map
     * @param index: The index of the item in the array list, which the player wants to take from the enemy(selected from JComboBox)
     */

    public void lootEnemy(int x, int y, int index) {
        if(player.getBackpack().size()<10) {
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
                enemy.setHitPoint(0);

            }
            player.getBackpack().add(item);
            inventoryView(x,y);
            characterView(x,y);
        }

    }

    /**
     * This function takes in the location of the enemy and returns the hit points of the enemy.
     *
     * @param x : Cell index for x cordinate of enemy in map
     * @param y : Cell index for y cordinate of enemy in map
     * @return: Attribute Hit points of Enemy
     */

    public int getEnemyHitPoint(int x, int y) {
        PCharacter enemy = campaign.getEnemy(x,y);
        return enemy.getHitPoint();
    }

    /**
     * This function takes in the location of the enemy and returns the items in its backpack.
     * @param x : Cell index for x cordinate of enemy in map
     * @param y : Cell index for y cordinate of enemy in map
     * @return : Array List of Items in Enemy Backpack
     */
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


    /**
     * This function checks if every map requirement is fulfilled.
     * @return
     */
    public boolean isFulfilled() {
        return campaign.isFulfilled();
    }

    public boolean exit() {
        player.levelUp();
        return campaign.exit();
    }

    public String getWeaponType() {
        return player.getWeaponType();
    }

    public int getPlayerY() {
        PMap map = campaign.getMap();
        cell = map.getCells();
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getHeight(); j++) {
                if (cell[i][j].getType().equals("PLAYER")) {
                    return j;
                }
            }
        return -1;
    }

    public int getPlayerX() {
        PMap map = campaign.getMap();
        cell = map.getCells();
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getHeight(); j++) {
                if (cell[i][j].getType().equals("PLAYER")) {
                    return i;
                }
            }
        return -1;
    }

    public int getFriendHitPoint(int x, int y) {
        PCharacter friend = campaign.getFriend(x,y);
        return friend.getHitPoint();
    }

    public void attackFriend(int x, int y) {
        PCharacter friend = campaign.getFriend(x,y);
        if(friend != null) {
            friend.setHitPoint(0);
            characterView(x,y);
        }
    }
}
