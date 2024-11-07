/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author ioana
 */

public class ClientOrderDetails {
    private Integer clientId;
    private Integer orderId;
    private String clientName;
    private String clientEmail;
    private String clientAddress;
    private LocalDateTime orderDate;
    private LocalDateTime availabilityStart;
    private LocalDateTime availabilityEnd; 
    private List<Product> orderItems;
    private String orderStatus;
    private double totalPrice;
    private int x_coordinate;
    private int y_coordinate;

    public ClientOrderDetails(Integer orderId,Integer clientId, String clientName, String clientEmail, String clientAddress,
                              LocalDateTime orderDate, LocalDateTime availabilityStart, LocalDateTime availabilityEnd,
                              List<Product> orderItems, String orderStatus, double totalPrice, int x_coordinate, int y_coordinate) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientAddress = clientAddress;
        this.orderDate = orderDate;
        this.availabilityStart = availabilityStart;
        this.availabilityEnd = availabilityEnd;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
    }

    public ClientOrderDetails() {
    }
    // Getters and Setters
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    
    public Integer getClientId() { return clientId; }
    public void setClientId(Integer clientId) { this.clientId = clientId; }
    
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<Product> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Product> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
        public LocalDateTime getAvailabilityStart() {
        return availabilityStart;
    }

    public void setAvailabilityStart(LocalDateTime availabilityStart) {
        this.availabilityStart = availabilityStart;
    }

    public LocalDateTime getAvailabilityEnd() {
        return availabilityEnd;
    }

    public void setAvailabilityEnd(LocalDateTime availabilityEnd) {
        this.availabilityEnd = availabilityEnd;
    }
    
    public int getXCoord() { return x_coordinate; }
    public void setxCoord(int x_coordinate) { this.x_coordinate = x_coordinate; }
    
    public int getYCoord() { return y_coordinate; }
    public void setYCoord(int y_coordinate) { this.y_coordinate = y_coordinate; }
}
