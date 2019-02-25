package cmput402.tdd;
import java.util.Scanner;
import org.apache.commons.collections4.map.LinkedMap;
import java.util.Map;

public class App {

    public LinkedMap<String, Person> people = new LinkedMap<String, Person>();

    public LinkedMap<String, Person> getPeople() {
        return people;
    }

    public Item createItem(Scanner input) throws Exception {
        System.out.println("Please enter the name of the item:");
        String name = input.nextLine();
        System.out.println("Please enter the cost of the item (dollars.cents):");
        String str_cost = input.nextLine();
        float cost = 0;

        if (name.length() == 0){
            throw new Exception("Name cannot be empty.");
        }
        if (name.length() > 20){
            throw new Exception("Item name cannot be longer than 20 characters.");
        }
        try{
            if (str_cost.equals("")){
                cost = 0.0f;
            }
            else{
                cost = Float.parseFloat(str_cost);
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid number");
            throw new NumberFormatException("Incorrect cost format");
        }
        if (precision(cost) > 2) {
            
            throw new NumberFormatException("Items do not support partial cents.");
        }
        Item item = new Item(name, cost);
        return item;
    }
    
    public ShoppingList createShoppingList(Scanner input) throws Exception {
        String name = input.nextLine();

        if (name.length() == 0){
            throw new Exception("Name cannot be empty.");
        }
        if (name.length() > 20){
            throw new Exception("ShoppingList name cannot be longer than 20 characters.");
        }
       ShoppingList shoppingList = new ShoppingList(name);
        return shoppingList;
    }

    public void editShoppingList(Scanner input, ShoppingList shoppingList){
        String response;
        String errorMsg = "";
        Item item = null;
        int quantity;
        int selection = 10;
        int index = 0;
        clearConsole();
        while(true) {
            clearConsole();
            System.out.println(shoppingList.toString());
            System.out.println(errorMsg);
            System.out.println("Please select an option to edit your shopping list:");
            System.out.println("1. Add\n2. Remove\n3. Return\n\n");
            response = input.nextLine();
            try{
                selection = Integer.parseInt(response);
                errorMsg = "";
            } catch (Exception e) {
                errorMsg = "Invalid Selection made.";
            }
            if (!errorMsg.equals("")){
                continue;
            }

            switch (selection){
                case 1:
                    try{
                        item = createItem(input);
                    } catch (Exception e) {
                        errorMsg = e.getMessage();
                        continue;
                    }
                    try {
                        System.out.println("How many of this item would you like to add?:");
                        response = input.nextLine();
                        quantity = Integer.parseInt(response);
                        shoppingList.add(item, quantity);
                    } catch (Exception e){
                        errorMsg = "Invalid quantity selected.";
                        continue;
                    }
                    errorMsg = "Item added.";
                    break;
                case 2:
                    int option = -1;
                    try{
                        System.out.println("Select an option to remove by:\n1. Name & Cost\n2. Position");
                        response = input.nextLine();
                        option = Integer.parseInt(response);
                        errorMsg = "";
                    } catch (Exception e) {
                        errorMsg = "Invalid option selected.";
                    }
                    if (!errorMsg.equals("")){
                        break;
                    }
                    switch (option){
                        case 1:
                            try {
                                item = createItem(input);
                            } catch (Exception e) {
                                errorMsg = e.getMessage();
                            }
                            if (!errorMsg.equals("")){
                                break;
                            }
                            if (!shoppingList.remove(item)){
                                errorMsg = "Item not found in shopping list.";
                            }
                            errorMsg = "Item removed.";
                        case 2:
                            System.out.println("Which Item would you like to remove?:");
                            response = input.nextLine();
                            try {
                                index = Integer.parseInt(response);
                            } catch (Exception e) {
                                errorMsg = "Invalid position selection";
                            }
                            if (!errorMsg.equals("")){
                                break;
                            }
                            if (!shoppingList.remove(index)){
                                errorMsg = "Item not found in shopping list.";
                            }
                            errorMsg = "Item removed.";
                            break;
                        default:
                            break;
                    }
                case 3:
                    clearConsole();
                    return;
                default:
                    errorMsg = "Invalid Selection made.";
                    break;
            }
        }

    }
    public void editRecipe(Scanner input, Recipe recipe) throws Exception{
        clearConsole();
        System.out.println("Please select an option to edit your recipe:");
        System.out.println("command format (ignore bracket): (add 1) (remove 5) (rename \"apples\")");
        System.out.println("*add\n*remove\n*rename\n");
        Integer quantity;
        Item item;
        String param;
        String command = input.nextLine();
        /*
        Eg:
        command = add 5

        function will split command into variables action and param
        action = add
        quantity = 5
         */
        Integer spaceIndex = command.indexOf(" ");
        if(spaceIndex == -1){
            throw new Exception("Invalid command format");
        }
        String action = command.substring(0, spaceIndex);

        if(action.equals("add") || action.equals("remove")) {
            param = command.substring(spaceIndex + 1, command.length());
            try{
                quantity = Integer.parseInt(param);
            }
            catch(Exception e){
                throw new Exception("Please enter a positive whole number for quantity");
            }
            try{
                item = createItem(input);
            }
            catch (Exception e) {
                throw new Exception("Failed to create item for recipe");
            }
            if(action.equals("add")){
                if(quantity > 0){
                    recipe.add(item, quantity);
                    System.out.println("Added "+param+" "+item.getName()+" to recipe successfully");
                }
                else{
                    throw new Exception("Quantity must be above 0");
                }

            }
            else if(action.equals("remove")){
                if(recipe.items.get(item) > quantity  && quantity > 0){
                    recipe.remove(item, quantity);
                    System.out.println("Removed "+param+" "+item.getName()+" from recipe successfully");
                }

                else if(recipe.items.get(item) == quantity) {
                    recipe.remove(item);
                }
                else{
                    throw new Exception("Could not remove item from recipe");
                }
            }
        }
        else if(action.equals("rename")){
            param = command.substring(spaceIndex+2, command.length()-1);
            if(param.length() <= 0 || param.length() > 40){
                throw new Exception("could not rename recipe");
            }
            else {
                recipe.setName(param);
            }
        }
        else {
            throw new Exception("Invalid command");
        }
    }

    public Recipe createRecipe(Scanner input) throws Exception {
        String name = input.nextLine();

        if (name.length() == 0){
            throw new Exception("Name cannot be empty.");
        }
        if (name.length() > 40){
            throw new Exception("Recipe name cannot be longer than 40 characters.");
        }
        Recipe recipe = new Recipe(name);
        return recipe;
    }

    public String displayPeople() {
        StringBuilder out = new StringBuilder(String.format("%-3s|%-20s|%-7s\n", "Id","Name","# Lists")); 
        int i = 0;
        for (Map.Entry<String, Person> entry : people.entrySet()) {
            out.append(String.format("%-3d|%-20s|%-7d\n", i, entry.getKey(), entry.getValue().getShoppingLists().size()));
        }
        return out.toString();
    }

    // source: https://stackoverflow.com/questions/9553354/how-do-i-get-the-decimal-places-of-a-floating-point-number-in-javascript
    private int precision(float  a) {
        int e = 1;
        int p = 0;
        while (a*e - Math.floor(a*e) != 0) { 
            e *= 10; 
            p++; }
        return p;
    }

    public final static void clearConsole(){
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            }
            else {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e) {
            //  Handle any exceptions.
        }
    }
}
