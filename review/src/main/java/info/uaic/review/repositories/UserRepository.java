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
    public void save(UserEntity user) {
        em.persist(user);
    }
    
    @Transactional
    public UserEntity findTeacherById(Integer teacherId) {
        try {
            return em.createQuery(
                    "SELECT u FROM UserEntity u WHERE u.id = :teacherId AND 'teacher' MEMBER OF u.roles",
                    UserEntity.class)
                    .setParameter("teacherId", teacherId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Transactional
    public List<UserEntity> findAllTeachers() {
        return em.createQuery(
                "SELECT u FROM UserEntity u WHERE 'teacher' MEMBER OF u.roles",
                UserEntity.class)
                .getResultList();
    }
}