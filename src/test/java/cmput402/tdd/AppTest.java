package cmput402.tdd;

import org.apache.commons.collections4.map.LinkedMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppTest {

    @Test
    public void testCreatePerson() {
        App app = new App();
        Person person;

        ByteArrayInputStream in = new ByteArrayInputStream("John\n".getBytes());
        Scanner scanner = new Scanner(in);

        //Create a person
        try {
            app.createPerson(scanner);
            assertEquals(app.getPeople().size(), 1);
        } catch (Exception e) {
            fail();
        }

        in = new ByteArrayInputStream("John\n".getBytes());
        scanner = new Scanner(in);

        //Person already exists
        try {
            app.createPerson(scanner);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "John already exists!");
        }
    }
    @Test
    public void testGetPerson() {
        App app = new App();
        Person person;

        ByteArrayInputStream in = new ByteArrayInputStream("John\n".getBytes());
        Scanner scanner = new Scanner(in);

        scanner = new Scanner(new ByteArrayInputStream("John\n".getBytes()));
        // Find a person
        app.getPeople().put("John", new Person("John"));
        try {
            person = app.getPerson(scanner);
            assertEquals("John", person.getName());
        } catch (Exception e) {
            fail();
        }

        scanner = new Scanner(new ByteArrayInputStream("Smith\n".getBytes()));
        // Person does not exist
        try {
            person = app.getPerson(scanner);
            fail();
        } catch (Exception e) {
            assertEquals("Person does not exist!", e.getMessage());
        }
    }

    @Test
    public void testGetShoppingList() {
        App app = new App();
        ShoppingList shoppingList;

        Person person = mock(Person.class);
        when(person.getShoppingLists()).thenReturn(new LinkedMap<String, ShoppingList>() {{
            put("Monday Shopping List", new ShoppingList("Monday Shopping List"));
        }}).thenReturn(new LinkedMap<String, ShoppingList>());

        ByteArrayInputStream inputStream = new ByteArrayInputStream("Monday Shopping List".getBytes());
        Scanner scanner = new Scanner(inputStream);

        // Successfully get Shopping List
        try {
            shoppingList = app.getShoppingList(scanner, person);
            assertEquals("Monday Shopping List", shoppingList.getName());
        } catch (Exception e) {
            fail();
        }

        inputStream = new ByteArrayInputStream("Monday Shopping List".getBytes());
        scanner = new Scanner(inputStream);
        // Shopping List does not exist
        try {
            shoppingList = app.getShoppingList(scanner, person);
            fail();
        } catch (Exception e) {
            assertEquals("Shopping List does not exist.", e.getMessage());
        }
    }

    @Test
    public void testCreateItem() {
        App app = new App();
        Item item;

        //Test1: correct input
        ByteArrayInputStream in = new ByteArrayInputStream("item1\n2.00\n".getBytes());
        Scanner scanner = new Scanner(in);
        try {
            item = app.createItem(scanner);
            assertEquals("item1", item.getName());
            assertEquals(2.0f, item.getCost(), 0.0);
        } catch (Exception e) {
            fail();
        }

        //Test2: too many cents given
        in = new ByteArrayInputStream("item1\n2.888\n".getBytes());
        scanner = new Scanner(in);
        try {
            item = app.createItem(scanner);
            fail();
        } catch (Exception e) {
            assertEquals("Items do not support partial cents.", e.getMessage());
        }

        //Test3: bad format cost
        in = new ByteArrayInputStream("item1\n2.88.8\n".getBytes());
        scanner = new Scanner(in);
        try {
            item = app.createItem(scanner);
            fail();
        } catch (Exception e) {
            assertEquals("Incorrect cost format", e.getMessage());
        }

        //Test4: empty name given
        in = new ByteArrayInputStream("\n 2.88.8".getBytes());
        scanner = new Scanner(in);
        try {
            item = app.createItem(scanner);
            fail();
        } catch (Exception e) {
            assertEquals("Name cannot be empty.", e.getMessage());
        }

        //Test5: empty cost given
        in = new ByteArrayInputStream("item1\n\n".getBytes());
        scanner = new Scanner(in);
        try {
            item = app.createItem(scanner);
            assertEquals(0.0f, item.getCost(), 0.0);
        } catch (Exception e) {
            fail();
        }

        //Test6: item name > 20 characters given        
        in = new ByteArrayInputStream("item11111111111111111\n2\n".getBytes());
        scanner = new Scanner(in);
        try {
            item = app.createItem(scanner);
            fail();
        } catch (Exception e) {
            assertEquals("Item name cannot be longer than 20 characters.", e.getMessage());
        }
    }

    @Test
    public void testCreateShoppingList() {
        App app = new App();
        ShoppingList shoppingList;

        //Test1: correct input
        ByteArrayInputStream in = new ByteArrayInputStream("list1\n".getBytes());
        Scanner scanner = new Scanner(in);
        try {
            shoppingList = app.createShoppingList(scanner);
            Assert.assertNotNull(shoppingList);
            assertEquals(shoppingList.getName(), "list1");
        } catch (Exception e) {
            fail();
        }

        //Test2: empty name given
        in = new ByteArrayInputStream("\n 2.88.8".getBytes());
        scanner = new Scanner(in);
        try {
            shoppingList = app.createShoppingList(scanner);
            fail();
        } catch (Exception e) {
            assertEquals("Name cannot be empty.", e.getMessage());
        }

        //Test3: list name > 20 characters given
        in = new ByteArrayInputStream("list11111111111111111\n2\n".getBytes());
        scanner = new Scanner(in);
        try {
            shoppingList = app.createShoppingList(scanner);
            fail();
        } catch (Exception e) {
            assertEquals("ShoppingList name cannot be longer than 20 characters.", e.getMessage());
        }
    }

    @Test
    public void testEditShoppingList() {
        App app = new App();
        ShoppingList shoppingList = new ShoppingList("list1");
        Person person = new Person("person1");
        person.addShoppingList(shoppingList);

        //Test1: correct input
        ByteArrayInputStream in = new ByteArrayInputStream("1\nitem1\n2.00\n4\n3\n".getBytes());
        Scanner scanner = new Scanner(in);

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertEquals(1, person.getShoppingLists().get("list1").getItems().size());

        // Test2: invalid choice
        in = new ByteArrayInputStream("4\n3\n".getBytes());
        scanner = new Scanner(in);

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertEquals(1, person.getShoppingLists().get("list1").getItems().size());

        // Test3: invalid cost
        in = new ByteArrayInputStream("1\nitem2\nlpq.00\n5\n3\n".getBytes());
        scanner = new Scanner(in);

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertEquals(1, person.getShoppingLists().get("list1").getItems().size());

        // Test4: invalid quantity
        in = new ByteArrayInputStream("1\nitem2\n3.00\nlots\n3\n".getBytes());
        scanner = new Scanner(in);

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertEquals(1, person.getShoppingLists().get("list1").getItems().size());

        // Test5: remove invalid option
        in = new ByteArrayInputStream("2\ncat\n3\n".getBytes());
        scanner = new Scanner(in);

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertEquals(1, person.getShoppingLists().get("list1").getItems().size());

        // Test5: remove by item
        person.getShoppingLists().get("list1").add("item2", 2.0f, 4);
        in = new ByteArrayInputStream("2\n1\nitem2\n2.0\n3\n".getBytes());
        scanner = new Scanner(in);

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertEquals(1, person.getShoppingLists().get("list1").getItems().size());

        // Test6: remove by item not in list
        in = new ByteArrayInputStream("2\n1\nitem3\n2.0\n3\n".getBytes());
        scanner = new Scanner(in);

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertEquals(1, person.getShoppingLists().get("list1").getItems().size());

        // Test7: remove by invalid item
        in = new ByteArrayInputStream("2\n1\nitem3\ncat\n3\n".getBytes());
        scanner = new Scanner(in);

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertEquals(1, person.getShoppingLists().get("list1").getItems().size());

        // Test8: remove by index
        person.getShoppingLists().get("list1").add("item2", 2.0f, 4);
        in = new ByteArrayInputStream("2\n2\n0\n3\n".getBytes());
        scanner = new Scanner(in);

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertEquals(1, person.getShoppingLists().get("list1").getItems().size());

        // Test9: remove by invalid index
        in = new ByteArrayInputStream("2\n2\ncat\n3\n".getBytes());
        scanner = new Scanner(in);

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertEquals(1, person.getShoppingLists().get("list1").getItems().size());

        // Test10: remove by index not in list
        in = new ByteArrayInputStream("2\n2\n8\n3\n".getBytes());
        scanner = new Scanner(in);

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertEquals(1, person.getShoppingLists().get("list1").getItems().size());

        // Test11: invalid selection
        in = new ByteArrayInputStream("cat\n3\n".getBytes());
        scanner = new Scanner(in);

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertEquals(1, person.getShoppingLists().get("list1").getItems().size());
    }

    @Test
    public void testCreateRecipe() {
        App app = new App();
        Recipe recipe;

        //Test1: correct input
        ByteArrayInputStream in = new ByteArrayInputStream("recipe1\n".getBytes());
        Scanner scanner = new Scanner(in);
        try {
            recipe = app.createRecipe(scanner);
            Assert.assertNotNull(recipe);
            assertEquals(recipe.getName(), "recipe1");
        } catch (Exception e) {
            Assert.fail();
        }

        //Test2: empty name given
        in = new ByteArrayInputStream("\n".getBytes());
        scanner = new Scanner(in);
        try {
            recipe = app.createRecipe(scanner);
            fail();
        } catch (Exception e) {
            assertEquals("Name cannot be empty.", e.getMessage());
        }

        //Test3: list name > 40 characters given
        in = new ByteArrayInputStream("12121212121212121212121212121212121212121\n2\n".getBytes());
        scanner = new Scanner(in);
        try {
            recipe = app.createRecipe(scanner);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Recipe name cannot be longer than 40 characters.", e.getMessage());
        }
    }


    @Test
    public void testDisplayPeople() {
        App app = new App();
        Person person = new Person("John");
        ShoppingList list = new ShoppingList("list1");
        person.addShoppingList(list);

        // Test1: empty list of people
        String out = "People:\n";
        assertEquals(app.displayPeople(), out);

        // test2: non-empty list of people
        app.getPeople().put(person.getName(), person);
        out += "John\n";
        System.out.println(out);
        System.out.println(app.displayPeople());
        assertEquals(app.displayPeople(), out);

    }

    @Test
    public void testEditRecipe() {
        App app = new App();
        Recipe recipe = new Recipe("apple pie");
        Person person= new Person("john");

        Item item1 = new Item("apple", 4.0f);
        Item item2 = new Item("sugar", 2.0f);

        //Test 1: Add new item to recipe
        ByteArrayInputStream in = new ByteArrayInputStream("add 1\napple\n4.0".getBytes());
        Scanner scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, recipe.items.size());
        assertEquals(1, recipe.items.get(item1).intValue());

        //Test 2 Add an existing item to recipe again
        in = new ByteArrayInputStream("add 3\napple\n4.0".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
        } catch (Exception e) {
            Assert.fail();
        }
        assertEquals(1, recipe.items.size());
        assertEquals(4, recipe.items.get(item1).intValue());

        //Test 3 Add 0 quantity to recipe
        in = new ByteArrayInputStream("add 0\nsugar\n2.0".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Quantity must be above 0", e.getMessage());
        }
        assertEquals(1, recipe.items.size());
        assertEquals(4, recipe.items.get(item1).intValue());
        assertFalse(recipe.items.containsKey(item2));


        //Test 4 Add another new item to recipe
        in = new ByteArrayInputStream("add 3\nsugar\n2.0".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
        } catch (Exception e) {
            Assert.fail();
        }
        assertEquals(2, recipe.items.size());
        assertEquals(3, recipe.items.get(item2).intValue());
        assertEquals(4, recipe.items.get(item1).intValue());

        //Test 5 Remove item from recipe completely
        in = new ByteArrayInputStream("remove 4\napple\n4.0".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
        } catch (Exception e) {
            Assert.fail();
        }
        assertEquals(1, recipe.items.size());
        assertEquals(3, recipe.items.get(item2).intValue());
        assertFalse(recipe.items.containsKey(item1));

        //Test 6 Remove quantity greater than current existing quantity
        in = new ByteArrayInputStream("remove 4\nsugar\n2.0".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Cannot remove negative quantity or greater than existing amount from recipe", e.getMessage());
        }
        assertEquals(1, recipe.items.size());
        assertEquals(3, recipe.items.get(item2).intValue());
        assertFalse(recipe.items.containsKey(item1));

        //Test 7 Remove item partially from recipe
        in = new ByteArrayInputStream("remove 2\nsugar\n2.0".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
        } catch (Exception e) {
            Assert.fail();
        }

        assertEquals(1, recipe.items.size());
        assertEquals(1, recipe.items.get(item2).intValue());

        //Test 8 Remove 0 quantity from recipe
        in = new ByteArrayInputStream("remove 0\nsugar\n2.0".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            System.out.println(e);
            assertEquals("item cannot be removed from recipe", e.getMessage());
        }
        assertEquals(1, recipe.items.size());
        assertEquals(1, recipe.items.get(item2).intValue());

        //Test 9 Rename recipe to valid name
        in = new ByteArrayInputStream("rename 'super apple pie'".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
        } catch (Exception e) {
            Assert.fail();
        }
        assertEquals("super apple pie", recipe.getName());

        //Test 10 Rename recipe to empty string
        in = new ByteArrayInputStream("rename ''".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Recipe name cannot be blank or greater than 40 letters", e.getMessage());
        }

        assertEquals("super apple pie", recipe.getName());

        //Test 11 Rename recipe to string > 40 char
        in = new ByteArrayInputStream("rename 'appleappleappleappleappleappleappleapple1'".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Recipe name cannot be blank or greater than 40 letters", e.getMessage());
        }
        assertEquals("super apple pie", recipe.getName());

        //Test 12 Enter invalid rename command
        in = new ByteArrayInputStream("relabel 'apple juice'".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Invalid command", e.getMessage());
        }
        assertEquals("super apple pie", recipe.getName());

        //Test 13 Invalid command input
        in = new ByteArrayInputStream("foo 2\nsugar\n2.0".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Invalid command", e.getMessage());
        }
        assertEquals(1, recipe.items.size());
        assertEquals(1, recipe.items.get(item2).intValue());

        //Test 14 Invalid remove command input
        in = new ByteArrayInputStream("delete apple\nsugar\n2.0".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Invalid command", e.getMessage());
        }
        assertEquals(1, recipe.items.size());
        assertEquals(1, recipe.items.get(item2).intValue());

        //Test 15 Invalid add command input
        in = new ByteArrayInputStream("add apple\nsugar\n2.0".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Invalid quantity!", e.getMessage());
        }
        assertEquals(1, recipe.items.size());
        assertEquals(1, recipe.items.get(item2).intValue());

        //Test 16 Enter invalid rename command with < 40 char
        in = new ByteArrayInputStream("relabel 'appleappleappleappleappleappleappleapple1'".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Invalid command", e.getMessage());
        }
        assertEquals("super apple pie", recipe.getName());

        //Test 17 Rename recipe to string > 40 char with invalid command
        in = new ByteArrayInputStream("relabel 'appleappleappleappleappleappleappleapple1'".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Invalid command", e.getMessage());
        }
        assertEquals("super apple pie", recipe.getName());

        //Test 18 Create invalid item to add
        in = new ByteArrayInputStream("add 2\n\n2.5".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Failed to create item for recipe!", e.getMessage());
        }
        assertEquals(1, recipe.items.size());
        assertEquals(1, recipe.items.get(item2).intValue());

        //Test 19 Test user input empty string
        in = new ByteArrayInputStream("\n".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Invalid command format", e.getMessage());
        }
        assertEquals(1, recipe.items.size());
        assertEquals(1, recipe.items.get(item2).intValue());

        //Test 20 Create invalid format for add command
        in = new ByteArrayInputStream(" add\n".getBytes());
        scanner = new Scanner(in);
        try {
            app.editRecipe(scanner, recipe, person);
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Invalid command", e.getMessage());
        }
        assertEquals(1, recipe.items.size());
        assertEquals(1, recipe.items.get(item2).intValue());
    }

    @Test
    public void testGetRecipe() {
        App app = new App();
        Recipe recipe;

        Person person = mock(Person.class);
        when(person.getRecipes()).thenReturn(new LinkedMap<String, Recipe>() {{
            put("Test recipe", new Recipe("Test recipe"));
        }}).thenReturn(new LinkedMap<String, Recipe>());

        ByteArrayInputStream inputStream = new ByteArrayInputStream("Test recipe".getBytes());
        Scanner scanner = new Scanner(inputStream);

        // Successfully get recipe
        try {
            recipe = app.getRecipe(scanner, person);
            assertEquals("Test recipe", recipe.getName());
        } catch (Exception e) {
            fail();
        }

        inputStream = new ByteArrayInputStream("Test recipe".getBytes());
        scanner = new Scanner(inputStream);

        // Recipe that does not exist
        try {
            recipe = app.getRecipe(scanner, person);
            fail();
        } catch (Exception e) {
            assertEquals("Recipe does not exist.", e.getMessage());
        }
    }


    @Test
    public void testDisplayRecipe() {
        App app = new App();
        Person person = new Person("John");
        Recipe recipe1 = new Recipe("recipe1");
        Recipe recipe2 = new Recipe("recipe2");

        // Test1: empty list of ShoppingLists
        assertEquals("John has the following recipes:\n",app.displayRecipes(person));

        // test2: non-empty list of people
        person.addRecipe(recipe1);
        person.addRecipe(recipe2);
        assertEquals("John has the following recipes:\nrecipe1\nrecipe2\n", app.displayRecipes(person));
    }

    @Test
    public void testDisplayShoppingList() {
        App app = new App();
        Person person = new Person("John");
        ShoppingList list1 = new ShoppingList("list1");
        ShoppingList list2 = new ShoppingList("list2");

        // Test1: empty list of ShoppingLists
        assertEquals("John has the following shopping lists:\n",app.displayShoppingLists(person));

        // test2: non-empty list of people
        person.addShoppingList(list1);
        person.addShoppingList(list2);
        assertEquals("John has the following shopping lists:\nlist1\nlist2\n", app.displayShoppingLists(person));
    }
}
