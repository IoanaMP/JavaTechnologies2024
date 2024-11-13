/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Entities;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
/**
 *
 * @author ioana
 */
@Embeddable
public class OrderItemsId implements Serializable {

    private int orderId;
    private int productId;

    public OrderItemsId() {}

    public OrderItemsId(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemsId)) return false;
        OrderItemsId that = (OrderItemsId) o;
        return orderId == that.orderId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}