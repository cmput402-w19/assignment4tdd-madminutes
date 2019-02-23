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

    @Test
    public void testAddShoppingList() {
        Person person = new Person("John");

        assertEquals(0, person.getShoppingLists().size());

        ShoppingList mockShoppingList = mock(ShoppingList.class);
        when(mockShoppingList.getName()).thenReturn("Monday Shopping List");

        assertTrue(person.addShoppingList(mockShoppingList));
        assertEquals(1, person.getShoppingLists().size());

        assertFalse(person.addShoppingList(mockShoppingList));
        assertEquals(1, person.getShoppingLists().size());
    }
}
