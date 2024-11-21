/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Repositories;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ioana
 */
public abstract class DataRepository<T, ID extends Serializable>
        implements Serializable {
    protected Class<T> entityClass;
    
    @PersistenceContext
    private EntityManager em;
    protected DataRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    @PostConstruct
    protected void init() {
    }
    //why this instead of the constructor?
    public T newInstance() {
        try {
            return entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            //...should throw a custom runtime exception
            return null;
        }
    }
    public void persist(T entity) {
        em.persist(entity);
    }
    public void update(T entity) {
        em.merge(entity);
    }
    public void remove(T entity) {
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }
        em.remove(entity);
    }
    public T refresh(T entity) {
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }
        em.refresh(entity);
        return entity;
    }
    public T findById(ID id) {
        if (id == null) {
            return null;
        }
        return em.find(entityClass, id);
    }
    public void clearCache() {
        em.getEntityManagerFactory().getCache().evictAll();
    }

}