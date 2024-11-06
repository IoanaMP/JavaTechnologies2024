/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Entities;
import java.util.Objects;

/**
 *
 * @author ioana
 */
public class Product {
    
    private Integer id;
    private String name;
    private String description;
    private Double weight;
    private Integer quantity;
    private double price;
    private Double totalPrice;
    private boolean selected;
    
    public Product(Integer productId, String name, double price, Integer quantity) {
        this.id = productId;
        this.name = name;
//        this.description = description;
//        this.weight = weight;
        this.quantity = quantity;
        this.price = price;
    }

    public Product() {
    }

    // Getters and Setters
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        System.out.println(this);
        System.out.println(obj);
        Product product = (Product) obj;
        
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        System.out.print(price);
        this.price = price;
    }
    
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}