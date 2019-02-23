package cmput402.tdd;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
