import java.util.ArrayList;

public class DatabaseManager {
    private ArrayList<Product> products;

    public DatabaseManager() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
