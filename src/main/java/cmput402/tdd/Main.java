package cmput402.tdd;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        App app = new App();
        boolean exitPersonMenu = false, exitApplication = false;
        Person person = null;
        int option;

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
                            person = app.getPerson(scanner);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            exitPersonMenu = true;
                        }
                        // Loop to do actions on that person
                        while (!exitPersonMenu) {
                            System.out.println("Choose from these choices");
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
                                        exitPersonMenu = true;
                                        break;
                                    case 6:
                                        exitApplication = true;
                                        break;
                                    default:
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
                        app.createPerson(scanner);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    exitApplication = true;
                    break;
                default:
                    System.out.println("Please input a valid option.");
                    break;
            }
        }
    }
}