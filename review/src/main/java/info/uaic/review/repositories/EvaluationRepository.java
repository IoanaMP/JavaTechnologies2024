/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.repositories;

import info.uaic.review.entities.*;
import info.uaic.review.interfaces.SubmissionInterface;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import jdk.nashorn.internal.runtime.logging.Logger;

/**
 *
 * @author ioana
 */

@Transactional(rollbackOn = {SQLException.class})
@ApplicationScoped
public class EvaluationRepository implements SubmissionInterface {

    @Inject
    private EntityManager em;
    
    @Override
    public void save(EvaluationEntity evaluation) {
        try {
            System.out.print("save ev");
            System.out.print(evaluation.getRegistrationNumber());
            System.out.print(evaluation.getTeacher());
            System.out.print(evaluation.getActivityName());

            em.persist(evaluation);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<EvaluationEntity> findEvaluationsByTeacherUsername(String username) {
        return em.createQuery(
            "SELECT e FROM EvaluationEntity e WHERE e.teacher.username = :username",
            EvaluationEntity.class)
            .setParameter("username", username)
            .getResultList();
    }

    public List<EvaluationEntity> findAllEvaluations() {
        return em.createQuery("SELECT e FROM EvaluationEntity e", EvaluationEntity.class).getResultList();
    }

    public List<EvaluationEntity> findEvaluationsByTeacher(String teacherUsername) {
        return em.createQuery(
            "SELECT e FROM EvaluationEntity e WHERE e.teacher.username = :username",
            EvaluationEntity.class)
            .setParameter("username", teacherUsername)
            .getResultList();
    }

    public List<EvaluationEntity> findEvaluationsByStudent(String studentUsername) {
        return em.createQuery(
            "SELECT e FROM EvaluationEntity e WHERE e.student.username = :username",
                EvaluationEntity.class)
            .setParameter("username", studentUsername)
            .getResultList();
    }

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
    
    public void saveEvaluationPeriod(EvaluationPeriod period) {
        em.persist(period);
    }


}