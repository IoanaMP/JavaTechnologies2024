/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Repositories;
import javax.persistence.*;
import java.util.List;
import info.uaic.vrp.Entities.*;
import javax.ejb.Stateless;
/**
 *
 * @author ioana
 */
@Stateless
public class OrdersRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Order> findAll() {
        return em.createNamedQuery("Orders.findAll", Order.class).getResultList();
    }

    public Order findById(int id) {
        return em.createNamedQuery("Orders.findById", Order.class)
                 .setParameter("id", id)
                 .getSingleResult();
    }

    public List<Order> findByClientId(int clientId) {
        return em.createNamedQuery("Orders.findByClientId", Order.class)
                 .setParameter("clientId", clientId)
                 .getResultList();
    }

    public void create(Order order) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(order);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while creating order", e);
        }
    }

    public void update(Order order) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(order);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while updating order", e);
        }
    }

    public void delete(Order order) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(em.contains(order) ? order : em.merge(order));
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while deleting order", e);
        }
    }
}
