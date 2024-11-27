/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
/**
 *
 * @author ioana
 */
@Entity
@Table(name = "order_items")
@NamedQueries({
    @NamedQuery(name = "OrderItems.findByOrderId", query = "SELECT o FROM OrderItems o WHERE o.id.orderId = :orderId"),
    @NamedQuery(name = "OrderItems.findByProductId", query = "SELECT o FROM OrderItems o WHERE o.id.productId = :productId")
})
public class OrderItem implements Serializable {

    @EmbeddedId
    private OrderItemsId id = new OrderItemsId();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public OrderItem() {}

    public OrderItem(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.id = new OrderItemsId(order.getId(), product.getId());
    }

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.id = new OrderItemsId();
    }

    public BigDecimal getTotalPrice() {
        BigDecimal price = product.getPrice();
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public OrderItemsId getId() { return id; }
    public void setId(OrderItemsId id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "OrderItem{" +
               "product=" + product.getName() +
               ", quantity=" + quantity +
               ", totalPrice=" + getTotalPrice() +
               '}';
    }
}
