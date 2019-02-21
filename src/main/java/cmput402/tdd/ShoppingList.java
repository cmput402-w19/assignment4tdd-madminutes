package cmput402.tdd;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingList {
    private String name;
    private String owner;
    public LinkedHashMap<Item,Integer> items = new LinkedHashMap<Item,Integer>();

    public ShoppingList(String name){return;}

    public ShoppingList(ShoppingList shoppingList){return;}


    public void setName(String Name) {}
    public String getName() {
        return null;
    }

    public void setOwner(String name) {}
    public String getOwner() { 
        return null;
    }

    public void add(Item item, Integer quantity) {}

    public void add(String Name, float cost, Integer quantity) {}
    
    public boolean remove(Item item){
        return false;
    }

    public boolean remove(Integer index){
        return false;
    }
    
    public boolean remove(String name){
        return false;
    }

    public float getTotalCost(){
        return 0;
    };

    public float getNumberOfItems(){
        return 0;
    };
    
    @Override
    public String toString(){
        return null;
    }






}
