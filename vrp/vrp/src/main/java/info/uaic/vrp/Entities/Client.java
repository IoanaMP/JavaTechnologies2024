/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Entities;

import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author ioana
 */
public class Client {
    private Integer id;
    private String name;
    private String address;
    private String timeStart;
    private String timeEnd;
    private String availableDay;
    private double xCoordinate;
    private double yCoordinate;
    // Getters and Setters
    
    public Integer getClientId() {
        return id;
    }

    public void setClientId(Integer clientId) {
        this.id = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getAvailableDay() {
        return availableDay;
    }

    public void setAvailableDay(String availabilityDays) {
        this.availableDay = availabilityDays;
    }
    
        public double getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

}
