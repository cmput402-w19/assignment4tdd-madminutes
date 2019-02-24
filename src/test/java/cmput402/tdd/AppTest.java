package cmput402.tdd;

import org.junit.Test;
import junit.framework.TestCase;

import java.util.Scanner;
import java.io.ByteArrayInputStream;

public class AppTest extends TestCase{

    @Test
    public void testCreateItem() {
        App app = new App();
        Item item;
        
        //Test1: correct input
        ByteArrayInputStream in = new ByteArrayInputStream("item1\n2.00\n".getBytes());
        Scanner scanner = new Scanner(in); 
        try{
            item = app.createItem(scanner);
            assertEquals(item.getName(), "item1");
            assertEquals(item.getCost(), 2.0f);
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
            assertTrue(e.getMessage().equals("Items do not support partial cents."));
        }

        //Test3: bad format cost
        in = new ByteArrayInputStream("item1\n2.88.8\n".getBytes());
        scanner = new Scanner(in); 
        try{
            item = app.createItem(scanner);
            fail();
        } catch (Exception e){
            assertTrue(e.getMessage().equals("Incorrect cost format"));
        }

        //Test4: empty name given
        in = new ByteArrayInputStream("\n 2.88.8".getBytes());
        scanner = new Scanner(in); 
        try{
            item = app.createItem(scanner);
            fail();
        } catch (Exception e){
            assertTrue(e.getMessage().equals("Name cannot be empty."));
        }

        //Test5: empty cost given
        in = new ByteArrayInputStream("item1\n\n".getBytes());
        scanner = new Scanner(in); 
        try{
            item = app.createItem(scanner);
            assertEquals(item.getCost(), 0.0f);
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
            assertTrue(e.getMessage().equals("Item name cannot be longer than 20 characters."));
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
            assertFalse(shoppingList == null);  
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
            assertTrue(e.getMessage().equals("Name cannot be empty."));
        }

        //Test3: list name > 20 characters given
        in = new ByteArrayInputStream("list11111111111111111\n2\n".getBytes());
        scanner = new Scanner(in); 
        try{
            shoppingList = app.createShoppingList(scanner);
            fail();
        } catch (Exception e){
            assertTrue(e.getMessage().equals("ShoppingList name cannot be longer than 20 characters."));
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
        assertTrue(person.getShoppingLists().get("list1").getItems().size() == 1);

        // Test2: invalid choice
        in = new ByteArrayInputStream("4\n3\n".getBytes());
        scanner = new Scanner(in); 

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertTrue(person.getShoppingLists().get("list1").getItems().size() == 1);

        // Test3: invalid cost
        in = new ByteArrayInputStream("1\nitem2\nlpq.00\n5\n3\n".getBytes());
        scanner = new Scanner(in); 

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertTrue(person.getShoppingLists().get("list1").getItems().size() == 1);

        // Test4: invalid quantity
        in = new ByteArrayInputStream("1\nitem2\n3.00\nlots\n3\n".getBytes());
        scanner = new Scanner(in); 

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertTrue(person.getShoppingLists().get("list1").getItems().size() == 1);

        // Test5: remove by item
        person.getShoppingLists().get("list1").add("item2", 2.0f, 4);
        in = new ByteArrayInputStream("2\n1\nitem2\n2.0\n3\n".getBytes());
        scanner = new Scanner(in); 

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertTrue(person.getShoppingLists().get("list1").getItems().size() == 1);

        // Test6: remove by item not in list
        in = new ByteArrayInputStream("2\n1\nitem3\n2.0\n3\n".getBytes());
        scanner = new Scanner(in); 

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertTrue(person.getShoppingLists().get("list1").getItems().size() == 1);

        // Test7: remove by invalid item
        in = new ByteArrayInputStream("2\n1\nitem3\ncat\n3\n".getBytes());
        scanner = new Scanner(in); 

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertTrue(person.getShoppingLists().get("list1").getItems().size() == 1);

        // Test8: remove by index
        person.getShoppingLists().get("list1").add("item2", 2.0f, 4);
        in = new ByteArrayInputStream("2\n2\n0\n3\n".getBytes());
        scanner = new Scanner(in); 

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertTrue(person.getShoppingLists().get("list1").getItems().size() == 1);
    
        // Test9: remove by invalid index
        in = new ByteArrayInputStream("2\n2\ncat\n3\n".getBytes());
        scanner = new Scanner(in); 

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertTrue(person.getShoppingLists().get("list1").getItems().size() == 1);

        // Test9: remove by index not in list
        in = new ByteArrayInputStream("2\n2\n8\n3\n".getBytes());
        scanner = new Scanner(in); 

        app.editShoppingList(scanner, person.getShoppingLists().get("list1"));
        assertTrue(person.getShoppingLists().get("list1").getItems().size() == 1);
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
