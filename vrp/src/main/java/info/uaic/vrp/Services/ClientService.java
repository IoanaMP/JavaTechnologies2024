/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Services;
import info.uaic.vrp.Entities.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.sql.DataSource;

/**
 *
 * @author ioana
 */

@Stateless
public class ClientService{
    @Resource(name = "myConn")
    private DataSource dataSource;

    public List<ClientOrderDetails> getAllClientOrders() {
        List<ClientOrderDetails> orderDetails = new ArrayList<>();

    String sql = "SELECT " +
                 "    c.name AS client_name, " +
                 "    c.Id AS client_Id, " +
                 "    c.address AS client_address, " +
                 "    c.email AS client_email, " +
                 "    o.id AS order_Id, " +
                 "    o.order_date AS order_date, " +
                 "    os.name AS order_status, " +
                 "    o.total_price AS total_price, " +
                 "    o.availability_start AS availability_start, " +
                 "    o.availability_end AS availability_end, " +
                 "    p.name AS product_name, " +
                 "    oi.quantity AS product_quantity, " +
                 "    p.Id as product_id, " +
                "    c.x_coordinate as x_coordinate, " +
                "    c.y_coordinate as y_coordinate, " +
                 "    p.price as product_price " +
                 "FROM " +
                 "    clients c " +
                 "JOIN " +
                 "    orders o ON c.id = o.client_id " +
                 "JOIN " +
                 "    order_items oi ON o.id = oi.order_id " +
                 "JOIN " +
                 "    products p ON oi.product_id = p.id " +
                 "JOIN " +
                 "    orders_status os ON o.status_id = os.id " +
                 "ORDER BY " +
                 "    o.order_date;";


        try (Connection connection = dataSource.getConnection();PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Integer orderId = rs.getInt("order_Id");
                Integer clientId = rs.getInt("client_Id");
                String clientName = rs.getString("client_name");
                String clientEmail = rs.getString("client_email");
                String clientAddress = rs.getString("client_address");
                LocalDateTime orderDate = rs.getTimestamp("order_date").toLocalDateTime();
                String orderStatus = rs.getString("order_status");
                double totalPrice = rs.getDouble("total_price");
                LocalDateTime availabilityStart = rs.getTimestamp("availability_start") != null ? 
                        rs.getTimestamp("availability_start").toLocalDateTime() : null;
                LocalDateTime availabilityEnd = rs.getTimestamp("availability_end") != null ? 
                        rs.getTimestamp("availability_end").toLocalDateTime() : null;
                int x_coordinate = rs.getInt("x_coordinate");
                int y_coordinate = rs.getInt("y_coordinate");
                
                BigDecimal productPrice = rs.getBigDecimal("product_price");
                System.out.println("Product Price from DB: " + productPrice); 
                Product orderItem = new Product(rs.getInt("product_id"), rs.getString("product_name"),rs.getBigDecimal("product_price"), rs.getInt("product_quantity"));
                
                ClientOrderDetails orderDetail = orderDetails.stream()
                    .filter(od -> od.getOrderDate().equals(orderDate) && od.getClientName().equals(clientName))
                    .findFirst()
                    .orElse(null);

                if (orderDetail == null) {
                    List<Product> orderItems = new ArrayList<>();
                    orderItems.add(orderItem);
                    orderDetail = new ClientOrderDetails(orderId,clientId, clientName, clientEmail, clientAddress, orderDate,availabilityStart,availabilityEnd, orderItems, orderStatus, totalPrice,x_coordinate,y_coordinate);
                    orderDetails.add(orderDetail);
                } else {
                    orderDetail.getOrderItems().add(orderItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orderDetails;
    }
    
    public int saveClient(ClientOrderDetails client) throws SQLException {
        String sql = "INSERT INTO clients (name, email, address) VALUES (?, ?, ?,?,?)";
        try (Connection connection = dataSource.getConnection();PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, client.getClientName());
            stmt.setString(2, client.getClientEmail());
            stmt.setString(3, client.getClientAddress());
            stmt.setInt(4, getRandomNumberUsingInts(-20,20));
            stmt.setInt(5, getRandomNumberUsingInts(-15,15));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating client failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating client failed, no ID obtained.");
                }
            }
        }
    }
    
    public int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
          .findFirst()
          .getAsInt();
    }

    public void updateClient(ClientOrderDetails client) throws SQLException {
        String sql = "UPDATE clients SET name = ?, email = ?, address = ? WHERE Id = ?";
        try (Connection connection = dataSource.getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, client.getClientName());
            stmt.setString(2, client.getClientEmail());
            stmt.setString(3, client.getClientAddress());
            stmt.setInt(4, client.getClientId());
            int affectedRows = stmt.executeUpdate();
        
            if (affectedRows == 0) {
                throw new SQLException("Updating client failed, no rows affected.");
            }
        }
    }
}
