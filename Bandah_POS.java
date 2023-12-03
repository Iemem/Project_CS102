package project_cs102;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author ACER
 */
public class Bandah_POS {

    public static final String fileName = "inventory.txt"
            + "";

    public static int printMenu() {
        int numMenuOptions = 2;

        System.out.println("- press (1) for sales procces");
        System.out.println("- press (2) to update inventory");
        System.out.println("- press (0) to exit");
        System.out.print(">>> ");

        Scanner input = new Scanner(System.in);
        int choice = -1;
        while (choice < 0 || choice > numMenuOptions) {
            try {
                choice = input.nextInt();
                input.nextLine();
                if ((choice < 0 || choice > numMenuOptions)) {
                    System.out.println("\nPlease enter a valid option from 1 to "
                            + numMenuOptions + " or 0 to Exit\n");
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nPlease enter a correct option (Numbers only)\n");
                break;
            }
        }
        return choice;
    }

    public static void main(String[] args) {

        try {
            GroceryStore store = new GroceryStore(fileName);

            System.out.println(" [Welcome to bandah pos system]");
            boolean goBack;
            while (true) {
                int choice = printMenu();
                switch (choice) {
                    case 1:
                        goBack = store.processSale();
                        while (goBack == false) {
                            goBack = store.processSale();
                        }
                        break;
                    case 2:
                        goBack = store.updateInventory();
                        while (goBack == false) {
                            goBack = store.updateInventory();
                        }
                        break;

                    case 0:
                        System.out.println("Thank you have a good day ");
                        System.exit(0);
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find inventory file!");
            System.exit(0);
        }
    }
}
