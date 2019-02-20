package cmput402.tdd;

public class Item {

    private String name;

    private float cost;

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, float cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        Item item = (Item) o;
        return item.getName().equals(name) && item.getCost() == cost;
    }
}
