/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Repositories;
import javax.persistence.*;
import java.util.List;
import info.uaic.vrp.Entities.*;
/**
 *
 * @author ioana
 */
public class OrdersRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Orders> findAll() {
        return em.createNamedQuery("Orders.findAll", Orders.class).getResultList();
    }

    public Orders findById(int id) {
        return em.createNamedQuery("Orders.findById", Orders.class)
                 .setParameter("id", id)
                 .getSingleResult();
    }

    public List<Orders> findByClientId(int clientId) {
        return em.createNamedQuery("Orders.findByClientId", Orders.class)
                 .setParameter("clientId", clientId)
                 .getResultList();
    }

    public void create(Orders order) {
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

    public void update(Orders order) {
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

    public void delete(Orders order) {
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
