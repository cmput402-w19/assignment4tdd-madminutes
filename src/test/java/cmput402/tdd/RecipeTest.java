package cmput402.tdd;


import junit.framework.TestCase;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecipeTest extends TestCase {
    @Test
    public void testRecipeName() {
        //Test 1: Check initial name on creation of list
        Recipe recipe = new Recipe("recipe1");
        assertTrue(recipe.getName().equals("recipe1"));

    }
    @Test
    public void testSetRecipeName() {
        //Test 2: Rename "recipie1" to "recipeRenamed"
        Recipe recipe = new Recipe("recipe1");
        recipe.setName("recipeRenamed");
        assertTrue(recipe.getName().equals("recipeRenamed"));
    }
    @Test
    public void testAddRecipeItem() {

        Boolean status;
        Recipe recipe = new Recipe("Apple Pie");
        Item item1 = new Item("apple",1.0f);
        Item item2 = new Item("bread", 5.0f);
        Item item3 = new Item("butter", 5.0f);
        Item item4 = new Item("sugar", 2.0f);

        //Test 3a: Adding 1 item to empty recipe map
        status = recipe.add(item1);
        assertTrue(recipe.items.size() == 1);
        assertTrue(recipe.items.get(item1).equals(1));
        assertTrue(status);

        //Test 3b: Adding different item to existing recipe map
        status = recipe.add(item2);
        assertTrue(recipe.items.size() == 2);
        assertTrue(recipe.items.get(item2).equals(1));
        assertTrue(status);

        //Test 3c: Add existing item with specified quantity to existing recipe map
        status = recipe.add(item2, 3);
        assertTrue(recipe.items.size() == 2);
        assertTrue(recipe.items.get(item2).equals(4));
        assertTrue(status);

        //Test 3d: Add different item with quantity 0 to test edge case
        status = recipe.add(item3, 0);
        assertTrue(recipe.items.size() == 2);
        assertTrue(recipe.items.get(item3) == null);
        assertFalse(status);

        //Test 3d: Add different item with quantity -1 to test edge case
        status = recipe.add(item3, -1);
        assertTrue(recipe.items.size() == 2);
        assertTrue(recipe.items.get(item3) == null);
        assertFalse(status);

        //Test 3e: Adding 1 ingredient using add(item, int) method
        status = recipe.add(item4, 1);
        assertTrue(recipe.items.size() == 3);
        assertTrue(recipe.items.get(item4) == 1);
        assertTrue(status);

        //Test 3f: Adding ingredient which exists but negative amounts
        status = recipe.add(item4, -1);
        assertTrue(recipe.items.size() == 3);
        assertTrue(recipe.items.get(item4) == 1);
        assertFalse(status);
    }
}
