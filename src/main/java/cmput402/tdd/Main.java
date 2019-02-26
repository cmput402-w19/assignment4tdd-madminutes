package cmput402.tdd;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        App app = new App();
        boolean exitPersonMenu = false, exitApplication = false;
        Person person = null;
        int option;

        clearConsole();
        // Loop to get a person
        while (!exitApplication) {
            exitPersonMenu = false;
            System.out.println("Choose from these choices");
            System.out.println("-------------------------\n");
            System.out.println("1 - Select a Person");
            System.out.println("2 - Create a Person");
            System.out.println("3 - Quit");

            try{
                option = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                option = -1;
            }

            switch (option) {
                case 1:
                    try {
                        try {
                            clearConsole();
                            app.displayPeople();
                            person = app.getPerson(scanner);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            exitPersonMenu = true;
                        }
                        // Loop to do actions on that person
                        while (!exitPersonMenu) {
                            clearConsole();
                            System.out.println("Please select from these choices");
                            System.out.println("-------------------------\n");
                            System.out.println("1 - Create a Shopping List");
                            System.out.println("2 - Edit a Shopping List");
                            System.out.println("3 - Create a Recipe");
                            System.out.println("4 - Edit a Recipe");
                            System.out.println("5 - Go back to select a person");
                            System.out.println("6 - Quit");

                            try{
                                option = Integer.parseInt(scanner.nextLine());
                            } catch (Exception e) {
                                option = -1;
                            }

                            try {
                                switch (option) {
                                    case 1:
                                        try{
                                            ShoppingList list = app.createShoppingList(scanner);
                                            person.getShoppingLists().put(list.getName(), list);
                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());
                                        }
                                        break;
                                    case 2:
                                        app.displayShoppingLists(person);
                                        ShoppingList shoppingList = app.getShoppingList(scanner, person);
                                        
                                        app.editShoppingList(scanner, shoppingList);
                                        break;
                                    case 3:
                                         Recipe newRecipe = app.createRecipe(scanner);
                                         person.addRecipe(newRecipe);
                                        break;
                                    case 4:
                                        app.displayRecipes(person);
                                        Recipe recipe = app.getRecipe(scanner, person);
                                        app.editRecipe(scanner, recipe);
                                        break;
                                    case 5:
                                        exitPersonMenu = true;
                                        break;
                                    case 6:
                                        exitPersonMenu = true;
                                        exitApplication = true;
                                        break;
                                    default:
                                        clearConsole();
                                        System.out.println("Please input a valid option.");
                                        break;
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        clearConsole();
                        app.createPerson(scanner);
                        clearConsole();
                    } catch (Exception e) {
                        clearConsole();
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    exitApplication = true;
                    break;
                default:
                    clearConsole();
                    System.out.println("Please input a valid option.");
                    break;
            }
        }
    }

    public final static void clearConsole(){
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                //Runtime.getRuntime().exec("cls");
            }
            else {
                System.out.println("trying to clear");
                System.out.print("\033[H\033[2J");
            }
        }
        catch (final Exception e) {
            //  Handle any exceptions.
        }
    }
}