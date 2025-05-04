public class InventoryManager {
    private DatabaseManager db;

    public InventoryManager(DatabaseManager db) {
        this.db = db;
    }

    public void addNewProduct(int id, String name, int quantity) {
        Product p = new Product(id, name, quantity);
        db.addProduct(p);
        System.out.println("Product added: " + p);
    }

    public void showAllProducts() {
        for (Product p : db.getAllProducts()) {
            System.out.println(p);
        }
    }

    public void updateProductQuantity(int id, int newQty) {
        Product p = db.getProductById(id);
        if (p != null) {
            p.setQuantity(newQty);
            System.out.println("Updated: " + p);
        } else {
            System.out.println("Product not found.");
        }
    }
}