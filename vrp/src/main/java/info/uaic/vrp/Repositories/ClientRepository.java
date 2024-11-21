/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Repositories;
import javax.persistence.*;
import java.util.List;
import info.uaic.vrp.Entities.Client;
import javax.ejb.Stateless;
/**
 *
 * @author ioana
 */
@Stateless
public class ClientRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Client> findAll() {
        return em.createNamedQuery("Client.findAll", Client.class).getResultList();
    }

    public Client findById(Integer id) {
        return em.createNamedQuery("Client.findById", Client.class)
                 .setParameter("id", id)
                 .getSingleResult();
    }

    public void create(Client client) {
        try {
            em.persist(client);
        } catch (PersistenceException e) {
            throw new RuntimeException("Error while creating client", e);
        }
    }

    public void update(Client client) {
        try {
            em.merge(client);
        } catch (PersistenceException e) {
            throw new RuntimeException("Error while updating client", e);
        }
    }

    public void delete(Client client) {
        try {
            em.remove(em.contains(client) ? client : em.merge(client));
        } catch (PersistenceException e) {
            throw new RuntimeException("Error while deleting client", e);
        }
    }

    public Client findByEmail(String email) {
        try {
            return em.createQuery("SELECT c FROM Client c WHERE c.email = :email", Client.class)
                     .setParameter("email", email)
                     .getSingleResult();
        } catch (PersistenceException e) {
            throw new RuntimeException("Client not found with email: " + email, e);
        }
    }
}