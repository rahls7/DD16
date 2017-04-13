package model;

/**
 * Created by mo on 2017-04-06.
 */
interface Strategy {

    /**
     * execute the strategy
     * @param x x coordinate
     * @param y y coordinate
     * @param x_player x coordinate of player
     * @param y_player y coordinate of player
     * @param weaponBonus weapon bonus
     * @param pCampaign Campaign that readed
     * @param pCharacter Character that execute the strategy
     * @return int[] coordinate
     */
    int[] execute(int x, int y, int x_player, int y_player, int weaponBonus, PCampaign pCampaign, PCharacter pCharacter);

    /**
     * move to destination
     * @param x x coordinate
     * @param y y coordinate
     * @param x_player x coordinate of player
     * @param y_player y coordinate of player
     * @param pCampaign Campain that readed
     * @return int[] coordinate
     */
    int[] move(int x, int y, int x_player, int y_player, PCampaign pCampaign);

    /**
     * attack the target
     * @param x x coordinate
     * @param y y coordinate
     * @param pCampaign Campaign that readed
     * @param pCharacter Character that executes the strategy
     */
    void attack(int x, int y, PCampaign pCampaign, PCharacter pCharacter);

    /**
     * loot the chest
     * @param x x coordinate
     * @param y y coordinate
     * @param pCampaign Cammpaign that readed
     * @param pCharacter Character that executes the strategy
     */
    void loot(int x, int y, PCampaign pCampaign, PCharacter pCharacter);
}
