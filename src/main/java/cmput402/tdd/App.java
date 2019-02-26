package cmput402.tdd;
import java.util.Scanner;
import org.apache.commons.collections4.map.LinkedMap;
import java.util.Map;

public class App {

    private LinkedMap<String, Person> people = new LinkedMap<String, Person>();

    public LinkedMap<String, Person> getPeople() {
        return people;
    }

    public Person getPerson(Scanner input) throws Exception {
        System.out.print("Please type the name of a person:\n>");
        String personName = input.nextLine();
        
        Person person = null;
        if(getPeople().containsKey(personName)) {
            person = getPeople().get(personName);
        } else {
            throw new Exception("Person does not exist!");
        }
        return person;
    }

    public void createPerson(Scanner input) throws Exception {
        System.out.print("Please enter the name of the Person to create:\n>");
        String name = input.nextLine();
        Person person = new Person(name);
        if(people.containsKey(name)){
            throw new Exception(name+" already exists!");
        } else {
            people.put(name, person);
        }
    }

    public ShoppingList getShoppingList(Scanner input, Person person) throws Exception {
        System.out.print("Please type the name of the shopping list:\n>");
        String shoppingListName = input.nextLine();

        LinkedMap<String, ShoppingList> shoppingLists = person.getShoppingLists();

        if(!shoppingLists.containsKey(shoppingListName)) {
            throw new Exception("Shopping List does not exist.");
        }
        return shoppingLists.get(shoppingListName);
    }

    public Item createItem(Scanner input) throws Exception {
        System.out.print("Please enter the name of the item:\n>");
        String name = input.nextLine();
        System.out.print("Please enter the cost of the item (dollars.cents):\n>");
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
        System.out.print("Please enter the name of the shopping list:\n>");
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
        Main.clearConsole();
        while(true) {
            Main.clearConsole();
            System.out.println(shoppingList.toString());
            System.out.println(errorMsg);
            System.out.println("Please select an option to edit your shopping list:");
            System.out.print("1 - Add\n2 - Remove\n3 - Return\n\n>");
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
                        System.out.print("How many of this item would you like to add?:\n>");
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
                    errorMsg = "";
                    try{
                        System.out.print("Select an option to remove by:\n1. Name & Cost\n2. Position\n\n>");
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
                            break;
                        case 2:
                            System.out.print("Which Item would you like to remove?:\n>");
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
                    break;
                case 3:
                    Main.clearConsole();
                    return;
                default:
                    errorMsg = "Invalid Selection made.";
                    break;
            }
        }

    }
    public void editRecipe(Scanner input, Recipe recipe, Person person) throws Exception{
        Main.clearConsole();
        System.out.println("Please select an option to edit your recipe:");
        System.out.println("command format (ignore bracket):\n- (add 1)\n- (remove 5)\n- (rename \"recipe_name\")");
        System.out.print("\n>");
        int quantity;
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
        int spaceIndex = command.indexOf(" ");
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
                throw new Exception("Invalid quantity!");
            }
            try{
                item = createItem(input);
            }
            catch (Exception e) {
                throw new Exception("Failed to create item for recipe!");
            }
            if(action.equals("add")){
                if(quantity > 0){
                    recipe.add(item, quantity);
                    Main.clearConsole();
                    System.out.println("Added "+param+" "+item.getName()+" to recipe successfully!");
                }
                else{
                    throw new Exception("Quantity must be above 0");
                }

            }
            else if(action.equals("remove")){
                if(quantity >= 0 && recipe.items.get(item) >= quantity){
                    recipe.remove(item, quantity);
                    Main.clearConsole();
                    System.out.println("Removed "+param+" "+item.getName()+" from recipe successfully!");
                }
                else{
                    throw new Exception("Cannot remove negative quantity or greater than existing amount from recipe");
                }
            }
        }
        else if(action.equals("rename")){
            param = command.substring(spaceIndex+2, command.length()-1);
            if(param.length() <= 0 || param.length() > 40){
                throw new Exception("Recipe name cannot be blank or greater than 40 letters");
            }
            else {
                person.getRecipes().remove(recipe.getName());
                recipe.setName(param);
                Main.clearConsole();
                person.getRecipes().put(param, recipe);
                System.out.println("Successfully renamed recipe to "+param);
            }
        }
        else {
            throw new Exception("Invalid command");
        }
    }

    public Recipe createRecipe(Scanner input) throws Exception {
        System.out.print("Enter a name for the new recipe:\n>");
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

    public Recipe getRecipe(Scanner input, Person person) throws Exception{
        System.out.println("Enter the name of the recipe you wish to edit: ");
        String recipeName = input.nextLine();

        LinkedMap<String, Recipe> recipeList = person.getRecipes();
        if(!recipeList.containsKey(recipeName)) {
            throw new Exception("Recipe does not exist.");
        }
        return recipeList.get(recipeName);
    }

    public String displayRecipes(Person person){
        String out = person.getName()+" has the following recipes:\n";
        for (Map.Entry<String, Recipe> entry : person.getRecipes().entrySet()) {
            String recipeObj = entry.getValue().toString();
            out += recipeObj;
        }
        System.out.println(out);
        return out;
    }

    public String displayShoppingLists(Person person){
        String out = person.getName()+" has the following shopping lists:\n";
        for (Map.Entry<String, ShoppingList> entry : person.getShoppingLists().entrySet()) {
            String ShoppingListObj = entry.getKey()+"\n";
            out += ShoppingListObj;
        }
        System.out.println(out);
        return out;
    }

    public String displayPeople() {
        String out = "People:\n"; 
        for (Map.Entry<String, Person> entry : people.entrySet()) {
            out += entry.getKey()+"\n";
        }
        System.out.println(out);
        return out;
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
}
