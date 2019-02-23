package cmput402.tdd;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonTest {

    @Test
    public void testGetName() {
        Person person = new Person("John");
        assertEquals("John", person.getName());
    }
}
