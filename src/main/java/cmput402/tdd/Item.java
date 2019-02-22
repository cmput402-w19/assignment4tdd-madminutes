package cmput402.tdd;

public class Item {

    private String name;

    private float cost;

    public Item(String name) {

    }

    public Item(String name, float cost) {
        if (cost < 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.cost = cost;
    }
    public String getName() {
        return null;
    }
}
