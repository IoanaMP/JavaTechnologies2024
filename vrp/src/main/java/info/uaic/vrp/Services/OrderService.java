/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Services;

import info.uaic.vrp.Entities.*;
import info.uaic.vrp.Utils.Enums.OrderStatusEnum;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

/**
 *
 * @author ioana
 */
@Stateless
public class OrderService {
    @Resource(name = "myConn")
    private DataSource dataSource;
    
    public int saveOrder(ClientOrderDetails order) throws SQLException {
        String sql = "INSERT INTO orders (client_id, order_date, total_price,status_Id,availability_start,availability_end) VALUES (?, ?, ?, ?,?,?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, order.getClientId());
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setDouble(3, order.getTotalPrice());
            stmt.setInt(4, OrderStatusEnum.Pending.getValue());
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(order.getAvailabilityStart())); 
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(order.getAvailabilityEnd())); 
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }
    
    public void saveOrderItems(int orderId, List<Product> orderItems) throws SQLException {
        String sql = "INSERT INTO order_items (order_id, product_id,quantity) VALUES (?, ?,1)";
        try (Connection connection = dataSource.getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {

            for (Product product : orderItems) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, product.getId());
                stmt.addBatch();
            }

            stmt.executeBatch(); 
        }
    }
    
    public void updateOrder(ClientOrderDetails order) throws SQLException {
        String sql = "UPDATE orders SET client_id = ?, order_date = ?, total_price = ?, status_id = ?, availability_start = ?, availability_end = ? WHERE Id = ?";

        try (Connection connection = dataSource.getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, order.getClientId());
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setDouble(3, order.getTotalPrice());
            stmt.setInt(4, OrderStatusEnum.Pending.getValue());
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(order.getAvailabilityStart()));
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(order.getAvailabilityEnd()));
            stmt.setInt(7, order.getOrderId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating order failed, no rows affected.");
            }
        }
    }
    
    public void updateOrderItems(int orderId, List<Product> orderItems) throws SQLException {
        String deleteSql = "DELETE FROM order_items WHERE order_id = ?";
        try (Connection connection = dataSource.getConnection();PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, orderId);
            deleteStmt.executeUpdate();
        }

        String insertSql = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, 1)";
        try (Connection connection = dataSource.getConnection();) {
            PreparedStatement insertStmt = connection.prepareStatement(insertSql);
            for (Product product : orderItems) {
                insertStmt.setInt(1, orderId);
                insertStmt.setInt(2, product.getId());
                insertStmt.addBatch();
            }

            insertStmt.executeBatch();
        }
    }
}
