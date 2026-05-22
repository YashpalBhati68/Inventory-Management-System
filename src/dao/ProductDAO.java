package dao;

import java.sql.*;
import java.util.*;

import entity.Product;

public class ProductDAO {
    private final Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    public void addProduct(Product p) throws SQLException {
        String sql = "INSERT INTO products (product_id, product_name, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getId());
            ps.setString(2, p.getName());
            ps.setInt(3, p.getQuantity());
            ps.setDouble(4, p.getPrice());
            ps.executeUpdate();
            System.out.println("Product added: " + p);
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                products.add(new Product(rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")));
            }
        }
        return products;
    }

    public void updateQuantity(int id, int newQty) throws SQLException {
        String sql = "UPDATE products SET quantity = ? WHERE product_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newQty);
            ps.setInt(2, id);
            if (ps.executeUpdate() > 0) {
                System.out.println("Updated product product_id " + id + " to quantity " + newQty);
            } else {
                System.out.println("No product found with ID " + id);
            }
        }
    }

    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                System.out.println("Deleted product ID " + id);
            } else {
                System.out.println("No product found with ID " + id);
            }
        }
    }
}
