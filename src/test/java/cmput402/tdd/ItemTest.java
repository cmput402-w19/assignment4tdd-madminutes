package cmput402.tdd;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    private Item createTestItem(String name, Float cost) {
        return new Item(name, cost);
    }

    @Test
    public void testGetName() {
        Item tuna = createTestItem("Tuna", 2.00f);
        assertEquals("Tuna", tuna.getName());
    }

    @Test
    public void testGetCost() {
        Item salmon = createTestItem("Salmon", 5.00f);
        assertEquals(salmon.getCost(), (5.00f), 0.0);
    }

    @Test
    public void TestNotEquals() {
        Item tuna = createTestItem("Tuna", 2.00f);
        Item salmon = createTestItem("Salmon", 5.00f);
        assertNotEquals(tuna, salmon);
    }

    @Test
    public void TestEquals() {
        Item tuna = createTestItem("Tuna", 2.00f);
        assertTrue(tuna.equals(tuna));
    }
}
