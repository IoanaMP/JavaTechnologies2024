/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import info.uaic.vrp.Entities.Product;
/**
 *
 * @author ioana
 */
@Stateless
public class StockManager {

    @PersistenceContext
    private EntityManager em;

    public int getCurrentStock(int productId) {
        Product product = em.find(Product.class, productId);
        if (product != null) {
            return product.getStock();
        }
        return 0;
    }

    public boolean reduceStock(int productId, int quantity) {
        Product product = em.find(Product.class, productId);
        if (product != null && product.getStock() >= quantity) {
            product.setStock(product.getStock() - quantity);
            return true;
        }
        return false;
    }
}