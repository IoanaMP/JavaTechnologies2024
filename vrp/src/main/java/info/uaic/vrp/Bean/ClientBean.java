package info.uaic.vrp.Bean;

import info.uaic.vrp.Entities.*;
import info.uaic.vrp.Services.ClientService;
import info.uaic.vrp.Services.OrderService;
import info.uaic.vrp.Services.ProductService;
import info.uaic.vrp.Utils.DatabaseConnection;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@Named("clientBean")
@SessionScoped
public class ClientBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<ClientOrderDetails> clients;
    private ClientOrderDetails newClient = new ClientOrderDetails();
    private ClientOrderDetails selectedOrder;
    private List<Product> availableProducts;
    private Product newOrderItem = new Product();
    private boolean orderItems;
    

    @Inject
    private ClientService clientService;
    @Inject
    private OrderService orderService;
    @Inject
    private ProductService productService;

    @PostConstruct
    public void init() {
        try {
            clients = clientService.getAllClientOrders();
            availableProducts = productService.getAll();
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to load client orders."));
        }
    }
    
    public void saveOrder() throws SQLException {
        if (this.selectedOrder.getOrderId() == null) {
            Integer clientId = clientService.saveClient(selectedOrder);
            System.out.print(clientId);
            selectedOrder.setClientId(clientId);
            Integer orderId = orderService.saveOrder(selectedOrder);
            selectedOrder.setOrderId(orderId);
            orderService.saveOrderItems(orderId,selectedOrder.getOrderItems());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client and Order Added"));
        }
        else {
            clientService.updateClient(selectedOrder);
            orderService.updateOrder(selectedOrder);
            orderService.updateOrderItems(selectedOrder.getOrderId(),selectedOrder.getOrderItems());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client and Order Updated"));
        }

        PrimeFaces.current().ajax().update("form:ordersTable");
        PrimeFaces.current().executeScript("PF('editDialog').hide()");
    }
    
    public void openNew() {
        this.selectedOrder = new ClientOrderDetails();
        if (this.selectedOrder.getOrderItems() == null) {
            this.selectedOrder.setOrderItems(new ArrayList<>());
        }
    }
    
    public void calculateTotalPrice() {
        if (selectedOrder != null && selectedOrder.getOrderItems() != null) {
            List<Product> matchedProducts = new ArrayList<>();
            for (Product selectedProduct : selectedOrder.getOrderItems()) {
                for (Product availableProduct : availableProducts) {
                    if (availableProduct.getId().equals(selectedProduct.getId())) {
                        System.out.print(availableProduct);
                        matchedProducts.add(availableProduct);
                        break;
                    }
                }
            }
            selectedOrder.setOrderItems(matchedProducts);

            double totalPrice = 0.0;
            for (Product prod : selectedOrder.getOrderItems()) {
                System.out.println("Selected Product ID: " + prod.getId() + ", Name: " + prod.getName() + ", Price: " + prod.getPrice());
                totalPrice += prod.getPrice();
            }
            selectedOrder.setTotalPrice(totalPrice);
        }
    }
        
    public List<ClientOrderDetails> getClients() {
        return clients;
    }

    public void setClients(List<ClientOrderDetails> clients) {
        this.clients = clients;
    }

    public ClientOrderDetails getNewClient() {
        return newClient;
    }

    public void setNewClient(ClientOrderDetails newClient) {
        this.newClient = newClient;
    }

    public ClientOrderDetails getSelectedOrder() {

        return selectedOrder;
    }

    public void setSelectedOrder(ClientOrderDetails selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public List<Product> getAvailableProducts() {
        return availableProducts;
    }

    public void setAvailableProducts(List<Product> availableProducts) {
        this.availableProducts = availableProducts;
    }

    public Product getNewOrderItem() {
        return newOrderItem;
    }

    public void setNewOrderItem(Product newOrderItem) {
        this.newOrderItem = newOrderItem;
    }
    
}
