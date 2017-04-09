package unittest;

import model.*;
import model.Character;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests for builder pattern.
 *
 * @author Mo Chen
 */
public class CharacterBuilderTest {

    Character character;
    Player player;

    @Before
    public void before() {
        player = new Player();
    }

    /**
     * test the bully character builder
     */
    @Test
    public void testBully() {
        BullyCharacterBuilder bullyCharacterBuilder = new BullyCharacterBuilder();
        player.setCharacterBuilder(bullyCharacterBuilder);
        player.constructCharacter("123");
        character = player.getCharacter();
        int[][] stats = character.getStats();
        assertTrue(stats[0][0] >= stats[2][0] && stats[2][0] >= stats[1][0]);
    }

    /**
     * test nimble character builder
     */
    @Test
    public void testNimble() {
        NimbleCharacterBuilder nimbleCharacterBuilder = new NimbleCharacterBuilder();
        player.setCharacterBuilder(nimbleCharacterBuilder);
        player.constructCharacter("234");
        character = player.getCharacter();
        int[][] stats = character.getStats();
        assertTrue(stats[1][0] >= stats[2][0] && stats[2][0] >= stats[0][0]);
    }

    /**
     * test tank character builder
     */
    @Test
    public void testTank() {
        TankCharacterBuilder tankCharacterBuilder = new TankCharacterBuilder();
        player.setCharacterBuilder(tankCharacterBuilder);
        player.constructCharacter("345");
        character = player.getCharacter();
        int[][] stats = character.getStats();
        assertTrue(stats[2][0] >= stats[1][0] && stats[1][0] >= stats[0][0]);
    }

}
