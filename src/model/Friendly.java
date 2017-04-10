package model;

import java.util.Random;

/**
 * Created by mo on 2017-04-08.
 */
public class Friendly implements Strategy {
    public int[] execute(int x, int y, int x_player, int y_player, int weaponBonus, PCampaign pCampaign, PCharacter pCharacter){
        int[] coordinate = new int[2];
        coordinate[0] = x;
        coordinate[1] = y;
        for (int i=0; i<3; i++) {
            coordinate = move(coordinate[0], coordinate[1], x_player, y_player, pCampaign);
//            attack();
        }
            loot(coordinate[0], coordinate[1], pCampaign, pCharacter);
        return coordinate;
    }

    public int[] move(int previous_x, int previous_y, int x_player, int y_player, PCampaign pCampaign){
        int[] coordinate = new int[2];
        coordinate[0] = previous_x;
        coordinate[1] = previous_y;
        Random random = new Random();
        int i = random.nextInt(4);
        if (i==0){
            if (previous_x+1 < pCampaign.getMap().getWidth())
                if (pCampaign.getCell(previous_x + 1, previous_y).getType().equals("")) {
                coordinate[0] = previous_x + 1;
                coordinate[1] = previous_y;
            }
        }else if (i==1){
            if (previous_x - 1 >= 0)
                if (pCampaign.getCell(previous_x -1, previous_y).getType().equals("")){
                coordinate[0] = previous_x - 1;
                coordinate[1] = previous_y;
            }
        }else if (i==2){
            if (previous_y + 1 < pCampaign.getMap().getHeight())
                if (pCampaign.getCell(previous_x, previous_y+1).getType().equals("")){
                coordinate[0] = previous_x;
                coordinate[1] = previous_y + 1;
            }
        }else if (i==3){
            if (previous_y - 1 >=0)
                if (pCampaign.getCell(previous_x, previous_y-1).getType().equals("")){
                coordinate[0] = previous_x;
                coordinate[1] = previous_y - 1;
            }
        }

        pCampaign.setCharacter(previous_x, previous_y, coordinate[0], coordinate[1], pCampaign.getFriend(previous_x, previous_y));

        return coordinate;
    }

    public void attack(int x, int y, PCampaign pCampaign, PCharacter pCharacter){

    }

    public void loot(int x, int y, PCampaign pCampaign, PCharacter pCharacter){
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
