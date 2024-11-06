/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Services;

import java.sql.Connection;
import java.sql.SQLException;
import info.uaic.vrp.Entities.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;

import javax.sql.DataSource;
/**
 *
 * @author ioana
 */
@Stateless
public class ProductService {
    @Resource(name = "myConn")
    private DataSource dataSource;

    public void save(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, description, weight, quantity) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getWeight());
            stmt.setInt(4, product.getQuantity());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                product.setId(keys.getInt(1));
            }
        }
    }

    public List<Product> getAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection connection = dataSource.getConnection();Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setWeight(rs.getDouble("weight"));
                product.setQuantity(rs.getInt("quantity"));
                products.add(product);
            }
        }
        return products;
    }
    
        public Product findProductById(Integer id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection connection = dataSource.getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    return product;
                }
            }
        }
        return null;
    }
}
