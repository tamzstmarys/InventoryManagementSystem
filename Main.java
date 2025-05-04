import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DatabaseManager db = new DatabaseManager();
        InventoryManager manager = new InventoryManager(db);

        while (true) {
            System.out.println("\n1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Quantity");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.print("ID: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Quantity: ");
                int qty = scanner.nextInt();
                manager.addNewProduct(id, name, qty);
            } else if (choice == 2) {
                manager.showAllProducts();
            } else if (choice == 3) {
                System.out.print("ID: ");
                int id = scanner.nextInt();
                System.out.print("New Quantity: ");
                int qty = scanner.nextInt();
                manager.updateProductQuantity(id, qty);
            } else if (choice == 4) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}
