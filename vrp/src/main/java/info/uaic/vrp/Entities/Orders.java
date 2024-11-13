/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Entities;

import java.io.Serializable;


/**
 *
 * @author ioana
 */
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id"),
    @NamedQuery(name = "Orders.findByClientId", query = "SELECT o FROM Orders o WHERE o.client.id = :clientId")
})
public class Orders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private int statusId;
    private LocalDateTime availabilityStart;
    private LocalDateTime availabilityEnd;

    public Orders() {}

    public Orders(Client client, LocalDateTime orderDate, BigDecimal totalPrice, int statusId, LocalDateTime availabilityStart, LocalDateTime availabilityEnd) {
        this.client = client;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
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

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public int getStatusId() { return statusId; }
    public void setStatusId(int statusId) { this.statusId = statusId; }

    public LocalDateTime getAvailabilityStart() { return availabilityStart; }
    public void setAvailabilityStart(LocalDateTime availabilityStart) { this.availabilityStart = availabilityStart; }

    public LocalDateTime getAvailabilityEnd() { return availabilityEnd; }
    public void setAvailabilityEnd(LocalDateTime availabilityEnd) { this.availabilityEnd = availabilityEnd; }
}
