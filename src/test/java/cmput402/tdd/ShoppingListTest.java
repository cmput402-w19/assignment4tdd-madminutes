package cmput402.tdd;

import static org.mockito.Mockito.mock;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;

import junit.framework.*;

public class ShoppingListTest extends TestCase {

    private Item createTestItem(String name, Float cost) {
        Item item = mock(Item.class); 
        return item;
    }

    Item item1 = createTestItem("item1", 1.0f);
    Item item2 = createTestItem("item2", 3.0f);
    Item item3 = createTestItem("item2", 3.0f);
    Item item4 = createTestItem("item2", 2.0f);
    Item item5 = createTestItem("item3", 3.0f);

    @Test
    public void testName() { 
        ShoppingList list1 = new ShoppingList("list1"); 

        // Test1: getName
        assertTrue(list1.getName().equals("List1"));

        // Test2: test setName
        list1.setName("list2");
        assertTrue(list1.getName().equals("List2"));
        
         
    }

    @Test
    public void testOwner() { 
        ShoppingList list = new ShoppingList("list1");
        ShoppingList list2 = new ShoppingList("list2");
        list.setOwner("Samuel"); 

        assertTrue(list.getOwner().equals("Samuel"));
        assertTrue(list.getOwner().equals(null));
    }

    @Test
    public void testAdd() { 
        ShoppingList list = new ShoppingList("list1");

        // Test1: Add to empty map
        list.add(item1, 1);
        assertTrue(list.items.size() == 1);
        assertTrue(list.items.get(item1) == 1);
        assertTrue(list.items.containsKey(item1));

        // Test2: Add to non empty map
        list.add(item2, 2);
        assertTrue(list.items.size() == 2);
        assertTrue(list.items.containsKey(item2));

        // Test3: Add existing Item to map
        list.add(item3, 3);
        assertTrue(list.items.size() == 2);
        assertTrue(list.items.get(item2) == 5);

        // Test4: Add Item with same name but different cost
        list.add(item4, 2);
        assertTrue(list.items.size() == 3);
        assertTrue(list.items.get(item2) == 5);

        // Test5: Add Item by name, cost, quantity
        list.add("Item3", 2.0f, 4);
        assertTrue(list.items.size() == 4);
    }

    @Test
    public void testRemove() { 
        ShoppingList list = new ShoppingList("list1");

        list.add(item1, 1);
        list.add(item2, 2);
        list.add(item4, 3);

        // Test1a: Remove by Item
        assertTrue(list.remove(item2));
        assertTrue(list.items.size() == 2);
        assertFalse(list.items.containsKey(item2));

        // Test1b: Remove by Item that doesn't exist
        assertFalse(list.remove(item4));
        
        // Test2a: Remove by Iindex
        assertTrue(list.remove(2));
        assertTrue(list.items.size() == 1);
        assertFalse(list.items.containsKey(item3));

        // Test2b: Remove index out of bounds
        assertFalse(list.remove(5));
        assertFalse(list.remove(-1));
    }

    @Test
    public void testToString() { 
        ShoppingList list = new ShoppingList("list1");

        // Test1: empty map
        assertTrue(list.toString().equals("Grocery List:\n"));

        // Test2: non-empty map
        list.add(item1, 4);
        String out = "Grocery List:\n4x Item1 $1.00\n";
        assertTrue(list.toString().equals(out));

    }

    @Test
    public void testTotalCost() { 
        ShoppingList list = new ShoppingList("list1");

        // Test1: empty map
        assertEquals(list.getTotalCost(), 0.0f);

        // test2: non empty map
        list.add(item1, 1);
        list.add(item2, 2);
        list.add(item4, 3);
        assertEquals(list.getTotalCost(), 13.0f);
    }

    @Test
    public void testGetNumberItems() { 
        ShoppingList list = new ShoppingList("list1");

        // Test1: empty map
        assertEquals(list.getNumberOfItems(), 0);

        // test2: non empty map
        list.add(item1, 1);
        list.add(item2, 2);
        list.add(item3, 3);
        assertEquals(list.getNumberOfItems(), 6);
    }
}