/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Bean;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import info.uaic.vrp.Entities.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ioana
 */
@Singleton
public class OrderRegistry {

    private Map<Client, List<Order>> clientOrders;

    @PostConstruct
    public void init() {
        clientOrders = new HashMap<>();
    }

    public void registerOrder(Client client, Order order) {
        clientOrders.computeIfAbsent(client, k -> new ArrayList<>()).add(order);
    }

    public List<Order> getOrdersForClient(Client client) {
        return clientOrders.getOrDefault(client, new ArrayList<>());
    }

    public Map<Client, List<Order>> getAllOrders() {
        return clientOrders;
    }
}