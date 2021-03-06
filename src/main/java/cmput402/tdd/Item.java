package cmput402.tdd;

import java.util.Objects;

public class Item {

    private String name;

    private float cost;

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, float cost) {
        if(!checkValidCost(cost)) {
            throw new IllegalArgumentException();
        }
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
        if(!checkValidCost(cost)) {
            throw new IllegalArgumentException();
        }
        this.cost = cost;
    }

    /**
     * We pull out the check even though it seems trivial. In the event that we
     * need to add more it will be easier to refactor
     * @param cost
     *  The cost to check
     * @return
     *  Whether the cost is positive
     */
    private boolean checkValidCost(float cost) {
        return !(cost < 0);
    }

    /**
     * We want to implement our own equals method to specifically check that the name and cost are equal
     * @param o
     *  A Item object
     * @return
     *  Whether the object o has the same name and cost as this
     */
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

    /**
     * We need to override the hashCode because we are using a custom equals check
     * @return
     *  a hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, cost*100);
    }
}
