/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import info.uaic.vrp.Entities.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
/**
 *
 * @author ioana
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderManager {
private static final Logger logger = LoggerFactory.getLogger(OrderManager.class);
@PersistenceContext
    private EntityManager em;

    @Inject
    private StockManager stockManager;

    private List<OrderItem> items = new ArrayList<>();
    private Client client;
    private Order currentOrder;
    
        @AroundInvoke
    public Object logExecution(InvocationContext ctx) throws Exception {
        String className = ctx.getTarget().getClass().getName();
        String methodName = ctx.getMethod().getName();
        String target = className + "." + methodName + "()";
        long startTime = System.currentTimeMillis();

        System.out.println("Entering method: " + target);

        try {
            return ctx.proceed(); // Proceed to the business logic
        } catch (Exception e) {
            System.err.println("Exception in method: " + target + " - " + e.getMessage());
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            System.out.println("Exiting method: " + target + " - Execution time: " + executionTime + " ms");
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void addItem(Product product, int quantity) {
        if (stockManager.getCurrentStock(product.getId()) >= quantity) {
            OrderItem item = new OrderItem(product, quantity);
            items.add(item);
        } else {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }
    }

    public void placeOrder() {
        if (client == null) {
            throw new IllegalStateException("Client must be set before placing an order.");
        }

        currentOrder = new Order();
        currentOrder.setClient(client);
        currentOrder.setStatusId(2);
        currentOrder.setOrderDate(LocalDateTime.now());
        
        BigDecimal totalPrice = items.stream()
        .map(OrderItem::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

        currentOrder.setTotalPrice(totalPrice);
        em.persist(currentOrder);
        em.flush(); // Ensure ID is generated
        System.out.println("Order ID after persist and flush: " + currentOrder.getId());

        if (items == null || items.isEmpty()) {
            throw new IllegalStateException("Items list is null or empty");
        }

        for (OrderItem item : items) {
            if (item.getProduct() == null || item.getProduct().getId() == null) {
                throw new IllegalStateException("Product or Product ID is null for OrderItem: " + item);
            }
            item.setId(new OrderItemsId(currentOrder.getId(), item.getProduct().getId()));
            item.setOrder(currentOrder);
            em.persist(item);
        }

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