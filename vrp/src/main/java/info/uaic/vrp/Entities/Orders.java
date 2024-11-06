/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author ioana
 */
public class Orders {
    private int id;
    private int clientId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private int statusId;
    private LocalDateTime availabilityStart;
    private LocalDateTime availabilityEnd;

    // Constructor
    public Orders(int id, int clientId, LocalDateTime orderDate, BigDecimal totalPrice, int statusId, LocalDateTime availabilityStart, LocalDateTime availabilityEnd) {
        this.id = id;
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.statusId = statusId;
        this.availabilityStart = availabilityStart;
        this.availabilityEnd = availabilityEnd;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

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