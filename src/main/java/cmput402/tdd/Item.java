package cmput402.tdd;

public class Item {

    private String name;

    private float cost;

    public Item(String name) {
    }

    public Item(String name, float cost) {
    }

    public String getName() {
        return null;
    }

    public void setName(String name) {
    }

    public float getCost() {
        return 0;
    }

    public void setCost(float cost) {

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
        return false;
    }
}
