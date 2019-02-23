package cmput402.tdd;

import org.apache.commons.collections4.map.LinkedMap;

public class Person {

    private String name;

    private LinkedMap<String, ShoppingList> shoppingLists = new LinkedMap<>();

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LinkedMap<String, ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public boolean addShoppingList(ShoppingList shoppingList) {
        if(shoppingLists.containsKey(shoppingList.getName())) {
            return false;
        }
        shoppingLists.put(shoppingList.getName(), shoppingList);
        return true;
    }
}
