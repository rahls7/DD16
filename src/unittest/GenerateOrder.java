package unittest;

import controller.PlayController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test the function of generating order
 * @author Ruijia Yang
 */
public class GenerateOrder {
    private PlayController pc;

    @Before
    /**
     * Initialize a play controller
     */
    public void before(){
        pc=new PlayController("123",11);
        pc.generateOrder();
    }

    @Test
    /**
     * Test the function of generating prder
     */
    public void test(){
        int l1=pc.getCharacters().size();
        int l2=pc.getOrder().size();
        System.out.println(l1+" "+l2);
        assertTrue(l1==l2);
    }
}
