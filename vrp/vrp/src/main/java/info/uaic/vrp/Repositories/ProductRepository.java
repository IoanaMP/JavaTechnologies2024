/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Repositories;

import javax.persistence.EntityManager;
import java.util.List;
import info.uaic.vrp.Entities.Product;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
/**
 *
 * @author ioana
 */
public class ProductRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Product> findAll() {
        return em.createNamedQuery("Product.findAll", Product.class).getResultList();
    }

    public Product findById(Long id) {
        return em.createNamedQuery("Product.findById", Product.class)
                 .setParameter("id", id)
                 .getSingleResult();
    }

    public Product findByName(String name) {
        return em.createNamedQuery("Product.findByName", Product.class)
                 .setParameter("name", name)
                 .getSingleResult();
    }

    public void create(Product product) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(product);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback(); 
            }
            throw new RuntimeException("Error while creating product", e);
        }
    }

    public void edit(Product product) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(product);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while updating product", e);
        }
    }

    public void remove(Product product) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(em.contains(product) ? product : em.merge(product));
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while removing product", e);
        }
    }
}
