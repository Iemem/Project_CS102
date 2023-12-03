package project_cs102;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ACER
 */
public class GroceryStore {

    List<Product> productList;
    List<Sale> SalesList;

    public GroceryStore(String fileName) throws FileNotFoundException {
        this.productList = new ArrayList<>();
        this.SalesList = new ArrayList<>();
        loadInventoryFromFile(fileName);
    }

    private void loadInventoryFromFile(String fileName) throws FileNotFoundException {
        File inFile = new File(fileName);
        try (Scanner input = new Scanner(inFile)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] tokens = line.split(",");
                String name = tokens[0];
                double price = Double.valueOf(tokens[1]);
                int inventoryLevel = Integer.valueOf(tokens[2]);

                Product newProduct = new Product(name, price, inventoryLevel);

                if (productList.contains(newProduct)) {
                    Product oldProduct = findProduct(name);
                    oldProduct.setInventoryLevel(inventoryLevel);
                } else {
                    productList.add(newProduct);
                }
            }
        }
    }

    public boolean processSale() {
        Scanner input = new Scanner(System.in);
        Cart shoppingCart = new Cart();
        boolean isCheckout = false;

        while (!isCheckout) {
            printStoreProducts();
            System.out.println("Enter the name of the product you want to add to cart or (0) to go back");
            String itemName = input.nextLine().trim();
            if (itemName.equals("0")) {
                return true;
            }

            Product p = this.findProduct(itemName);
            if (p == null) {
                System.out.println("Cannot find the product, please make sure your spelling is correct!");
                return false;
            } else {
                addToCart(shoppingCart, p);
                if (shoppingCart.getSize() == 0) {
                    continue;
                }
                System.out.println("Do you want to continue or checkout?");
                System.out.println("1- Continue");
                System.out.println("2- Checkout");
                try {
                    int choice = input.nextInt();
                    input.nextLine();
                    if (choice > 2 || choice < 0) {
                        System.out.println("Enter a valid choice");
                    } else if (choice == 2) {
                        isCheckout = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("\nPlease enter a correct option (Numbers only)\n");
                }
            }
        }
        checkout(shoppingCart);
        return true;
    }

    public boolean updateInventory() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        printStoreProducts();
        System.out.println("- press (1) To write inventory to file");
        System.out.println("- press (2) To update inventory from a file ");
        System.out.println("- press (3) To add a new product to inventory");
        System.out.println("- press (4) To remove a product from inventory");
        System.out.println("- press (0) To go back");

        try {
            int choice = input.nextInt();
            input.nextLine();
            if (choice == 0) {
                return true;
            }

            if (choice > 4 || choice < 0) {
                System.out.println("Enter a correct choice");
                return false;
            } else if (choice == 1) {
                System.out.println("- press (1) To write inventory to the main file");
                System.out.println("- press (2) To write inventory to another file");
                choice = input.nextInt();
                input.nextLine();
                switch (choice) {
                    case 1:
                        writeInventoryToFile("mainFile.txt");
                        break;
                    case 2:
                        System.out.println("Enter the file name to write to: ");
                        String fileName = input.nextLine();
                        writeInventoryToFile(fileName);
                        break;
                    default:
                        System.out.println("Enter a correct choice");
                }
                return true;
            } else if (choice == 2) {
                updateInventoryFromFile();
                return true;
            } else if (choice == 3) {
                addNewProductToInventory();
                return true;
            } else {
                System.out.println("Enter the product name to remove: ");
                String name = input.nextLine();
                removeProduct(name);
                return true;
            }
        } catch (InputMismatchException e) {
            System.out.println("\nPlease enter a correct option (Numbers only)\n");
        }
        return true;
    }

    //------------------------------------------------------- 
    //helper methods
    public void printStoreProducts() {
        System.out.printf("%-15s%-10s%-10s\n", "Product Name", "Price", "Inventory Level");
        for (Product p : this.productList) {
            System.out.printf("%-15S%5.2f%10d\n", p.getName(), p.getPrice(), p.getInventoryLevel());
        }
    }

    public Product findProduct(String name) {
        //return  the product if found otherwise return null
        Product p = null;
        for (Product item : this.productList) {
            String productName = item.getName();
            if (productName.equalsIgnoreCase(name)) {
                p = item;
                break;
            }
        }
        return p;
    }

    public void addProduct(Product p) {
        this.productList.add(p);
    }

    public void removeProduct(String name) {
        Product item = findProduct(name);
        if (item == null) {
            System.out.println("The product does not exist");
        } else {
            this.productList.remove(item);
            Product.decreaseNumProducts();
            System.out.println(name + " has been removed from inventory.");
        }
    }

    public Cart addToCart(Cart shoppingCart, Product item) {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("How many " + item.getName() + " do you want? (enter quantity) ");
            int quantity = input.nextInt();
            input.nextLine();

            if (quantity > item.getInventoryLevel()) {
                throw new InputMismatchException("quantity exceeds current inventory level.");
            } else if (quantity < 1) {
                throw new InputMismatchException("quantity must be more than 0");
            } else {
                shoppingCart.addItem(item, quantity);
                updateInventoryLevel(item, quantity);

                System.out.println(shoppingCart);
            }

        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return shoppingCart;
    }

    public void checkout(Cart shoppingCart) {
        Scanner input = new Scanner(System.in);
        //String customerName, Cart cart, double discountRate
        System.out.println("Enter customer name: ");
        String name = input.nextLine();
        System.out.println("Enter discount rate between (0 to 100)");
        try {
            double discount = input.nextDouble();
            input.nextLine();

            Sale transaction = new Sale(name, shoppingCart, discount);
            SalesList.add(transaction);
            System.out.println(transaction);

        } catch (InputMismatchException e) {
            System.out.println("\nPlease enter a correct option (Numbers only)\n");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateInventoryLevel(Product item, int quantity) {
        int newInventoryLevel = item.getInventoryLevel() - quantity;
        item.setInventoryLevel(newInventoryLevel);
    }

    public void updateInventoryFromFile() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please ensure that the new file has the following format.");
        System.out.println("name,price,inventoryLevel");
        System.out.println("Enter file name: ");
        String fileName = input.nextLine();
        loadInventoryFromFile(fileName);
        System.out.println("Updated from " + fileName + " successfully.");

    }

    public void addNewProductToInventory() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter name of new product: ");
        String name = input.nextLine();
        Product p = findProduct(name);
        if (p != null) {
            System.out.println(name + " already exists.");
        } else {
            try {
                System.out.println("Enter product price: ");
                double price = input.nextDouble();
                input.nextLine();
                System.out.println("Enter Inventory amount: ");
                int inventoryLevel = input.nextInt();
                input.nextLine();
                p = new Product(name, price, inventoryLevel);
                addProduct(p);
                System.out.println("Producted Added Successfuly.");
                printStoreProducts();
            } catch (InputMismatchException e) {
                System.out.println("\nPlease enter a correct values\n");
            }
        }
    }

    public void writeInventoryToFile(String fileName) throws FileNotFoundException {
        File outFile = new File(fileName);
        PrintWriter pw = new PrintWriter(outFile);

        for (Product p : productList) {
            pw.println(p.getName() + "," + p.getPrice() + "," + p.getInventoryLevel());
        }
        pw.close();
        System.out.println("Inventory has been successfully written to " + fileName);
    }

}


