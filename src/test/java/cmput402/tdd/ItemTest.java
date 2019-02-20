package cmput402.tdd;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

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

    @Test
    public void TestNotEquals() {
        Item tuna = new Item("Tuna", 2.00f);
        Item salmon = new Item("Salmon", 5.00f);
        assertNotEquals(tuna, salmon);
    }

    @Test
    public void TestEquals() {
        Item tuna = new Item("Tuna", 2.00f);
        assertTrue(tuna.equals(tuna));
    }
}
