package unittest;

import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mo on 2017-04-12.
 */
public class StrategyTest {

    PCharacter pCharacter;
    @Before
    public void before(){
        pCharacter = new PCharacter("009", "2");
    }

    /**
     * test aggressive
     */
    @Test
    public void testAggressive(){
        Aggressive aggressive = new Aggressive();
        pCharacter.setStrategy(aggressive);
        assertEquals(pCharacter.getStrategy(), aggressive);
    }

    /**
     * Test friendly
     */
    @Test
    public void testFriendly(){
        Friendly friendly = new Friendly();
        pCharacter.setStrategy(friendly);
        assertEquals(friendly, pCharacter.getStrategy());
    }

    /**
     * Test computer
     */
    @Test
    public void testComputer(){
        Computer computer = new Computer();
        pCharacter.setStrategy(computer);
        assertEquals(computer, pCharacter.getStrategy());
    }

    /**
     * Test freezing
     */
    @Test
    public void testFreezing(){
        Freezing freezing = new Freezing();
        pCharacter.setStrategy(freezing);
        assertEquals(freezing, pCharacter.getStrategy());
    }

    /**
     * Test frightening
     */
    @Test
    public void testFrightening(){
        Frightening frightening = new Frightening();
        pCharacter.setStrategy(frightening);
        assertEquals(frightening, pCharacter.getStrategy());
    }

}
