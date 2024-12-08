/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.repositories;

import info.uaic.review.entities.RoleEntity;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author ioana
 */
public class RoleRepository {

    @Inject
    private EntityManager em;

    public RoleEntity findByName(String name) {
        try {
            return em.createQuery("SELECT r FROM RoleEntity r WHERE r.name = :name", RoleEntity.class)
                     .setParameter("name", name)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
