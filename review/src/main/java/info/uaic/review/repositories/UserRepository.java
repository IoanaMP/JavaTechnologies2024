/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.repositories;
import info.uaic.review.entities.UserEntity;
import info.uaic.review.logging.LoggingInterceptor;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
/**
 *
 * @author ioana
 */
@Transactional(rollbackOn = {SQLException.class})
@Named
public class UserRepository {

    @Inject
    private EntityManager em;

    @Interceptors(LoggingInterceptor.class)
    @Transactional
    public void save(UserEntity user) {
        em.persist(user);
    }
    
    public UserEntity findTeacherById(Integer teacherId) {
        try {
            return em.createQuery(
                    "SELECT u FROM UserEntity u JOIN u.roles r WHERE u.id = :teacherId AND r.name = :roleName",
                    UserEntity.class)
                    .setParameter("teacherId", teacherId)
                    .setParameter("roleName", "teacher")
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<UserEntity> findAllTeachers() {
        return em.createQuery(
                "SELECT u FROM UserEntity u JOIN u.roles r WHERE r.name = :roleName",
                UserEntity.class)
                .setParameter("roleName", "teacher")
                .getResultList();
    }
    
    public UserEntity findById(Integer id) {
        try {
            return em.createNamedQuery("UserEntity.findById", UserEntity.class)
                     .setParameter("id", id)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
        public UserEntity findByUsername(String username) {
        try {
            return em.createNamedQuery("UserEntity.findByUsername", UserEntity.class)
                     .setParameter("username", username)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}