import entity.Product;
import util.DBUtil;
import dao.ProductDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {
            ProductDAO dao = new ProductDAO(conn);
            Scanner sc = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                System.out.println("\n--- Inventory Menu ---");
                System.out.println("1. Add product");
                System.out.println("2. List products");
                System.out.println("3. Update quantity");
                System.out.println("4. Delete product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");

                int choice = sc.nextInt();
                switch (choice) {
                    case 1 : {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        System.out.print("Name: ");
                        String name = sc.next();
                        System.out.print("Quantity: ");
                        int qty = sc.nextInt();
                        System.out.print("Price: ");
                        double price = sc.nextDouble();
                        dao.addProduct(new Product(id, name, qty, price));
                    }break;
                    case 2 : {
                        List<Product> products = dao.getAllProducts();
                        System.out.println("---- Inventory ----");
                        products.forEach(System.out::println);
                    }break;
                    case 3 : {
                        System.out.print("Product ID: ");
                        int pid = sc.nextInt();
                        System.out.print("New Quantity: ");
                        int newQty = sc.nextInt();
                        dao.updateQuantity(pid, newQty);
                    }break;
                    case 4 : {
                        System.out.print("Product ID to delete: ");
                        int delId = sc.nextInt();
                        dao.deleteProduct(delId);
                    }break;
                    case 5 : {
                        exit = true;
                    }break;
                    default :System.out.println("Invalid choice. Try again.");
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}