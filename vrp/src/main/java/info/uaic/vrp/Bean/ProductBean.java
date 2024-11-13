/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Bean;

import info.uaic.vrp.Entities.Product;
import info.uaic.vrp.Repositories.ProductRepository;
import java.io.Serializable;

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
public class ProductBean implements Serializable {

    private Product product = new Product();
    private List<Product> products;

    @Inject
    private ProductRepository productRepository;

    @PostConstruct
    public void init() {
        loadProducts();
    }

    public void loadProducts() {
        try {
            this.products = productRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String viewProductsTable() {
        loadProducts();
        return "productsTable";
    }

    public String saveProduct() {
        try {
            productRepository.create(product);
            loadProducts();
            product = new Product();
            return "productsTable?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void deleteProduct(Product productToDelete) {
        try {
            productRepository.remove(productToDelete);
            loadProducts();
        } catch (Exception e) {
            e.printStackTrace();
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