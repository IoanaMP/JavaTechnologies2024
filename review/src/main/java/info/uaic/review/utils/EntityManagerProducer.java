/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ioana
 */
@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceContext(unitName = "punit")
    private EntityManager em;

    @Produces
    public EntityManager produceEntityManager() {
        return em;
    }
}
