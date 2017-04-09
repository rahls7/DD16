package model;

/**
 * Created by mo on 2017-04-08.
 */
public class Freezing implements Strategy {
    public int[] execute(int x, int y, int x_player, int y_player, int weaponBonus, PCampaign pCampaign, PCharacter pCharacter){
        int[] coordinate = new int[2];
        coordinate[0] = x;
        coordinate[1] = y;
        move(x, y, x_player, y_player, pCampaign);
        return coordinate;
    }

    public int[] move(int x, int y, int x_player, int y_player, PCampaign pCampaign){
        int[] coordinate = new int[2];
        coordinate[0] = x;
        coordinate[1] = y;
        return coordinate;
    }

    public void attack(){

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
