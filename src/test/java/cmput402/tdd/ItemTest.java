package cmput402.tdd;

import org.junit.Test;

public class ItemTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeCost() {
        new Item("Tuna", -5.00f);
    }
}
