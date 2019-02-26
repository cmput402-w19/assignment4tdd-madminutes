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

    public void add(Item item, Integer quantity) throws Exception{
        if(items.get(item) == null && quantity > 0){
            items.put(item, quantity);
        }
        else if(items.get(item) != null && quantity > 0){
            items.put(item, items.get(item)+quantity);
        }
        else{
            throw new Exception("Cannot add negative quantity of items");
        }
    }
    public void add(Item item){
        items.put(item, 1);
    }

    public void remove(Item item, Integer quantity) throws Exception{
        if(items.get(item) != null && items.get(item)> quantity && quantity > 1){
            items.put(item, items.get(item)-quantity);
        }
        else if(items.get(item) != null && items.get(item) == quantity){
            items.remove(item);
        }
        else{
            throw new Exception("item cannot be removed from recipe");
        }
    }

    public void remove(Item item) throws Exception{
        if(items.get(item) != null){
            items.remove(item);
        }
        else{
            throw new Exception("item cannot be removed from recipe");
        }
    }

    @Override
    public String toString(){
        StringBuilder out =new StringBuilder(this.getName()+"\n");
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            Item ingredient = entry.getKey();
            Integer quantity = entry.getValue();
            out.append("  - "+ingredient.getName() + " x" + quantity.toString() + "\n");
        }
        return out.toString();
    }
}
