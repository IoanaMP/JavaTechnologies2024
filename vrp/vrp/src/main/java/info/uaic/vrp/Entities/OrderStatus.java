/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Entities;

/**
 *
 * @author ioana
 */
public class OrderStatus {
    private int id;
    private String statusName;

    // Constructor
    public OrderStatus(int id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStatusName() { return statusName; }
    public void setStatusName(String statusName) { this.statusName = statusName; }
}
