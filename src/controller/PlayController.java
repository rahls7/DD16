package controller;


import model.*;
import org.json.JSONObject;
import view.PCellPanel;
import view.PCharacteristicPanel;
import view.PInventoryPanel;
import view.Play;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

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
    private PMap pMap;
    private int campaign_id, current_mapindex;
    PCharacteristicPanel pCharacteristicPanel;
    PInventoryPanel pInventoryPanel;

    private ArrayList<PCharacter> characters;
    private PCell[][] cell;
    private PCellPanel[][] cellPanels;
    private Random rgen = new Random();
    private PlayIO playIO;

    private ArrayList<PCharacter> enemys;
    private ArrayList<PCharacter> friends;

    private int attackRoll;
    private int attackBon;
    private int finalAttack;
    private int damageRoll;
    private int damagePen;

    private List<PCharacter> order;
    private int player_index;

    /**
     * Initialize a play controller
     *
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


        playIO = new PlayIO();
        enemys = new ArrayList<PCharacter>();
        friends = new ArrayList<PCharacter>();
    }


    public void setCellPanel(PCellPanel[][] pCellPanel) {
        cellPanels = pCellPanel;
    }

    /**
     * Get the campaign model of player
     * @return PCampaign

     */
    public PCampaign getCampaign() {
        return this.campaign;
    }

    /**
     * Get the player
     * @return PCharacter
     */
    public PCharacter getPlayer() {
        return this.player;
    }

    /**
     * Get the campaign json based on the campaign id
     *
     * @param campaign_id
     * @return JSON Object of a campaign
     */
    private JSONObject readCampaign(int campaign_id) {
        return campaignio.readCampaign(campaign_id);
    }

    /**
     * Get the json of current map
     * @return JSON Object of a Map
     */
    public JSONObject readCurrentMap() {
        campaign.adaptMapToLevel(player.getLevel());
        return campaign.readCurrentMap();
    }

    /**
     * Set observer to the character panel
     *
     * @param pCharacteristicPanel
     */
    public void setCharacterObserver(PCharacteristicPanel pCharacteristicPanel) {
        this.pCharacteristicPanel = pCharacteristicPanel;
        readCharacter();
    }

    /**
     * Set observer to the inventory panel
     *
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
        characters = new ArrayList<PCharacter>();
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getHeight(); j++) {
                System.out.println(cell[i][j].getType());
                if (cell[i][j].getType().equals("CHARACTER") || cell[i][j].getType().equals("PLAYER")) {
                    PCharacter pCharacter = (PCharacter) cell[i][j].getContent();
                    pCharacter.addObserver(pCharacteristicPanel);
                    characters.add(pCharacter);
                }
            }
        String displayDice = generateOrder();
        Play.displayInfo(displayDice);
        player.addObserver(pCharacteristicPanel);
        player.addObserver(pInventoryPanel);

    }


    public void beforePlayer() {
        if (player_index == 0) {
//            System.out.println("Play is the first");
        } else {
            for (int i = 0; i < player_index; i++) {
                if (order.get(i).getHitPoint()> 0)
                    order.get(i).burningDamage();
                if (order.get(i).getHitPoint() > 0)
                    execute(order, i);
            }
        }
    }

    /**
     * Generate the action order of the player and all the NPCs
     */


    private String generateOrder() {
        order = new ArrayList<PCharacter>();
        player_index = -1;
        int[] index = new int[characters.size()];
        int[] random = new int[characters.size()];
        int temp1;
        int temp2;
        for (int i = 0; i < characters.size(); i++) {
            index[i] = i;
            int num = 1 + (int) (Math.random() * 20);
            random[i] = num;
        }
        for (int i = 0; i < characters.size() - 1; i++) {
            for (int j = 0; j < characters.size() - 1 - i; j++) {
                if (random[j + 1] > random[j]) {
                    temp1 = random[j];
                    random[j] = random[j + 1];
                    random[j + 1] = temp1;
                    temp2 = index[j];
                    index[j] = index[j + 1];
                    index[j + 1] = temp2;
                }
            }
        }
        for (int i = 0; i < characters.size(); i++) {
            order.add(characters.get(index[i]));
        }
        for (int i = 0; i < order.size(); i++) {
            if (order.get(i).getCategory() == 2) {
                player_index = i;
            }
        }
        String display="The order is: \n";
        for(int i=0;i<characters.size();i++){
            if(order.get(i).getCategory()==0){
                display=display+"Friend, "+random[i]+"\n";
            }else if(order.get(i).getCategory()==1){
                display=display+"Enemy, "+random[i]+"\n";
            }else if(order.get(i).getCategory()==2){
                display=display+"Player, "+random[i]+"\n";
            }
        }
        return display;

    }

    public ArrayList<PCharacter> getCharacters(){
        return this.characters;
    }
    public List<PCharacter> getOrder(){
        return this.order;
    }

    public void setPlayer() {
        campaign.setPlayer(player);
    }


    public void backToPlayer() {


        if (player_index == 0) {
            for (int i = 1; i < order.size(); i++) {
                if (order.get(i).getHitPoint()> 0)
                    order.get(i).burningDamage();
                if (order.get(i).getHitPoint()> 0)
                    execute(order, i);
            }
        } else if (player_index == order.size() - 1) {
            for (int i = 0; i < player_index; i++) {
                if (order.get(i).getHitPoint()> 0)
                    order.get(i).burningDamage();
                if (order.get(i).getHitPoint() > 0)
                    execute(order, i);
            }
        } else {
            for (int i = player_index + 1; i < order.size(); i++) {
                if (order.get(i).getHitPoint()> 0)
                    order.get(i).burningDamage();
                if (order.get(i).getHitPoint() > 0)
                    execute(order, i);
            }
            for (int i = 0; i < player_index; i++) {
                if (order.get(i).getHitPoint()> 0)
                    order.get(i).burningDamage();
                if (order.get(i).getHitPoint() > 0)
                  execute(order, i);
            }
        }
    }


    /**
     * Call the function character view to notify observer the observable gets changed
     *
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

    public void characterView() {
        player.characterView();
    }

    /**
     * Notify observers
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public void inventoryView(int x, int y) {
        PCharacter pCharacter = (PCharacter) cell[x][y].getContent();
        pCharacter.addObserver(pInventoryPanel);
        pCharacter.inventoryView();
    }

    /**
     * Notify observers.
     */

    public void inventoryView() {
        player.inventoryView();
    }

    /**
     * Set player
     *
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
     *
     * @param x : Cell index for x cordinate of player in map
     * @param y : Cell index for y cordinate of player in map
     */
    public void lootChest(int x, int y) {
        if (player.getBackpack().size() < 10) {
            PItem item = campaign.getChestItem(x, y);
            if (item != null)
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
     *
     * @param x      : Cell index for x cordinate of friend NPC in map
     * @param y      : Cell index for y cordinate of friend NPC in map
     * @param index: The index of the item in the array list, which the player wants to take from the friendly NPC(selected from JComboBox)
     */
    public void exchangeItem(int x, int y, int index) {
        PCharacter friend = campaign.getFriend(x, y);
        if (friend.getBackpack().size() > 0) {
            int size = friend.getBackpack().size();
            int itemIndex = rgen.nextInt(size);
            PItem playerItemSel = player.getBackpack().get(index);
            PItem friendItemSel = friend.getBackpack().get(itemIndex);
            player.getBackpack().remove(index);
            friend.getBackpack().remove(itemIndex);
            friend.addToBackpack(playerItemSel);// add to backpack

            player.addToBackpack(friendItemSel);//add to backpack from friends backpack
            inventoryView(x, y);
            characterView(x, y);
            // remove item from friend backpack
        }
    }

    /**
     * Set the equipments of the player.
     *
     * @param pItems Items in the equipments.
     */

    public void setEquipment(ArrayList<PItem> pItems) {
        player.setEquipment(pItems);
    }

    /**
     * Set the items in backpack.
     *
     * @param pItems Items in the backpack.
     */

    public void setBackpack(ArrayList<PItem> pItems) {
        player.setBackpack(pItems);
    }

    /**
     * Recalculate stats of the player.
     */

    public void recalculateStats() {
        player.recalculateStats();
    }


    /**
     * This function kills the enemy by making his hit points 0.
     *
     * @param x : Cell index for x cordinate of enemy in map
     * @param y : Cell index for y cordinate of enemy in map
     */

    public void attackEnemy(int x, int y) {
        PCharacter enemy = campaign.getEnemy(x, y);
        if (enemy != null) {
            if (enemy.getHitPoint() > 0) {
                attackRoll = rgen.nextInt(20) + 1;
                attackBon = player.getAttackBonus();
                finalAttack = attackRoll + attackBon;
                Play.displayInfo("Your attack roll is " + attackRoll + " Your final Attack points including attackBonus " + attackBon + " is " + finalAttack);
                Play.displayInfo("Enemy armour class is " + enemy.getArmorClass());
                if (finalAttack >= enemy.getArmorClass()) {
                    damageRoll = rgen.nextInt(8) + 1;
                    damagePen = damageRoll + player.getDamageBonus();
                    Play.displayInfo("Your damage roll is " + damageRoll + " Your final damage points including damageBonus " + player.getDamageBonus() + " is " + damagePen);
                    int j = enemy.getHitPoint() - damagePen; // damage bonus
                    Play.displayInfo("Hit points after deduction are " + j);
                    enemy.setHitPoint(j);
                    if (enemy.getHitPoint() > 0) {
                        for (String i : player.getWeapon().getEnchantments()) {
                            if (i.equals("Slaying")) {
                                enemy.setHitPoint(0);
                                Play.displayInfo("Enemy Slayed");
                            } else if (i.equals("Freezing")) {
                                int enchBonus = player.getWeapon().getAttributeValue();
                                enemy.setFreezeTurns(enchBonus);
                                Play.displayInfo("Enemy frozen");
                            } else if (i.equals("Pacifying")) {
                                //Do something
                                enemy.setCategory(0);
                                enemy.setStrategy(new Friendly());
                                Play.displayInfo("Enemy Pacified");
                            } else if (i.equals("Frightening")) {
                                int enchBonus = player.getWeapon().getAttributeValue();
                                enemy.setFrighteningTurns(enchBonus);
                                Play.displayInfo("Enemy frightened");
                            } else if (i.equals("Burning")) {
                                int enchBonus = player.getWeapon().getAttributeValue() * 5;
                                enemy.setBurnTurns(3);
                                enemy.setBurnDamage(enchBonus);
                                Play.displayInfo("Enemy Burning");
                            }
                        }
                    }
                }
            }
            characterView(x, y);
        }
    }

    /**
     * This function takes the item from enemy backpack and puts it in the player backpack
     *
     * @param x      : Cell index for x cordinate of enemy in map
     * @param y      : Cell index for y cordinate of enemy in map
     * @param index: The index of the item in the array list, which the player wants to take from the enemy(selected from JComboBox)
     */

    public void lootEnemy(int x, int y, int index) {
        if (player.getBackpack().size() < 10) {
            PCharacter enemy = campaign.getEnemy(x, y);
            ArrayList<PItem> enemyBack = enemy.getBackpack();
            ArrayList<PItem> enemyEquip = enemy.getEquipment();
            int equipSize = enemyEquip.size();
            PItem item;

            if (index >= equipSize) {
                item = enemyBack.get(index - equipSize);
                boolean removeBack = enemyBack.remove(item);
            } else {
                item = enemyEquip.get(index);
                boolean removeEqip = enemyEquip.remove(item);
                enemy.recalculateStats();
                enemy.setHitPoint(0);

            }
            player.getBackpack().add(item);
            inventoryView(x, y);
            characterView(x, y);
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
        PCharacter enemy = campaign.getEnemy(x, y);
        return enemy.getHitPoint();
    }

    /**
     * This function takes in the location of the enemy and returns the items in its backpack.
     *
     * @param x : Cell index for x cordinate of enemy in map
     * @param y : Cell index for y cordinate of enemy in map
     * @return : Array List of Items in Enemy Backpack
     */

    public ArrayList<PItem> getEnemyItem(int x, int y) {
        PCharacter enemy = campaign.getEnemy(x, y);
        ArrayList<PItem> backEnemy = enemy.getBackpack();
        ArrayList<PItem> equipEnemy = enemy.getEquipment();
        ArrayList<PItem> backEquipEnemy = new ArrayList<PItem>();
        for (PItem item : equipEnemy) {
            backEquipEnemy.add(item);
        }
        for (PItem item : backEnemy) {
            backEquipEnemy.add(item);
        }
        return backEquipEnemy;
    }


    /**
     * This function checks if every map requirement is fulfilled.
     * @return boolean

     */
    public boolean isFulfilled() {
        return campaign.isFulfilled();
    }

    public boolean exit() {
        player.levelUp();
        return campaign.exit();
    }

    public void savePlay(){
        pMap = campaign.getMap();
        campaign_id = campaign.getCampaign_id();
        current_mapindex = campaign.getCurrent_mapindex();

        playIO.savePlay(pMap, campaign_id, current_mapindex);
    }


    public void execute_player(){
        int x_player = -1;
        int y_player = -1;
        player.setStrategy(new Computer());
        PMap map = campaign.getMap();
        for (int k = 0; k < map.getWidth(); k++)
            for (int j = 0; j < map.getHeight(); j++) {
                if (cell[k][j].getType().equals("PLAYER")) {
                    PCharacter character = (PCharacter) cell[k][j].getContent();
                    x_player = k;
                    y_player = j;
                }
            }

        int enemy_exist = 0;
        int x = -1;
        int y = -1;
        for (PCharacter pCharacter : enemys) {
            if (pCharacter.getHitPoint() > 0) {
                cell = map.getCells();
                for (int i = 0; i < map.getWidth(); i++)
                    for (int j = 0; j < map.getHeight(); j++) {
                        if (cell[i][j].getType().equals("CHARACTER")) {
                            PCharacter character = (PCharacter) cell[i][j].getContent();
                            if (character.equals(pCharacter)) {
                                x = i;
                                y = j;
                                enemy_exist = 1;
                                break;
                            }
                        }
                    }
            }
        }

        cellPanels[x_player][y_player].removeContent();
        int[] coordinate = new int[2];
        coordinate = player.executeStrategy(x_player, y_player, x, y, enemy_exist, campaign);
        cellPanels[coordinate[0]][coordinate[1]].setContent("PLAYER");
    }


    /**
     * execute PCharacter
     * @param order order of character
     * @param i index of that character
     */
    public void execute(List<PCharacter> order, int i){
        int x_player = -1;
        int y_player = -1;
        PMap map = campaign.getMap();
        for (int k = 0; k < map.getWidth(); k++)
            for (int j = 0; j < map.getHeight(); j++) {
                if (cell[k][j].getType().equals("PLAYER")) {
                    PCharacter character = (PCharacter) cell[k][j].getContent();
                    x_player = k;
                    y_player = j;
                }
            }

        PCharacter pCharacter = order.get(i);
        if (pCharacter.getCategory() == 0)
            pCharacter.setStrategy(new Friendly());
        else if (pCharacter.getCategory() == 1)
            pCharacter.setStrategy(new Aggressive());
        map = campaign.getMap();
        cell = map.getCells();
        int x = -1;
        int y = -1;
        for (int k = 0; k < map.getWidth(); k++)
            for (int j = 0; j < map.getHeight(); j++) {
                if (cell[k][j].getType().equals("CHARACTER")) {
                    PCharacter character = (PCharacter) cell[k][j].getContent();
                    if (character == pCharacter) {
                        x = k;
                        y = j;
                    }
                }
            }

        cellPanels[x][y].removeContent();
        int[] coordinate = new int[2];
        coordinate = pCharacter.executeStrategy(x, y, x_player, y_player, 1, campaign);
        cellPanels[coordinate[0]][coordinate[1]].setContent("CHARACTER " + pCharacter.getId() + " " + pCharacter.getCategory());
        characterView(x_player, y_player);
        inventoryView(x_player, y_player);
    }

    /**
     *
     */
    public void turn() {
        // get the coordinate of player
        int x_player = -1;
        int y_player = -1;
        PMap map = campaign.getMap();
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getHeight(); j++) {
                if (cell[i][j].getType().equals("PLAYER")) {
                    PCharacter character = (PCharacter) cell[i][j].getContent();
                    x_player = i;
                    y_player = j;
                }
            }


        for (PCharacter pCharacter : friends) {
            Friendly friendly = new Friendly();
            pCharacter.setStrategy(friendly);
            map = campaign.getMap();
            cell = map.getCells();
            int x = -1;
            int y = -1;
            for (int i = 0; i < map.getWidth(); i++)
                for (int j = 0; j < map.getHeight(); j++) {
                    if (cell[i][j].getType().equals("CHARACTER")) {
                        PCharacter character = (PCharacter) cell[i][j].getContent();
                        if (character == pCharacter) {
                            x = i;
                            y = j;
                        }
                    }
                }

            cellPanels[x][y].removeContent();
            int[] coordinate = new int[2];
            coordinate = pCharacter.executeStrategy(x, y, x_player, y_player, 1, campaign);
            cellPanels[coordinate[0]][coordinate[1]].setContent("CHARACTER " + pCharacter.getId() + " " + pCharacter.getCategory());
        }

        for (PCharacter pCharacter : enemys) {
            Aggressive aggressive = new Aggressive();
            pCharacter.setStrategy(aggressive);
            map = campaign.getMap();
            cell = map.getCells();
            int x = -1;
            int y = -1;
            for (int i = 0; i < map.getWidth(); i++)
                for (int j = 0; j < map.getHeight(); j++) {
                    if (cell[i][j].getType().equals("CHARACTER")) {
                        PCharacter character = (PCharacter) cell[i][j].getContent();
                        if (character.equals(pCharacter)) {
                            x = i;
                            y = j;
                        }
                    }
                }

            cellPanels[x][y].removeContent();
            int[] coordinate = new int[2];
            coordinate = pCharacter.executeStrategy(x, y, x_player, y_player, 1, campaign);
            cellPanels[coordinate[0]][coordinate[1]].setContent("CHARACTER " + pCharacter.getId() + " " + pCharacter.getCategory());

        }
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
        PCharacter friend = campaign.getFriend(x, y);
        return friend.getHitPoint();
    }

    public void attackFriend(int x, int y) {
        PCharacter friend = campaign.getFriend(x, y);
        if (friend != null) {
            if (friend.getHitPoint() > 0) {
                attackRoll = rgen.nextInt(20) + 1;
                attackBon = player.getAttackBonus();
                finalAttack = attackRoll + attackBon;
                Play.displayInfo("Your attack roll is " + attackRoll + " Your final Attack points including attackBonus " + attackBon + " is " + finalAttack);
                Play.displayInfo("Enemy armour class is " + friend.getArmorClass());
                if (finalAttack >= friend.getArmorClass()) {
                    damageRoll = rgen.nextInt(8) + 1;
                    damagePen = damageRoll + player.getDamageBonus();
                    Play.displayInfo("Your damage roll is " + damageRoll + " Your final damage points including damageBonus " + player.getDamageBonus() + " is " + damagePen);
                    int j = friend.getHitPoint() - damagePen; // damage bonus
                    Play.displayInfo("Hit points after deduction are " + j);
                    friend.setHitPoint(j);
                    friend.setStrategy(new Aggressive());
                    friend.setCategory(1);
                }
                if (friend.getHitPoint() > 0) {
                    for (String i : player.getWeapon().getEnchantments()) {
                        if (i.equals("Slaying")) {
                            friend.setHitPoint(0);
                            Play.displayInfo("Friend Slayed");
                        } else if (i.equals("Freezing")) {
                            int enchBonus = player.getWeapon().getAttributeValue();
                            friend.setFreezeTurns(enchBonus);
                            Play.displayInfo("Friend Frozen");
                        } else if (i.equals("Pacifying")) {
                            //Do something
                            friend.setCategory(0);
                            friend.setStrategy(new Friendly());

                        } else if (i.equals("Frightening")) {
                            int enchBonus = player.getWeapon().getAttributeValue();
                            friend.setFrighteningTurns(enchBonus);
                            Play.displayInfo("Friend Frightened");
                        } else if (i.equals("Burning")) {
                            int enchBonus = player.getWeapon().getAttributeValue() * 5;
                            friend.setBurnTurns(3);
                            friend.setBurnDamage(enchBonus);
                            Play.displayInfo("Friend Burning");
                        }
                    }
                }

                characterView(x, y);
            }
        }
    }
}
