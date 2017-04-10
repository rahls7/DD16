package model;

/**
 * Created by mo on 2017-04-08.
 */
public class Computer implements Strategy{
    public int[] execute(int x, int y, int x_player, int y_player, int weaponBonus, PCampaign pCampaign, PCharacter pCharacter){
        int[] coordinate = new int[2];
        coordinate[0] = x;
        coordinate[1] = y;
        for (int i=0; i<3; i++) {
            if (coordinate[0]==x_player && (Math.abs(coordinate[1]-y_player) <= 1))
                break;
            if (coordinate[1]==y_player && (Math.abs(coordinate[0]-x_player) <= 1))
                break;
            coordinate = move(coordinate[0], coordinate[1], x_player, y_player, pCampaign);
            attack(coordinate[0], coordinate[1], pCampaign, pCharacter);
            loot(coordinate[0], coordinate[1], pCampaign, pCharacter);
        }
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

        pCampaign.setPlayer(previous_x, previous_y, current_x, current_y, pCampaign.getPlayer(previous_x, previous_y));

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
