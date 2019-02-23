package cmput402.tdd;

import org.junit.Test;
import junit.framework.TestCase;

import static org.mockito.Mockito.*;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

public class AppTest extends TestCase{

    private Item createTestItem(String name, Float cost) {
        Item item = mock(Item.class); // new Item(name, cost)); 
        when(item.getName()).thenReturn(name);
        when(item.getCost()).thenReturn(cost);
        return item;
    }

    @Test
    public void testCreateItem() {
        App app = new App();
        
        //Test1: correct input
        ByteArrayInputStream in = new ByteArrayInputStream("item1\n 2.00\n".getBytes());
        Scanner scanner = new Scanner(in); 
        Item item = app.createItem(scanner);
        assertTrue(item.getName() == "item1");
        assertTrue(item.getCost() == 2.0f);

        //Test2: too many cents given
        in = new ByteArrayInputStream("item1\n 2.888\n".getBytes());
        scanner = new Scanner(in); 
        try{
            item = app.createItem(scanner);
            fail();
        } catch (Exception e){
            assertTrue(e.getMessage().equals("Items do not support partial cents."));
        }
        //Test3: bad format cost
        in = new ByteArrayInputStream("item1\n 2.88.8\n".getBytes());
        scanner = new Scanner(in); 
        try{
            item = app.createItem(scanner);
            fail();
        } catch (Exception e){
            assertTrue(e.getMessage().equals("Incorrect cost Format"));
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
            fail();
        } catch (Exception e){
            assertTrue(e.getMessage().equals("Name cannot be empty."));
        }
        //Test6: item name > 20 characters given        
        in = new ByteArrayInputStream("item11111111111111111\n2\n".getBytes());
        scanner = new Scanner(in); 
        try{
            item = app.createItem(scanner);
            fail();
        } catch (Exception e){
            assertTrue(e.getMessage().equals("Name cannot be longer than 20 characters."));
        }
    }

}
