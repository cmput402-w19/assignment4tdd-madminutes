package cmput402.tdd;

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

}
