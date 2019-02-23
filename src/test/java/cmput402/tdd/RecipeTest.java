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
        //Test 2: Rename "recipe" to "recipeRenamed"
        Recipe recipe = new Recipe("recipe1");
        recipe.setName("recipeRenamed");
        assertTrue(recipe.getName().equals("recipeRenamed"));
    }
}
