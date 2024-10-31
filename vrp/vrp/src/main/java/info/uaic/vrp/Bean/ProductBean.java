/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Bean;

import info.uaic.vrp.Entities.Product;
import info.uaic.vrp.Services.ProductService;
import info.uaic.vrp.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ioana
 */
@ManagedBean(name="productBean")
@SessionScoped
public class ProductBean{
    private Product product = new Product();
    private List<Product> products;
    private ProductService productServie;

    public ProductBean() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                System.out.println("Database connection established successfully.");
            } else {
                System.out.println("Failed to establish database connection.");
            }
            this.productServie = new ProductService(connection);
            this.products = productServie.getAll();
            System.out.println("Loading products...");
            for (Product product : products) {
                System.out.println("Product ID: " + product.getId() + ", Name: " + product.getName());
            }
            System.out.print(this.products);
        } catch (SQLException e) {
        }
    }

    public String saveProduct(){
        try {
            productServie.save(product);
            products = (List<Product>) productServie.getAll(); 
            product = new Product();
            return "products?faces-redirect=true";
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}