package cmput402.tdd;
import java.util.Scanner;
import org.apache.commons.collections4.map.LinkedMap;
import java.util.Map;

public class App {

    public LinkedMap<String, Person> people = new LinkedMap<String, Person>();

    public LinkedMap<String, Person> getPeople() {
        return people;
    }

    public Person getPerson(Scanner input, boolean create) throws Exception {
        System.out.println("Please type the name of a person");
        String personName = input.nextLine();

        Person person;
        if (create) {
            person = createPerson(personName);
        } else {
            if(getPeople().containsKey(personName)) {
                person = getPeople().get(personName);
            } else {
                throw new Exception("Person does not exist.");
            }
        }
        return person;
    }

    private Person createPerson(String name) {
        Person person = new Person(name);
        getPeople().put(name, person);
        return person;
    }

    public ShoppingList getShoppingList(Scanner input, Person person) throws Exception {
        return null;
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
