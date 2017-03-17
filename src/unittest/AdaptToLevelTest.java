package unittest;

import controller.PlayController;
import model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Ruijia Yang
 *
 * Test the funciton of adapting to level
 */
public class AdaptToLevelTest {
    private PCampaign campaign;
    private PCharacter npc;
    private PlayController pc;
    private PCharacter player;
    private PChest chest;
    private PMap map;
    private PCell[][] cells;
    boolean findNPC=false;
    boolean findChest=false;

    @Before
    /**
     * Initialize a play controller
     */
    public void before(){
        pc=new PlayController("123456",4);
        campaign=pc.getCampaign();
        campaign.adaptMapToLevel(pc.getPlayer().getLevel());
        map=campaign.getMapsList().get(0);
        cells=map.getCells();
        player=pc.getPlayer();
    }
    @Test
    /**
     * Test the funciton of adapting to level
     */
    public void test(){
        for(int i=0;i<map.getWidth();i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (findNPC && findChest) {
                    break;
                }
                if (cells[i][j].getType().equals("CHARACTER") && !findNPC) {
                    findNPC = true;
                    npc = (PCharacter) cells[i][j].getContent();
                } else if (cells[i][j].getType().equals("CHEST") && !findChest) {
                    findChest = true;
                    chest = (PChest) cells[i][j].getContent();
                }
            }
            if (findChest && findNPC) {
                break;
            }
        }
        assertEquals(player.getLevel(),npc.getLevel());
        assertEquals(chest.getItem().getAttributeValue(),map.adaptItemAttributeLevel(player.getLevel()));
    }

}
