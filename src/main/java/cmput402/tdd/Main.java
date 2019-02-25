package cmput402.tdd;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        App app = new App();
        boolean quit = false;
        Person person = null;

        // Loop to get a person
        while (person == null && !quit) {
            System.out.println("Choose from these choices");
            System.out.println("-------------------------\n");
            System.out.println("1 - Select a Person");
            System.out.println("2 - Create a Person");
            System.out.println("3 - Quit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    try {
                        person = app.getPerson(scanner, false);
                        // Loop to do actions on that person
                        while (!quit) {
                            System.out.println("Choose from these choices");
                            System.out.println("-------------------------\n");
                            System.out.println("1 - Create a Shopping List");
                            System.out.println("2 - Edit a Shopping List");
                            System.out.println("3 - Create a Recipe");
                            System.out.println("4 - Edit a Recipe");
                            System.out.println("5 - Quit");

                            int option = scanner.nextInt();

                            try {
                                switch (option) {
                                    case 1:
                                        app.createShoppingList(scanner);
                                        break;
                                    case 2:
                                        ShoppingList shoppingList = app.getShoppingList(scanner, person);
                                        app.editShoppingList(scanner, shoppingList);
                                        break;
                                    case 3:
                                        // app.createRecipe(scanner);
                                        break;
                                    case 4:
                                        // app.editRecipe(scanner);
                                        break;
                                    case 5:
                                        quit = true;
                                        break;
                                    default:
                                        System.out.println("Please input a valid option.");
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
                        person = app.getPerson(scanner, true);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    quit = true;
                    break;
                default:
                    System.out.println("Please input a valid option.");
            }
        }
    }
}