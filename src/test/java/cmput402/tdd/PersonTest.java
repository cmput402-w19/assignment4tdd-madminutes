package cmput402.tdd;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonTest {

    @Test
    public void testGetName() {
        Person person = new Person("John");
        assertEquals("John", person.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddShoppingList() {
        Person person = new Person("John");

        assertEquals(0, person.getShoppingLists().size());

        ShoppingList mockShoppingList = mock(ShoppingList.class);
        when(mockShoppingList.getName()).thenReturn("Monday Shopping List");

        person.addShoppingList(mockShoppingList);
        assertEquals(1, person.getShoppingLists().size());

        person.addShoppingList(mockShoppingList);
        assertEquals(1, person.getShoppingLists().size());
    }

    @Test
    public void testRemoveList() {
        Person person = new Person("person1");

        ShoppingList mockListOne = mock(ShoppingList.class);
        ShoppingList mockListTwo = mock(ShoppingList.class);

        when(mockListOne.getName()).thenReturn("List 1");
        when(mockListTwo.getName()).thenReturn("List 2");

        person.addShoppingList(mockListOne);
        person.addShoppingList(mockListTwo);

        // Remove by name that doesn't exist
        try {
            person.removeShoppingList("List 3");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        // Remove by index that doesn't exist
        try {
            person.removeShoppingList(-1);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            person.removeShoppingList(8);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        person.removeShoppingList(0);
        person.removeShoppingList("List 2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addRecipe() {
        Person person = new Person("John");

        assertEquals(0, person.getRecipes().size());

        Recipe mockRecipe = mock(Recipe.class);
        when(mockRecipe.getName()).thenReturn("Chicken Recipe");

        person.addRecipe(mockRecipe);
        assertEquals(1, person.getRecipes().size());

        person.addRecipe(mockRecipe);
        assertEquals(1, person.getRecipes().size());
    }

    @Test
    public void testRemoveRecipe() {
        Person person = new Person("John");

        Recipe mockRecipeOne = mock(Recipe.class);
        Recipe mockRecipeTwo = mock(Recipe.class);

        when(mockRecipeOne.getName()).thenReturn("Recipe 1");
        when(mockRecipeTwo.getName()).thenReturn("Recipe 2");

        person.addRecipe(mockRecipeOne);
        person.addRecipe(mockRecipeTwo);

        System.out.println("Added recipes");
        // Remove by name that doesn't exist
        try {
            person.removeRecipe("Recipe 3");
            assert false;
        } catch (Exception e) {
            System.out.println(e.toString());
            assertTrue(e instanceof IllegalArgumentException);
        }

        System.out.println("Hello");
        // Remove by index that doesn't exist
        try {
            person.removeRecipe(-1);
            assert false;
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            person.removeRecipe(8);
            assert false;
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        person.removeRecipe(0);
        person.removeRecipe("Recipe 2");
    }
}
