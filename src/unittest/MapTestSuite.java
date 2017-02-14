package unittest;

/**
 * Created by Alleria on 2017/2/14.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({MapValidationTest.class, MapContentTest.class})
public class MapTestSuite {

}
