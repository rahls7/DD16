package unittest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for map editor.
 *
 * @author Jiayao Zhou
 * @version 1.0.0
 */

@RunWith(Suite.class)
@SuiteClasses({MapValidationTest.class, MapContentTest.class})
public class TestSuite {

}
