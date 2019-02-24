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
            System.out.println("1 - Find a Person");
            System.out.println("2 - Create a Person");
            System.out.println("3 - Quit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    try {
                        person = app.getPerson(scanner, false);
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