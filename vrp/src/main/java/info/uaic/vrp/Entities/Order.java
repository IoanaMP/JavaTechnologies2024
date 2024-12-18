/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ioana
 */

@Entity
@Table(name = "orders")
@NamedQueries({
    @NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o"),
    @NamedQuery(name = "Order.findById", query = "SELECT o FROM Order o WHERE o.id = :id"),
    @NamedQuery(name = "Order.findByClientId", query = "SELECT o FROM Order o WHERE o.client.id = :clientId")
})
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_weight", nullable = true)
    private Double totalWeight;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "status_id")
    private Integer statusId;

    @Column(name = "availability_start", nullable = true)
    private LocalDateTime availabilityStart;

    @Column(name = "availability_end", nullable = true)
    private LocalDateTime availabilityEnd;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {}

    public Order(Client client, LocalDateTime orderDate, int statusId, LocalDateTime availabilityStart, LocalDateTime availabilityEnd) {
        this.client = client;
        this.orderDate = orderDate;
        this.statusId = statusId;
        this.availabilityStart = availabilityStart;
        this.availabilityEnd = availabilityEnd;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public BigDecimal getTotalPrice() {
        if (totalPrice == null) {
            totalPrice = orderItems.stream()
                                   .map(OrderItem::getTotalPrice)
                                   .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public int getStatusId() { return statusId; }
    public void setStatusId(int statusId) { this.statusId = statusId; }

    public LocalDateTime getAvailabilityStart() { return availabilityStart; }
    public void setAvailabilityStart(LocalDateTime availabilityStart) { this.availabilityStart = availabilityStart; }

    public LocalDateTime getAvailabilityEnd() { return availabilityEnd; }
    public void setAvailabilityEnd(LocalDateTime availabilityEnd) { this.availabilityEnd = availabilityEnd; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> items) {
        this.orderItems.clear();
        System.out.print(items.size());
        if (items != null) {
            items.forEach(this::addOrderItem);
        }
    }

public void addOrderItem(OrderItem item) {
    if (item == null) {
        throw new IllegalArgumentException("OrderItem cannot be null");
    }
    if (item.getProduct() == null || item.getProduct().getId() == null) {
        throw new IllegalStateException("Product or Product ID cannot be null for OrderItem: " + item);
    }
    
    orderItems.add(item);
    item.setOrder(this);
}

    public void removeOrderItem(OrderItem item) {
        orderItems.remove(item);
        item.setOrder(null);
    }
}