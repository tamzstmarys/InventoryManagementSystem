import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;

public class DatabaseManager {

    private Connection conn;

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection("jdbc:sqlite:inventory.db");
            Statement stmt = conn.createStatement();

            String inventorySql = "CREATE TABLE IF NOT EXISTS inventory (" +
                                  "item_id INTEGER PRIMARY KEY," +
                                  "item_name TEXT NOT NULL," +
                                  "item_quantity INTEGER NOT NULL," +
                                  "item_location TEXT NOT NULL)";
            stmt.execute(inventorySql);

            String ordersSql = "CREATE TABLE IF NOT EXISTS orders (" +
                               "order_id INTEGER PRIMARY KEY," +
                               "order_date DATE NOT NULL," +
                               "customer_name TEXT NOT NULL," +
                               "order_status TEXT NOT NULL)";
            stmt.execute(ordersSql);
            
            String shipmentsSql = "CREATE TABLE IF NOT EXISTS shipments (" +
                                  "shipment_id INTEGER PRIMARY KEY," +
                                  "destination TEXT NOT NULL," +
                                  "shipment_date DATE NOT NULL," +
                                  "shipment_status TEXT NOT NULL)";
            stmt.execute(shipmentsSql);

            System.out.println("Database connected. Tables are ready.");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public void addProduct(Product p) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO inventory (item_id, item_name, item_quantity, item_location) VALUES (?, ?, ?, ?)")) {

            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getName());
            stmt.setInt(3, p.getQuantity());
            stmt.setString(4, "Aisle 1");
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:inventory.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM inventory")) {

            while (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("item_name");
                int quantity = rs.getInt("item_quantity");
                productList.add(new Product(id, name, quantity));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
        return productList;
    }

    public Product getProductById(int id) {
        Product product = null;
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:inventory.db");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM inventory WHERE item_id = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("item_name");
                int quantity = rs.getInt("item_quantity");
                product = new Product(id, name, quantity);
            }

        } catch (SQLException e) {
            System.out.println("Error finding product: " + e.getMessage());
        }
        return product;
    }

    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
