package cmput402.tdd;

import org.apache.commons.collections4.map.LinkedMap;

public class ShoppingList {

    private String name;

    private String owner = "";

    private LinkedMap<Item, Integer> items = new LinkedMap<>();

    public ShoppingList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String name) {
        this.owner = name;
    }

    public String getOwner() {
        return owner;
    }

    public LinkedMap<Item, Integer> getItems() {
        return items;
    }

    public void add(Item item, Integer quantity) {
        if (items.containsKey(item)) {
            items.put(item, items.get(item) + quantity);
        }
        else {
            items.put(item, quantity);
        }
    }

    public void add(String name, float cost, Integer quantity) {
        Item item = new Item(name, cost);
        this.add(item, quantity);
    }
}
