package cmput402.tdd;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShoppingListTest {

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
}
