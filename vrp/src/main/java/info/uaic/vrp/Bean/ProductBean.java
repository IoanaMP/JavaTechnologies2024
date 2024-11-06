/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Bean;

import info.uaic.vrp.Entities.Product;
import info.uaic.vrp.Services.ProductService;
import info.uaic.vrp.Utils.DatabaseConnection;
import java.io.Serializable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ioana
 */
@Named("productBean")
@SessionScoped
public class ProductBean implements Serializable{
    private Product product = new Product();
    private List<Product> products;
    @Inject
    private ProductService productService;
    
    
    @PostConstruct
    public void init(){

        try {
            this.products = productService.getAll();
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
            productService.save(product);
            products = (List<Product>) productService.getAll(); 
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