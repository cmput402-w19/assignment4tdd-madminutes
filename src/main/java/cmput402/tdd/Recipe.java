package cmput402.tdd;
import org.apache.commons.collections4.map.LinkedMap;

import java.util.Map;

public class Recipe {

    private String name = "";
    public LinkedMap<Item, Integer> items = new LinkedMap<Item, Integer>();

    public Recipe(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public Boolean add(Item item, Integer quantity){
        return false;
    }
    public Boolean add(Item item){
        return false;
    }
}