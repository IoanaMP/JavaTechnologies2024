/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Bean;

import info.uaic.vrp.Repositories.ProductRepository;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import info.uaic.vrp.Entities.*;
import java.math.BigDecimal;
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

    private List<Product> products;
    private List<Product> selectedProducts;
    private Map<Integer, Integer> quantities = new HashMap<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @PostConstruct
    public void init() {
        products = stockManager.getAvailableProducts();
    }

    public void calculateTotalPrice() {
        totalPrice = BigDecimal.ZERO;

        if (selectedProducts != null) {
            for (Product product : selectedProducts) {
                int quantity = quantities.getOrDefault(product.getId(), 1);

                totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            }
        }
    }

    public void placeOrder() {
        if (selectedProducts == null || selectedProducts.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "No products selected."));
            return;
        }

        try {
            Client client = getCurrentClient();
            orderManager.setClient(client);

            for (Product product : selectedProducts) {
                int quantity = quantities.getOrDefault(product.getId(), 1);

                if (stockManager.getCurrentStock(product.getId()) >= quantity) {
                    orderManager.addItem(product, quantity);
                } else {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName());
                }
            }

            orderManager.placeOrder();

            Order order = orderManager.getCurrentOrder();
            orderRegistry.registerOrder(client, order);

            selectedProducts.clear();
            quantities.clear();
            totalPrice = BigDecimal.ZERO;

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Order placed successfully!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    private Client getCurrentClient() {
        return new Client();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
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