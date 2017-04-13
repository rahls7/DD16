package unittest;

import model.CharacterBuilder;
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
@SuiteClasses({AdaptToLevelTest.class, AttackTest.class, CampaignTest.class, CharacterBuilderTest.class, CharacterContentClass.class, CharacterMovementTest.class,

        ExchangeItemTest.class, ItemContentTest.class, LootChestTest.class, LootEnemyTest.class, MapLoadingTest.class, MapValidationTest.class, MapContentTest.class, StrategyTest.class, WeaponDecoratorTest.class, GenerateOrder.class})
public class TestSuite {

}
