package model;

import view.Play;

import java.util.Random;

/**
 * Created by mo on 2017-04-06.
 */
public class Aggressive implements Strategy {
    public int[] execute(int x, int y, int x_player, int y_player, int weaponBonus, PCampaign pCampaign, PCharacter pCharacter){
        int[] coordinate = new int[2];
        coordinate[0] = x;
        coordinate[1] = y;
        for (int i=0; i<3; i++) {
            if (coordinate[0] == x_player && (Math.abs(coordinate[1] - y_player) <= 1))
                break;
            if (coordinate[1] == y_player && (Math.abs(coordinate[0] - x_player) <= 1))
                break;
            coordinate = move(coordinate[0], coordinate[1], x_player, y_player, pCampaign);
        }
            attack(coordinate[0], coordinate[1], pCampaign, pCharacter);
            loot(coordinate[0], coordinate[1], pCampaign, pCharacter);

        return coordinate;
    }

    public int[] move(int previous_x, int previous_y, int x_player, int y_player, PCampaign pCampaign) {

        int current_x = -1;
        int current_y = -1;
        int add_x = 0;
        int add_y = 0;
        if (previous_x < x_player)
            add_x = 1;
        else if (previous_x > x_player)
            add_x = -1;
        else
            add_x = 0;

        if (previous_y < y_player)
            add_y = 1;
        else if (previous_y > y_player)
            add_y = -1;
        else
            add_y = 0;

        if (add_x != 0) {
            if (pCampaign.getCell(previous_x + add_x, previous_y).getType().equals("")) {
                current_x = previous_x + add_x;
                current_y = previous_y;
            } else if (pCampaign.getCell(previous_x, previous_y + add_y).getType().equals("")) {
                current_x = previous_x;
                current_y = previous_y + add_y;
            } else  if (pCampaign.getCell(previous_x, previous_y - add_y).getType().equals("")){
                current_x = previous_x;
                current_y = previous_y - add_y;
            } else{
                current_x = previous_x;
                current_y = previous_y;
            }
        }
        else if (add_y!=0){
            if (pCampaign.getCell(previous_x, previous_y+add_y).getType().equals("")){
                current_x = previous_x;
                current_y = previous_y + add_y;
            } else if (pCampaign.getCell(previous_x + add_x, previous_y).getType().equals("")) {
                current_x = previous_x + add_x;
                current_y = previous_y;
            } else if (pCampaign.getCell(previous_x, previous_y - add_y).getType().equals("")){
                    current_x = previous_x;
                    current_y = previous_y - add_y;
            } else{
                    current_x = previous_x;
                    current_y = previous_y;
            }
        }

        pCampaign.setCharacter(previous_x, previous_y, current_x, current_y, pCampaign.getEnemy(previous_x, previous_y));

//         *       2. compare the x coordinate and move. if x equals. compare the y coordinate and move on.
//         *       3. if a character is in attack range, attack the character
//         *       4. if he's near a chest, loot the chest
//         *    }
        int[] coordinate = new int[2];
        coordinate[0] = current_x;
        coordinate[1] = current_y;
        return coordinate;
    }

    public void attack(int x, int y, PCampaign pCampaign, PCharacter pCharacter) {
        int[] ranged_x = {x-2, x, x, x+2, x-1, x, x, x+1, x-1, x+1, x-1, x+1};
        int[] ranged_y = {y, y-2, y+2, y, y, y-1, y+1, y, y-1, y+1, y+1, y-1};

        int[] melee_x = {x-1, x, x, x+1};
        int[] melee_y = {y, y-1, y+1, y};

        int flag = 0; // no attack
        String weapon_type = pCharacter.getWeaponType();
        PMap map = pCampaign.getMap();
        PCell[][] cells = map.getCells();
        for (int i=0; i<map.getWidth(); i++)
            for (int j=0; j<map.getHeight(); j++)
                if ((cells[i][j].getType().equals("CHARACTER") || cells[i][j].getType().equals("PLAYER")) && (!(i==x && j==y))){
                    PCharacter pCharacters = (PCharacter)cells[i][j].getContent();
                    if(weapon_type != null && weapon_type.equals("Ranged Weapon")) {
                        for(int k = 0; k < ranged_x.length; k++) {
                            if(ranged_x[k] == i && ranged_y[k] == j)
                               attackEnemy(pCharacter, pCharacters, pCampaign);
                        }
                    }
                    else {
                        for(int k = 0; k < melee_x.length; k++) {
                            if(melee_x[k] == i && melee_y[k] == j)
                                attackEnemy(pCharacter, pCharacters, pCampaign);
                        }
                    }

                }

    }

    private void attackEnemy(PCharacter attacker, PCharacter attacked, PCampaign campaign){
        Random rgen = new Random();
        if(attacked.getCategory()==0){
            attacked.setCategory(1);
            attacked.setStrategy(new Aggressive());
        }
        if(attacked != null) {
            if(attacked.getHitPoint()>0) {
                int attackRoll = rgen.nextInt(20) + 1;
                int attackBon = attacker.getAttackBonus();
                int finalAttack = attackRoll + attackBon;
                Play.displayInfo("Attack roll is " + attackRoll + " Final Attack points including attackBonus " + attackBon + " is " + finalAttack);
                Play.displayInfo("Armor class is " + attacked.getArmorClass());
                if(finalAttack >= attacked.getArmorClass()) {
                    int damageRoll = rgen.nextInt(8) + 1;
                    int damagePen = damageRoll + attacker.getDamageBonus();
                    Play.displayInfo("Damage roll is " + damageRoll + " Final damage points including damageBonus " + attacker.getDamageBonus() + " is " + damagePen);
                    int j = attacked.getHitPoint() - damagePen; // damage bonus
                    Play.displayInfo("Hit points after deduction are " + j);
                    attacked.setHitPoint(j);
                }
            }
        }
    }

    public void loot(int x, int y, PCampaign pCampaign, PCharacter pCharacter) {
        if (x+1 < pCampaign.getMap().getWidth())
            if (pCampaign.getCell(x+1, y).getType().equals("CHEST")){
                if (pCharacter.getBackpack().size() < 10) {
                    PItem item = pCampaign.getChestItem(x+1, y);
                    if (item != null)
                        pCharacter.addBackpack(item);
                }
            }

        if (x-1 >= 0)
            if (pCampaign.getCell(x-1, y).getType().equals("CHEST")){
                if (pCharacter.getBackpack().size() < 10) {
                    PItem item = pCampaign.getChestItem(x-1, y);
                    if (item != null)
                        pCharacter.addBackpack(item);
                }
            }

        if (y+1 < pCampaign.getMap().getHeight())
            if (pCampaign.getCell(x, y+1).getType().equals("CHEST")){
                if (pCharacter.getBackpack().size() < 10) {
                    PItem item = pCampaign.getChestItem(x, y+1);
                    if (item != null)
                        pCharacter.addBackpack(item);
                }
            }

        if (y-1 >= 0 )
            if (pCampaign.getCell(x, y-1).getType().equals("CHEST")){
                if (pCharacter.getBackpack().size() < 10) {
                    PItem item = pCampaign.getChestItem(x, y-1);
                    if (item != null)
                        pCharacter.addBackpack(item);
                }
            }
    }
}
