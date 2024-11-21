/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Repositories;

import javax.persistence.EntityManager;
import java.util.List;
import info.uaic.vrp.Entities.Product;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
/**
 *
 * @author ioana
 */
@Stateless
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
    
    public List<Product> findAvailableProducts() {
        return em.createNamedQuery("Product.findAvailableProducts", Product.class)
                 .getResultList();
    }

    public void create(Product product) {
        try {
            em.persist(product);
        } catch (PersistenceException e) {
            throw new RuntimeException("Error while creating product", e);
        }
    }

    public void edit(Product product) {
        try {
            em.merge(product);
        } catch (PersistenceException e) {
            throw new RuntimeException("Error while updating product", e);
        }
    }

    public void remove(Product product) {
        try {
            em.remove(em.contains(product) ? product : em.merge(product)); 
        } catch (PersistenceException e) {
            throw new RuntimeException("Error while removing product", e);
        }
    }
}
