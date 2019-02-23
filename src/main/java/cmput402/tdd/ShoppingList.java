package cmput402.tdd;

import org.apache.commons.collections4.map.LinkedMap;

import java.util.Map;

public class ShoppingList {

    private String name;

    private String owner = "";

    private LinkedMap<Item, Integer> items = new LinkedMap<>();

    public ShoppingList(String name) {
        this.name = name;
    }

    public ShoppingList(ShoppingList shoppingList) {
        this.name = shoppingList.name;
        this.items = shoppingList.items.clone();
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

    public boolean remove(Item item) {
        if (!items.containsKey(item)) {
            return false;
        }
        items.remove(item);
        return true;
    }

    public boolean remove(int index) {
        if (index < 0 || items.size() == 0) {
            return false;
        }

        if (items.size() < index-1) {
            return false;
        }

        items.remove(index);
        return true;
    }

    public boolean remove(String name, float cost) {
        Item item = new Item(name, cost);
        return this.remove(item);
    }

    public float getTotalCost() {
        float cost = 0;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            cost += entry.getValue() * entry.getKey().getCost();
        }
        return cost;
    }

    public int getNumberOfItems() {
        int count = 0;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            count += entry.getValue();
        }
        return count;
    }

    public void addToShoppingList(Recipe recipe){

    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Grocery List:\n");
        float cost;
        String name;
        Integer quantity;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            cost = entry.getKey().getCost();
            name = entry.getKey().getName();
            quantity = entry.getValue();
            out.append(String.format("%dx %s $%.2f\n", quantity, name, cost));
        }
        return out.toString();
    }
}
