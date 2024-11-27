/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Produces;

/**
 *
 * @author ioana
 */
@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceContext(unitName = "punit")
    private EntityManagerProducer em;

    @Produces
    public EntityManagerProducer produceEntityManager() {
        return em;
    }
}
