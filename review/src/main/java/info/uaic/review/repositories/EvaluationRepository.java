/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.repositories;

import info.uaic.review.entities.*;
import java.time.LocalDateTime;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 *
 * @author ioana
 */
@Named
@ApplicationScoped
public class EvaluationRepository {

    @Inject
    private EntityManager em;

    @Transactional
    public void saveEvaluation(@Valid EvaluationEntity evaluation) {
        em.persist(evaluation);
    }
    
    @Transactional
    public List<EvaluationEntity> findEvaluationsByTeacherUsername(String username) {
        return em.createQuery(
            "SELECT e FROM EvaluationEntity e WHERE e.teacher.username = :username",
            EvaluationEntity.class)
            .setParameter("username", username)
            .getResultList();
    }

    @Transactional
    public List<EvaluationEntity> findAllEvaluations() {
        return em.createQuery("SELECT e FROM EvaluationEntity e", EvaluationEntity.class).getResultList();
    }

    @Transactional
    public List<EvaluationEntity> findEvaluationsByTeacher(String teacherUsername) {
        return em.createQuery(
            "SELECT e FROM EvaluationEntity e WHERE e.teacher.username = :username",
            EvaluationEntity.class)
            .setParameter("username", teacherUsername)
            .getResultList();
    }

    @Transactional
    public List<EvaluationEntity> findEvaluationsByStudent(String studentUsername) {
        return em.createQuery(
            "SELECT e FROM EvaluationEntity e WHERE e.student.username = :username",
                EvaluationEntity.class)
            .setParameter("username", studentUsername)
            .getResultList();
    }

    @Transactional
    public EvaluationPeriod getCurrentEvaluationPeriod() {
        try {
            return em.createQuery(
                "SELECT e FROM EvaluationPeriod e WHERE :now BETWEEN e.startDate AND e.endDate",
                EvaluationPeriod.class)
                .setParameter("now", LocalDateTime.now())
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Transactional
    public void saveEvaluationPeriod(EvaluationPeriod period) {
        em.persist(period);
    }


}