package model;

/**
 * Created by mo on 2017-04-06.
 */
interface Strategy {

    int[] execute(int x, int y, int x_player, int y_player, int weaponBonus, PCampaign pCampaign, PCharacter pCharacter);

    int[] move(int x, int y, int x_player, int y_player, PCampaign pCampaign);

    void attack();

    void loot(int x, int y, PCampaign pCampaign, PCharacter pCharacter);
}
