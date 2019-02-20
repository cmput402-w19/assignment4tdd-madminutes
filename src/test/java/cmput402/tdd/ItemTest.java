package cmput402.tdd;

import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void testSetName() {
        Item salmon = new Item("Tuna");
        assertEquals("Tuna", salmon.getName());
        salmon.setName("Salmon");
        assertEquals("Salmon", salmon.getName());
    }

    @Test
    public void testSetCost() {
        Item tuna = new Item("Tuna", 5.00f);
        assertEquals(5.00f, tuna.getCost(), 0);
        tuna.setCost(10.00f);
        assertEquals(10.00f, tuna.getCost(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeCost() {
        Item tuna = new Item("Tuna");
        tuna.setCost(-5.00f);
    }

    @Test
    public void TestEquals() {
        Item tuna1 = new Item("Tuna", 2.00f);
        Item tuna2 = new Item("Tuna", 2.00f);
        assertTrue(tuna1.equals(tuna2));
    }
    @Test
    public void TestEqualsSameObject() {
        Item tuna = new Item("Tuna", 2.00f);
        assertTrue(tuna.equals(tuna));
    }

    @Test
    public void TestNotEqualsName() {
        Item tuna = new Item("Tuna", 2.00f);
        Item salmon = new Item("Salmon", 2.00f);
        assertFalse(tuna.equals(salmon));
    }

    @Test
    public void TestEqualsNull() {
        Item tuna = new Item("Tuna", 2.00f);
        Person person = new Person();
        assertFalse(tuna.equals(person));
    }

    @Test
    public void TestNotEqualsCost() {
        Item tuna1 = new Item("Tuna", 2.00f);
        Item tuna2 = new Item("Tuna", 5.00f);
        assertFalse(tuna1.equals(tuna2));
    }
}
