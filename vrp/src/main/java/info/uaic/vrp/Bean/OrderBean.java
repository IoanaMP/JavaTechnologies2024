/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import info.uaic.vrp.Entities.*;
import info.uaic.vrp.Repositories.ClientRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
/**
 *
 * @author ioana
 */
@Named("orderBean")
@ViewScoped
public class OrderBean implements Serializable {

    @Inject
    private StockManager stockManager;

    @Inject
    private OrderManager orderManager;

    @Inject
    private OrderRegistry orderRegistry;
    
    @Inject
    private ClientRepository clientRepository; 

    private List<Product> products = new ArrayList<>();
    private Map<Integer, Integer> quantities = new HashMap<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @PostConstruct
    public void init() {
        products = stockManager.getAvailableProducts();

        for (Product product : products) {
            quantities.put(product.getId(), 0);
        }
    }

public void calculateTotalPrice() {
    totalPrice = BigDecimal.ZERO;

    for (Product product : products) {
        Object quantityObj = quantities.getOrDefault(product.getId(), 0);
        System.out.println("Quantity type: " + (quantityObj != null ? quantityObj.getClass().getName() : "null"));
        Integer quantity = quantityObj instanceof Integer ? (Integer) quantityObj : Integer.valueOf(quantityObj.toString());
        
        if (quantity > 0) {
            totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }
    }

    System.out.println("Updated total price: " + totalPrice);
}

public void placeOrder() {
    try {
        List<Product> orderedProducts = new ArrayList<>();

        for (Product product : products) {
            Object quantityObj = quantities.get(product.getId());
            int quantity;

            try {
                if (quantityObj instanceof Integer) {
                    quantity = (Integer) quantityObj;
                } else if (quantityObj != null) {
                    quantity = Integer.parseInt(quantityObj.toString());
                } else {
                    quantity = 0;
                }
            } catch (NumberFormatException e) {
                quantity = 0;
            }

            if (quantity > 0) {
                orderedProducts.add(product);
                orderManager.addItem(product, quantity);
            }
        }

        if (orderedProducts.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Order Empty", "Select products to place an order."));
            return;
        }

        Client client = getCurrentClient();
        orderManager.setClient(client);

        orderManager.placeOrder();
        Order order = orderManager.getCurrentOrder();
        orderRegistry.registerOrder(client, order);

        for (Product product : products) {
            quantities.put(product.getId(), 0);
        }
        totalPrice = BigDecimal.ZERO;

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage("Order placed successfully!"));
    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        e.printStackTrace();
    }
}

    private Client getCurrentClient() {
        try {
            int randomId = getRandomId(5, 7);
            Client client = clientRepository.findById(randomId);

            if (client == null) {
                throw new RuntimeException("No client found with ID: " + randomId);
            }
            System.out.print(client.getName());
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch random client", e);
        }
    }

    private int getRandomId(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Map<Integer, Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(Map<Integer, Integer> quantities) {
        this.quantities = quantities;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}


