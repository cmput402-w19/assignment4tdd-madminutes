package cmput402.tdd;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ItemTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeCost() {
        new Item("Tuna", -5.00f);
    }

    @Test
    public void testGetName() {
        Item tuna = new Item("Tuna");
        assertEquals("Tuna", tuna.getName());
    }
}
