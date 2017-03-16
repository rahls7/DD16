package controller;


import com.sun.org.apache.bcel.internal.classfile.PMGClass;
import model.*;
import org.json.JSONObject;
import view.PCharacteristicPanel;
import view.PInventoryPanel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlayController {
    private CampaignIO campaignio;
    private PCampaign campaign;
    private PCharacter player;
    PCharacteristicPanel pCharacteristicPanel;
    PInventoryPanel pInventoryPanel;
    private ArrayList<PCharacter> characters;
    private PCell[][] cell;

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

    public void lootChest(int x, int y) {
        if (player.getEquipment().size() < 10) {
            PItem item = campaign.getChestItem(x, y);
            player.addEquipment(item);
        }
    }

    public PCharacter getPlayer(){
        return player;
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

}
