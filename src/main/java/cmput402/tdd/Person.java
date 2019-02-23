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

    public void addShoppingList(ShoppingList shoppingList) {

        String listName = shoppingList.getName();

        if(shoppingLists.containsKey(listName)) {
            throw new IllegalArgumentException();
        }

        shoppingLists.put(listName, shoppingList);
    }

    public void removeShoppingList(String name){

        if (!shoppingLists.containsKey(name)) {
            throw new IllegalArgumentException();
        }

        shoppingLists.remove(name);
    }

    public void removeShoppingList(int index){

        if (index < 0 || index >= shoppingLists.size()) {
            throw new IllegalArgumentException();
        }

        shoppingLists.remove(index);
    }
}
