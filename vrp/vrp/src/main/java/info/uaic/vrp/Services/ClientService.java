/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Services;
import info.uaic.vrp.Entities.Client;
import info.uaic.vrp.Entities.ClientOrder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ioana
 */
public class ClientService {
    private final Connection connection;

    public ClientService(Connection connection) {
        this.connection = connection;
    }

   public List<Client> getAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Client client = new Client();
                client.setClientId(rs.getInt("id"));
                client.setName(rs.getString("name"));
                client.setAddress(rs.getString("address"));
                client.setAvailableDay(rs.getString("available_day"));
                client.setTimeStart(rs.getString("time_start"));
                client.setTimeEnd(rs.getString("time_end"));
                clients.add(client);
            }
        }
        return clients;
    }

        public List<ClientOrder> getClientOrders(int clientId) {
        List<ClientOrder> clientOrders = new ArrayList<>();
        String query = "SELECT * FROM clients_order WHERE client_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ClientOrder order = new ClientOrder();
                    clientOrders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientOrders;
    }


    public void addClientOrder(ClientOrder order) {
        String query = "INSERT INTO clients_order (client_id, product_id, order_date, quantity) VALUES (?, ?, CURRENT_DATE, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, order.getClientId());
            stmt.setInt(2, order.getProductId());
            stmt.setInt(3, order.getQuantity());
            stmt.executeUpdate();
            System.out.println("Order added for client ID: " + order.getClientId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
