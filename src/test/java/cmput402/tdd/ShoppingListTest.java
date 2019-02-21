package cmput402.tdd;

import static org.mockito.Mockito.mock;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runners.JUnit4;

import static org.mockito.Mockito.*;
import org.mockito.invocation.*;
import org.mockito.Answers;

import java.util.Objects;

import junit.framework.*;

public class ShoppingListTest extends TestCase {

    private Item createTestItem(String name, Float cost) {
        Item item = mock(Item.class); // new Item(name, cost)); 
        when(item.getName()).thenReturn(name);
        when(item.getCost()).thenReturn(cost);
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
        assertTrue(list1.getName().equals("list1"));

        // Test2: test setName
        list1.setName("list2");
        assertTrue(list1.getName().equals("list2"));
        
         
    }

    @Test
    public void testOwner() { 
        ShoppingList list = new ShoppingList("list1");
        ShoppingList list2 = new ShoppingList("list2");
        list.setOwner("Samuel"); 

        assertTrue(list.getOwner().equals("Samuel"));
        assertTrue(list2.getOwner().equals(""));
    }

    @Test
    public void testCopyConstructor() { 
        ShoppingList list = new ShoppingList("list1");
        list.add(item1, 1);
        list.add(item2, 2);
        list.add(item5, 3);
        ShoppingList list2 = new ShoppingList(list);
    
        assertTrue(list.getName().equals("list1"));
        assertTrue(list2.items.equals(list.items));
        list2.add(item4, 2);
        assertFalse(list2.items.equals(list.items));
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
        assertTrue(list.items.size() == 1);
        assertTrue(list.items.get(a) == 1);
        assertTrue(list.items.containsKey(a));

        // Test2: Add to non empty map
        list.add(b, 2);
        assertTrue(list.items.size() == 2);
        assertTrue(list.items.containsKey(b));

        // Test3: Add existing Item to map
        list.add(c, 3);
        assertTrue(list.items.size() == 2);
        assertTrue(list.items.get(a) == 4);

        System.out.println(list.toString());

        // Test4: Add Item with same name but different cost
        list.add(d, 5);
        assertTrue(list.items.size() == 3);
        assertTrue(list.items.get(d) == 5);

        // Test5: Add Item by name, cost, quantity
        list.add("c", 2.0f, 4);
        assertTrue(list.items.containsKey(new Item("c", 2.0f)));
        assertTrue(list.items.size() == 4);
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
        assertTrue(list.items.size() == 2);
        assertFalse(list.items.containsKey(b));

        // Test1b: Remove by Item that doesn't exist
        assertFalse(list.remove(d));
        
        // Test2a: Remove by index
        assertTrue(list.remove(0));
        assertTrue(list.items.size() == 1);
        assertFalse(list.items.containsKey(a));

        // Test2b: Remove index out of bounds
        assertFalse(list.remove(5));
        assertFalse(list.remove(-1));

        // Test3b: Remove by name and cost that doesn't exit
        assertFalse(list.remove("d", 6.0f));

        // Test3a: Remove by name and cost
        assertTrue(list.remove("c", 1.0f));
        assertTrue(list.items.size() == 0);
        assertFalse(list.items.containsKey(c));

        // Test4: Remove from empty list
        assertFalse(list.remove("Item6", 1.0f));
        assertFalse(list.remove(item3));
        assertFalse(list.remove(1));
    }

    @Test
    public void testToString() { 
        ShoppingList list = new ShoppingList("list1");

        // Test1: empty map
        assertTrue(list.toString().equals("Grocery List:\n"));

        // Test2: non-empty map
        list.add(item1, 4);
        String out = "Grocery List:\n4x item1 $1.00\n";
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
        list.add(item5, 3);
        assertEquals(list.getNumberOfItems(), 6);
    }
}