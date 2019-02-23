package cmput402.tdd;

import org.apache.commons.collections4.map.LinkedMap;

public class Person {

    private String name;

    private LinkedMap<String, ShoppingList> shoppingLists;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LinkedMap<String, ShoppingList> getShoppingLists() {
        return null;
    }

    public boolean addShoppingList(ShoppingList shoppingList) {
        return false;
    }
}
