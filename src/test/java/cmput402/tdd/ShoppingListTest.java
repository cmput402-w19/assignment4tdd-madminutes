package cmput402.tdd;


import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShoppingListTest {

    private Item createTestItem(String name, Float cost) {
        Item item = mock(Item.class); // new Item(name, cost));
        when(item.getName()).thenReturn(name);
        when(item.getCost()).thenReturn(cost);
        return item;
    }

    private Item item1 = createTestItem("item1", 1.0f);
    private Item item2 = createTestItem("item2", 3.0f);
    private Item item3 = createTestItem("item2", 2.0f);
    private Item item4 = createTestItem("item3", 3.0f);

    @Test
    public void testName() {
        ShoppingList list1 = new ShoppingList("list1");

        // Test1: getName
        assertEquals("list1", list1.getName());

        // Test2: test setName
        list1.setName("list2");
        assertEquals("list2", list1.getName());
    }

    @Test
    public void testOwner() {
        ShoppingList list = new ShoppingList("list1");
        ShoppingList list2 = new ShoppingList("list2");
        list.setOwner("Samuel");

        assertEquals("Samuel", list.getOwner());
        assertEquals("", list2.getOwner());
    }

    /*
        We cannot test Add or remove methods with the use of mocked objects due to mockito limitations.
        Report from Mockito:
        1. you stub either of: final/private/equals()/hashCode() methods.
        Those methods *cannot* be stubbed/verified.

        This means that our LinkedMap will not index correctly with the use of mock objects since hashes
        which should be identical are in fact not.
    */
    @Test
    public void testAdd() {
        ShoppingList list = new ShoppingList("list1");

        Item a = new Item("a", 1.0f);
        Item b = new Item("b", 1.0f);
        Item c = new Item("a", 1.0f);
        Item d = new Item("a", 2.0f);

        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(c.hashCode());

        System.out.println("-------------");

        // Test1: Add to empty map
        list.add(a, 1);
        assertEquals(1, list.getItems().size());
        assertEquals(1, (int) list.getItems().get(a));
        assertTrue(list.getItems().containsKey(a));

        // Test2: Add to non empty map
        list.add(b, 2);
        assertEquals(2, list.getItems().size());
        assertTrue(list.getItems().containsKey(b));

        // Test3: Add existing Item to map
        list.add(c, 3);
        assertEquals(2, list.getItems().size());
        assertEquals(4, (int) list.getItems().get(a));

        System.out.println(list.toString());

        // Test4: Add Item with same name but different cost
        list.add(d, 5);
        assertEquals(3, list.getItems().size());
        assertEquals(5, (int) list.getItems().get(d));

        // Test5: Add Item by name, cost, quantity
        list.add("c", 2.0f, 4);
        assertTrue(list.getItems().containsKey(new Item("c", 2.0f)));
        assertEquals(4, list.getItems().size());
    }

    @Test
    public void testRemove() {
        ShoppingList list = new ShoppingList("list1");

        Item a = new Item("a", 1.0f);
        Item b = new Item("b", 1.0f);
        Item c = new Item("c", 1.0f);
        Item d = new Item("d", 6.0f);

        list.add(a, 1);
        list.add(b, 2);
        list.add(c, 3);


        // Test1a: Remove by Item
        assertTrue(list.remove(b));
        assertEquals(2, list.getItems().size());
        assertFalse(list.getItems().containsKey(b));

        // Test1b: Remove by Item that doesn't exist
        assertFalse(list.remove(d));

        // Test2a: Remove by index
        assertTrue(list.remove(0));
        assertEquals(1, list.getItems().size());
        assertFalse(list.getItems().containsKey(a));

        // Test2b: Remove index out of bounds
        assertFalse(list.remove(5));
        assertFalse(list.remove(-1));

        // Test3b: Remove by name and cost that doesn't exit
        assertFalse(list.remove("d", 6.0f));

        // Test3a: Remove by name and cost
        assertTrue(list.remove("c", 1.0f));
        assertEquals(0, list.getItems().size());
        assertFalse(list.getItems().containsKey(c));

        // Test4: Remove from empty list
        assertFalse(list.remove("Item6", 1.0f));
        assertFalse(list.remove(item1));
        assertFalse(list.remove(1));
    }

    @Test
    public void testTotalCost() {
        ShoppingList list = new ShoppingList("list1");

        // Test1: empty map
        assertEquals(0.0f, list.getTotalCost(), 0.0);

        // test2: non empty map
        list.add(item1, 1);
        list.add(item2, 2);
        list.add(item3, 3);
        assertEquals(13.0f, list.getTotalCost(),0.0);
    }

    @Test
    public void testGetNumberItems() {
        ShoppingList list = new ShoppingList("list1");

        // Test1: empty map
        assertEquals(0, list.getNumberOfItems());

        // test2: non empty map
        list.add(item1, 1);
        list.add(item2, 2);
        list.add(item4, 3);
        assertEquals(6, list.getNumberOfItems());
    }

    @Test
    public void testToString() {
        ShoppingList list = new ShoppingList("list1");

        // Test1: empty map
        assertEquals("Grocery List:\n", list.toString());

        // Test2: non-empty map
        list.add(item1, 4);
        String out = "Grocery List:\n4x item1 $1.00\n";
        assertEquals(list.toString(), out);

    }
}
