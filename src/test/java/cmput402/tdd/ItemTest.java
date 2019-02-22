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

    @Test
    public void testGetCost() {
        Item salmon = new Item("Salmon", 5.00f);
        assertEquals(salmon.getCost(), (5.00f), 0.0);
    }
}
