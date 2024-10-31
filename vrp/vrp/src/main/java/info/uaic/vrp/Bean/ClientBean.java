/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Bean;


import java.util.List;
import info.uaic.vrp.Entities.*;
import info.uaic.vrp.Services.ClientService;
import info.uaic.vrp.Services.ProductService;
import info.uaic.vrp.Utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
/**
 *
 * @author ioana
 */
@ManagedBean(name = "clientBean")
@SessionScoped
public class ClientBean {
private List<Client> clients;
    private Client newClient = new Client();
    private ClientService clientService;

    public ClientBean() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            clientService = new ClientService(connection);
            clients = clientService.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> getClients() {
        return clients;
    }

//    public void addClient() {
//        clientService.addClient(newClient);
//        clients = clientService.getAll(); 
//        newClient = new Client();
//    }

    public Client getNewClient() {
        return newClient;
    }

    public void setNewClient(Client newClient) {
        this.newClient = newClient;
    }
}