/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Bean;

import info.uaic.vrp.Entities.OrderItemDetails;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import info.uaic.vrp.Entities.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ioana
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderManager {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private StockManager stockManager;

    private List<OrderItem> items = new ArrayList<>();
    private Client client;
    private Order currentOrder;

    public void setClient(Client client) {
        this.client = client;
    }

    public void addItem(Product product, int quantity) {
        if (stockManager.getCurrentStock(product.getId()) >= quantity) {
            items.add(new OrderItem(product, quantity));
        } else {
            throw new RuntimeException("Insufficient stock: " + product.getName());
        }
    }

    public void placeOrder() {
        if (client == null) {
            throw new IllegalStateException("Client must be set before placing an order.");
        }

        currentOrder = new Order();
        currentOrder.setClient(client);
        currentOrder.setOrderItems(items);
        em.persist(currentOrder);

        for (OrderItem item : items) {
            stockManager.reduceStock(item.getProduct().getId(), item.getQuantity());
        }

        items.clear();
    }

    public Order getCurrentOrder() {
        if (currentOrder == null) {
            throw new IllegalStateException("No current order");
        }
        return currentOrder;
    }

    public void reset() {
        items.clear();
        client = null;
        currentOrder = null;
    }
}