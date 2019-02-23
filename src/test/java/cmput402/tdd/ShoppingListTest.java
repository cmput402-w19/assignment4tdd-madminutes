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
    private Item item5 = createTestItem("item2", 3.0f);

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

    @Test
    public void testCopyConstructor() {
        ShoppingList list = new ShoppingList("list1");
        list.add(item1, 1);
        list.add(item2, 2);
        list.add(item4, 3);
        ShoppingList list2 = new ShoppingList(list);

        assertEquals("list1", list.getName());
        assertEquals(list2.getItems(), list.getItems());
        list2.add(item5, 2);
        assertNotEquals(list2.getItems(), list.getItems());
    }

    @Test
    public void testAddToShoppingList(){
        ShoppingList list = new ShoppingList("list1");

        Recipe recipe1 = new Recipe("Apple Pie");
        Item item1 = new Item("apple",1.0f);
        Item item2 = new Item("bread", 5.0f);
        Item item3 = new Item("butter", 5.0f);

        Recipe recipe2 = new Recipe("Lemonade");
        Item item4 = new Item("lemon",4.0f);
        Item item5 = new Item("water", 1.0f);

        Recipe recipe3 = new Recipe("Apple Juice");

        //Adding ingredients for apple pie
        recipe1.add(item1, 3);
        recipe1.add(item2, 2);
        recipe1.add(item3, 1);

        //Adding ingredients for lemonade
        recipe2.add(item4, 3);
        recipe2.add(item5, 2);

        //Adding ingredients for apple juice
        recipe3.add(item5, 2);
        recipe3.add(item1, 4);

        //Test 5a: Add recipe to empty shopping list
        list.addToShoppingList(recipe1);
        assertEquals(3, list.getItems().size());
        assertEquals(3, list.getItems().get(item1).intValue());
        assertEquals(2, list.getItems().get(item2).intValue());
        assertEquals(1, list.getItems().get(item3).intValue());

        //Test 5b: Add different recipe to existing shopping list
        list.addToShoppingList(recipe2);
        assertEquals(5, list.getItems().size());
        assertEquals(3, list.getItems().get(item4).intValue());
        assertEquals(2, list.getItems().get(item5).intValue());

        //Test 5c: Adding recipe with ingredients which already exist in shopping list
        list.addToShoppingList(recipe3);
        assertEquals(5, list.getItems().size());
        assertEquals(7, list.getItems().get(item1).intValue());
        assertEquals(4, list.getItems().get(item5).intValue());
    }
    @Test
    public void testRemoveFromShoppingList(){
        ShoppingList list = new ShoppingList("list1");

        Recipe recipe1 = new Recipe("Apple Pie");
        Recipe recipe2 = new Recipe("Lemonade");
        Recipe recipe3 = new Recipe("Apple Juice");
        Recipe recipe4 = new Recipe("Empty Recipe");
        Recipe recipe5 = new Recipe("Special Recipe");

        //Adding ingredients for apple pie
        recipe1.add(item1, 3);
        recipe1.add(item2, 2);
        recipe1.add(item3, 1);

        //Adding ingredients for lemonade
        recipe2.add(item4, 3);
        recipe2.add(item5, 2);

        //Adding ingredients for apple juice
        recipe3.add(item5, 2);
        recipe3.add(item1, 4);

        //Adding ingredients for special recipe
        recipe5.add(item1, 3);

        list.addToShoppingList(recipe1);
        list.addToShoppingList(recipe2);
        list.addToShoppingList(recipe3);

        //Test 6a: Remove recipe with overlapping items from shopping list
        list.removeFromShoppingList(recipe3);
        assertEquals(5, list.getItems().size());
        assertEquals(3, list.getItems().get(item4).intValue());
        assertEquals(2, list.getItems().get(item5).intValue());

        //Test 6b: Remove recipe with no ingredients from shopping list
        list.removeFromShoppingList(recipe4);
        assertEquals(5, list.getItems().size());
        assertEquals(3, list.getItems().get(item1).intValue());
        assertEquals(2, list.getItems().get(item2).intValue());
        assertEquals(1, list.getItems().get(item3).intValue());
        assertEquals(3, list.getItems().get(item4).intValue());
        assertEquals(2, list.getItems().get(item5).intValue());

        //Test 6c: Remove multiple recipes from shopping list
        list.removeFromShoppingList(recipe2);
        assertEquals(3, list.getItems().size());
        assertEquals(false, list.getItems().containsKey(item4));
        assertEquals(false, list.getItems().containsKey(item5));

        //Test 6d: Remove all recipes from shopping list
        list.removeFromShoppingList(recipe1);
        assertEquals(0, list.getItems().size());
        assertEquals(false, list.getItems().containsKey(item1));
        assertEquals(false, list.getItems().containsKey(item2));
        assertEquals(false, list.getItems().containsKey(item3));
        //Test 6e: Remove recipe with ingredients which do not exist in shopping list
        list.addToShoppingList(recipe5);
        recipe5.add(item2, 2);
        list.removeFromShoppingList(recipe5);
        assertEquals(false, list.getItems().containsKey(item1));
        assertEquals(false, list.getItems().containsKey(item2));

        //Test 6f: Remove recipe with ingredients more than amount existing in shopping list
        list.addToShoppingList(recipe5);
        recipe5.add(item1, 2);
        list.removeFromShoppingList(recipe5);
        assertEquals(true, list.getItems().containsKey(item1));
        assertEquals(3, list.getItems().get(item1).intValue());

    }
}
