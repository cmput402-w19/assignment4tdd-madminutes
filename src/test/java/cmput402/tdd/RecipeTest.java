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
    @Test
    public void testRemoveRecipeItem(){
        Boolean status;
        Recipe recipe = new Recipe("Apple Pie");
        Item item1 = new Item("apple",1.0f);
        Item item2 = new Item("bread", 5.0f);
        Item item3 = new Item("butter", 5.0f);
        Item item4 = new Item("sugar", 4.0f);

        recipe.add(item1, 5);
        recipe.add(item2, 5);
        recipe.add(item3, 5);

        //Test 4a: Remove ingredient from recipe
        status = recipe.remove(item1);
        assertTrue(recipe.items.size() == 2);
        assertTrue(recipe.items.get(item1) == null);
        assertTrue(status);

        //Test 4b: Attempt to remove ingredient which does not exist in recipe
        status = recipe.remove(item4);
        assertTrue(recipe.items.size() == 2);
        assertTrue(recipe.items.get(item4) == null);
        assertFalse(status);

        //Test 4c: Attempt to remove more than available quantity from recipe
        status = recipe.remove(item2, 7);
        assertTrue(recipe.items.size() == 2);
        assertTrue(recipe.items.get(item2) == 5);
        assertFalse(status);

        //Test 4d: Test edge case of removing 0 quantity
        status = recipe.remove(item2, 0);
        assertTrue(recipe.items.size() == 2);
        assertTrue(recipe.items.get(item2) == 5);
        assertFalse(status);

        //Test 4e: Remove negative amount of quantity
        status = recipe.remove(item2, -1);
        assertTrue(recipe.items.size() == 2);
        assertTrue(recipe.items.get(item2) == 5);
        assertFalse(status);

        //Test 4f: Remove partial amount of ingredient from total quantity
        status = recipe.remove(item2, 3);
        assertTrue(recipe.items.size() == 2);
        assertTrue(recipe.items.get(item2) == 2);
        assertTrue(status);

        //Test 4g: Remove remaining amount of ingredient
        status = recipe.remove(item2, 2);
        assertTrue(recipe.items.size() == 1);
        assertTrue(recipe.items.get(item2) == null);
        assertTrue(status);

        //Test 4h: Remove ingredient which does not exist in recipe
        status = recipe.remove(item4, 3);
        assertTrue(recipe.items.size() == 1);
        assertTrue(recipe.items.get(item4) == null);
        assertFalse(status);

        //Test 4i: Remove zero amount of ingredient from recipe
        status = recipe.remove(item3, 0);
        assertTrue(recipe.items.size() == 1);
        assertTrue(recipe.items.get(item3) == 5);
        assertFalse(status);

        //Test 4i: Remove more than available amount of ingredient from recipe
        status = recipe.remove(item3, 7);
        assertTrue(recipe.items.size() == 1);
        assertTrue(recipe.items.get(item3) == 5);
        assertFalse(status);
    }

    @Test
    public void testToString(){
        Recipe recipe1 = new Recipe("Apple Pie");
        Item item1 = new Item("apple",1.0f);
        Item item2 = new Item("bread", 5.0f);
        Item item3 = new Item("butter", 5.0f);

        Recipe recipe2 = new Recipe("");

        //Adding ingredients for apple pie
        recipe1.add(item1, 3);
        recipe1.add(item2, 2);
        recipe1.add(item3, 1);

        //Test 5a: Test empty recipe
        String out = "\n";
        assertTrue(recipe2.toString().equals(out));

        //Test 5b: Test valid recipe toString output
        out = "Apple Pie\napple - x3\nbread - x2\nbutter - x1\n";
        assertTrue(recipe1.toString().equals(out));
    }


}
