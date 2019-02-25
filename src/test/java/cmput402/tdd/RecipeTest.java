package cmput402.tdd;


import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecipeTest extends TestCase {

    private Item createTestItem(String name, Float cost) {
        Item item = mock(Item.class); // new Item(name, cost));
        when(item.getName()).thenReturn(name);
        when(item.getCost()).thenReturn(cost);
        return item;
    }

    private Item item1 = createTestItem("apple",1.0f);
    private Item item2 = createTestItem("bread", 5.0f);
    private Item item3 = createTestItem("butter", 5.0f);
    private Item item4 = createTestItem("sugar", 2.0f);


    @Test
    public void testRecipeName() {
        //Test 1: Check initial name on creation of list
        Recipe recipe = new Recipe("recipe1");
        assertEquals("recipe1", recipe.getName());

    }
    @Test
    public void testSetRecipeName() {
        //Test 2: Rename "recipe1" to "recipeRenamed"
        Recipe recipe = new Recipe("recipe1");
        recipe.setName("recipeRenamed");
        assertEquals("recipeRenamed", recipe.getName());
    }
    @Test
    public void testAddRecipeItem() {

        Recipe recipe = new Recipe("Apple Pie");

        //Test 3a: Adding 1 item to empty recipe map
        recipe.add(item1);
        assertEquals(1, recipe.items.size());
        assertEquals(1, recipe.items.get(item1).intValue());

        //Test 3b: Adding different item to existing recipe map
        recipe.add(item2);
        assertEquals(2, recipe.items.size());
        assertEquals(1, recipe.items.get(item2).intValue());

        //Test 3c: Add existing item with specified quantity to existing recipe map
        try {
            recipe.add(item2, 3);
        }
        catch(Exception e){
            Assert.fail();
        }
        assertEquals(2, recipe.items.size());
        assertEquals(4, recipe.items.get(item2).intValue());

        //Test 3d: Add different item with quantity 0 to test edge case
        try {
            recipe.add(item3, 0);
        }
        catch(Exception e){
            assertEquals("Cannot add negative quantity of items", e.getMessage());
        }
        assertEquals(2, recipe.items.size());
        assertEquals(null, recipe.items.get(item3));

        //Test 3d: Add different item with quantity -1 to test edge case
        try {
            recipe.add(item3, -1);
        }
        catch(Exception e){
            assertEquals("Cannot add negative quantity of items", e.getMessage());
        }
        assertEquals(2, recipe.items.size());
        assertEquals(null, recipe.items.get(item3));


        //Test 3e: Adding 1 ingredient using add(item, int) method
        try {
            recipe.add(item4, 1);
        }
        catch(Exception e){
            Assert.fail();
        }
        assertEquals(3, recipe.items.size());
        assertEquals(1, recipe.items.get(item4).intValue());


        //Test 3f: Adding ingredient which exists but negative amounts
        try {
            recipe.add(item4, -1);
        }
        catch(Exception e){
            assertEquals("Cannot add negative quantity of items", e.getMessage());
        }
        assertEquals(3, recipe.items.size());
        assertEquals(1, recipe.items.get(item4).intValue());
    }


    @Test
    public void testRemoveRecipeItem(){

        Boolean status;
        Recipe recipe = new Recipe("Apple Pie");

        try {
            recipe.add(item1, 5);
            recipe.add(item2, 5);
            recipe.add(item3, 5);
        }
        catch(Exception e){
            Assert.fail();
        }
        //Test 4a: Remove ingredient from recipe
        try {
            recipe.remove(item1);
        }
        catch(Exception e){
            Assert.fail();
        }
        assertEquals(2, recipe.items.size());
        assertEquals(null, recipe.items.get(item1));

        //Test 4b: Attempt to remove ingredient which does not exist in recipe
        try {
            recipe.remove(item4);
        }
        catch(Exception e){
            assertEquals("item cannot be removed from recipe", e.getMessage());
        }
        assertEquals(2, recipe.items.size());
        assertEquals(null, recipe.items.get(item4));


        //Test 4c: Attempt to remove more than available quantity from recipe
        try {
            recipe.remove(item2, 7);
        }
        catch(Exception e){
            assertEquals("item cannot be removed from recipe", e.getMessage());
        }
        assertEquals(2, recipe.items.size());
        assertEquals(5, recipe.items.get(item2).intValue());

        //Test 4d: Test edge case of removing 0 quantity
        try {
            recipe.remove(item2, 0);
        }
        catch(Exception e){
            assertEquals("item cannot be removed from recipe", e.getMessage());
        }
        assertEquals(2, recipe.items.size());
        assertEquals(5, recipe.items.get(item2).intValue());

        //Test 4e: Remove negative amount of quantity
        try {
            recipe.remove(item2, -1);
        }
        catch(Exception e){
            assertEquals("item cannot be removed from recipe", e.getMessage());
        }
        assertEquals(2, recipe.items.size());
        assertEquals(5, recipe.items.get(item2).intValue());

        //Test 4f: Remove partial amount of ingredient from total quantity
        try {
            recipe.remove(item2, 3);
        }
        catch(Exception e){
            Assert.fail();
        }
        assertEquals(2, recipe.items.size());
        assertEquals(2, recipe.items.get(item2).intValue());

        //Test 4g: Remove remaining amount of ingredient
        try {
            recipe.remove(item2, 2);
        }
        catch(Exception e){
            Assert.fail();
        }
        assertEquals(1, recipe.items.size());
        assertEquals(null, recipe.items.get(item2));

        //Test 4h: Remove ingredient which does not exist in recipe
        try {
            recipe.remove(item4, 3);
        }
        catch(Exception e){
            assertEquals("item cannot be removed from recipe", e.getMessage());
        }
        assertEquals(1, recipe.items.size());
        assertEquals(null, recipe.items.get(item4));

        //Test 4i: Remove zero amount of ingredient from recipe
        try {
            recipe.remove(item3, 0);
        }
        catch(Exception e){
            assertEquals("item cannot be removed from recipe", e.getMessage());
        }
        assertEquals(1, recipe.items.size());
        assertEquals(5, recipe.items.get(item3).intValue());

        //Test 4i: Remove more than available amount of ingredient from recipe
        try {
            recipe.remove(item3, 7);
        }
        catch(Exception e){
            assertEquals("item cannot be removed from recipe", e.getMessage());
        }
        assertEquals(1, recipe.items.size());
        assertEquals(5, recipe.items.get(item3).intValue());
    }

    @Test
    public void testToString(){
        Recipe recipe = new Recipe("Apple Pie");
        Recipe recipe2 = new Recipe("");

        //Adding ingredients for apple pie
        try {
            recipe.add(item1, 3);
            recipe.add(item2, 2);
            recipe.add(item3, 1);
        }
        catch(Exception e){
            Assert.fail();
        }

        //Test 5a: Test empty recipe
        String out = "\n";
        assertEquals(out, recipe2.toString());

        //Test 5b: Test valid recipe toString output
        out = "Apple Pie\napple - x3\nbread - x2\nbutter - x1\n";
        assertEquals(out, recipe.toString());
    }


}
