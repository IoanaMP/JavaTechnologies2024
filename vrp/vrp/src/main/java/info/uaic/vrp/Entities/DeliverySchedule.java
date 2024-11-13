/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Entities;

import java.time.LocalDateTime;

/**
 *
 * @author ioana
 */
public class DeliverySchedule {
    private String clientName;
     private Integer clientId;
    private LocalDateTime startDateTime;
    private LocalDateTime arrivalDateTime; 
        private double distance;
        private int x, y;

    public DeliverySchedule(String clientName, Integer clientId, LocalDateTime startDateTime, LocalDateTime arrivalDateTime, double distance, int x_coordinate, int y_coordinate) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.startDateTime = startDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.distance = distance;
        this.x = x_coordinate;
        this.y = y_coordinate;
    }

        public String getClientName() { return clientName; }
        public Integer getClientId() { return clientId; }
        public LocalDateTime getArrivalDateTime() { return arrivalDateTime; }
        public LocalDateTime getStartDateTime() { return startDateTime; }
        public double getDistance() { return distance; }
        public int getX() { return x; }
        public int getY() { return y; }
}
