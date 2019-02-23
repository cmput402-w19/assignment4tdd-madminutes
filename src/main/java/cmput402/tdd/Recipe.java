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
        if(items.get(item) == null && quantity > 0){
            items.put(item, quantity);
            return true;
        }
        else if(items.get(item) != null && quantity > 0){
            items.put(item, items.get(item)+quantity);
            return true;
        }
        else{
            return false;
        }
    }
    public Boolean add(Item item){
        items.put(item, 1);
        return true;
    }

    public Boolean remove(Item item, Integer quantity){
        if(items.get(item) != null && items.get(item)> quantity && quantity > 1){
            items.put(item, items.get(item)-quantity);
            return true;
        }
        else if(items.get(item) != null && items.get(item) == quantity){
            items.remove(item);
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean remove(Item item){
        if(items.get(item) != null){
            items.remove(item);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString(){
        return null;
    }
}
