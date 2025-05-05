import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection conn;

    public void connect() {
        try {
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
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
