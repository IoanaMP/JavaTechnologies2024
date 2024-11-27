/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.dao;

import info.uaic.review.entities.EvaluationEntity;
import info.uaic.review.repositories.EvaluationRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ioana
 */
@Named
@RequestScoped
public class AdminDashboardBean {

    @Inject
    private EvaluationRepository evaluationRepository;

    private List<EvaluationEntity> evaluations;

    @PostConstruct
    public void init() {
        evaluations = evaluationRepository.findAllEvaluations();
    }

    public List<EvaluationEntity> getEvaluations() {
        return evaluations;
    }
}
