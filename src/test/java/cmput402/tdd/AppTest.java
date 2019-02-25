package cmput402.tdd;

import org.apache.commons.collections4.map.LinkedMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Scanner;
import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppTest {

    @Test
    public void testGetPerson() {
        App app = new App();
        Person person;

        ByteArrayInputStream in = new ByteArrayInputStream("John\n".getBytes());
        Scanner scanner = new Scanner(in);

        // Create a person
        try {
            person = app.getPerson(scanner, true);
            assertEquals(1, app.getPeople().size());
        } catch (Exception e) {
            fail();
        }

        scanner = new Scanner(new ByteArrayInputStream("John\n".getBytes()));
        // Find a person
        try {
            person = app.getPerson(scanner, false);
            assertEquals("John", person.getName());
        } catch (Exception e) {
            fail();
        }

        scanner = new Scanner(new ByteArrayInputStream("Smith\n".getBytes()));
        // Person does not exist
        try {
            person = app.getPerson(scanner, false);
            fail();
        } catch (Exception e) {
            assertEquals("Person does not exist.", e.getMessage());
        }
    }

    @Test
    public void testGetShoppingList() {
        App app = new App();
        ShoppingList shoppingList;

        Person person = mock(Person.class);
        when(person.getShoppingLists()).thenReturn(new LinkedMap<String, ShoppingList>(){{
            put("Monday Shopping List", new ShoppingList("Monday Shopping List"));
        }});

        ByteArrayInputStream inputStream = new ByteArrayInputStream("Monday Shopping List".getBytes());
        Scanner scanner = new Scanner(inputStream);

        // Successfully get Shopping List
        try {
            shoppingList = app.getShoppingList(scanner, person);
            assertEquals("Monday Shopping List", shoppingList.getName());
        } catch (Exception e) {
            fail();
        }

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
        try{
            item = app.createItem(scanner);
            assertEquals("item1", item.getName());
            assertEquals(2.0f, item.getCost(), 0.0);
        } catch (Exception e){
            fail();
        }

        //Test2: too many cents given
        in = new ByteArrayInputStream("item1\n2.888\n".getBytes());
        scanner = new Scanner(in); 
        try{
            item = app.createItem(scanner);
            fail();
        } catch (Exception e){
            System.out.println(e.getMessage());;
            assertEquals("Items do not support partial cents.", e.getMessage());
        }

        //Test3: bad format cost
        in = new ByteArrayInputStream("item1\n2.88.8\n".getBytes());
        scanner = new Scanner(in); 
        try{
            item = app.createItem(scanner);
            fail();
        } catch (Exception e){
            assertEquals("Incorrect cost format", e.getMessage());
        }

        //Test4: empty name given
        in = new ByteArrayInputStream("\n 2.88.8".getBytes());
        scanner = new Scanner(in); 
        try{
            item = app.createItem(scanner);
            fail();
        } catch (Exception e){
            assertEquals("Name cannot be empty.", e.getMessage());
        }

        //Test5: empty cost given
        in = new ByteArrayInputStream("item1\n\n".getBytes());
        scanner = new Scanner(in); 
        try{
            item = app.createItem(scanner);
            assertEquals(0.0f, item.getCost(), 0.0);
        } catch (Exception e){
            fail();
        }

        //Test6: item name > 20 characters given        
        in = new ByteArrayInputStream("item11111111111111111\n2\n".getBytes());
        scanner = new Scanner(in); 
        try{
            item = app.createItem(scanner);
            fail();
        } catch (Exception e){
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
        try{
            shoppingList = app.createShoppingList(scanner);
            Assert.assertNotNull(shoppingList);
            assertEquals(shoppingList.getName(), "list1");
        } catch (Exception e) {
            fail();
        }

        //Test2: empty name given
        in = new ByteArrayInputStream("\n 2.88.8".getBytes());
        scanner = new Scanner(in); 
        try{
            shoppingList = app.createShoppingList(scanner);
            fail();
        } catch (Exception e){
            assertEquals("Name cannot be empty.", e.getMessage());
        }

        //Test3: list name > 20 characters given
        in = new ByteArrayInputStream("list11111111111111111\n2\n".getBytes());
        scanner = new Scanner(in); 
        try{
            shoppingList = app.createShoppingList(scanner);
            fail();
        } catch (Exception e){
            assertEquals("ShoppingList name cannot be longer than 20 characters.", e.getMessage());
        }
    }

    @Test
    public void testEditShoppingList(){
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
    public void testDisplayPeople() {
        App app = new App();
        Person person = new Person("John");
        ShoppingList list = new ShoppingList("list1");
        person.addShoppingList(list);
        
        // Test1: empty list of people
        String out = String.format("%-3s|%-20s|%-7s\n", "Id","Name","# Lists");
        assertEquals(app.displayPeople(), out);

        // test2: non-empty list of people
        app.getPeople().put(person.getName(), person);
        out += String.format("%-3d|%-20s|%-7d\n", 0, "John", 1);
        assertEquals(app.displayPeople(), out);
        
    }

}
