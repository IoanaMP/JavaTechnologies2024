/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.dao;

import info.uaic.review.entities.EvaluationEntity;
import info.uaic.review.repositories.EvaluationRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    private long totalEvaluations;
    private Map<String, Long> evaluationsByTeacher;
    private Map<String, Double> averageGradeByTeacher;

    @PostConstruct
    public void init() {
        evaluations = evaluationRepository.findAllEvaluations();
        calculateStatistics();
    }

    private void calculateStatistics() {
        totalEvaluations = evaluations.size();

        evaluationsByTeacher = evaluations.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getTeacher().getName(),
                        Collectors.counting()
                ));

        averageGradeByTeacher = evaluations.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getTeacher().getName(),
                        Collectors.averagingInt(EvaluationEntity::getGrade)
                ));
    }

    public List<EvaluationEntity> getEvaluations() {
        return evaluations;
    }

    public long getTotalEvaluations() {
        return totalEvaluations;
    }

    public Map<String, Long> getEvaluationsByTeacher() {
        return evaluationsByTeacher;
    }

    public Map<String, Double> getAverageGradeByTeacher() {
        return averageGradeByTeacher;
    }
}