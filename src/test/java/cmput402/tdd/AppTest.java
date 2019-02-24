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
    public void testDisplayPeople() {
        App app = new App();
        String out = String.format("%d %20s %7s\n", "Id","Name","# Lists");
        assertEquals(app.displayPeople(), out);
        app.getPeople().
        out += String.format("%d %20s %7d\n", "1", "John", "1");
    }

}
