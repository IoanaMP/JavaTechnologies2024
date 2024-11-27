/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.repositories;

import info.uaic.review.entities.EvaluationEntity;
import info.uaic.review.entities.EvaluationPeriod;
import info.uaic.review.entities.UserEntity;
import info.uaic.review.interfaces.SubmissionInterface;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
/**
 *
 * @author ioana
 */

@Decorator
public abstract class SubmissionDecorator implements SubmissionInterface {

    @Inject
    @Delegate
    @Any
    SubmissionInterface submissionInterface;

    @Inject
    private EvaluationRepository evaluationRepository;

    @Override
    public void save(EvaluationEntity evaluation) {
        if (!isWithinSubmissionPeriod()) {
            throw new IllegalStateException("Submission period is not active");
        }
        submissionInterface.save(evaluation);
    }

    private boolean isWithinSubmissionPeriod() {
        EvaluationPeriod period = evaluationRepository.getCurrentEvaluationPeriod();
        LocalDateTime now = LocalDateTime.now();
        return period != null && now.isAfter(period.getStartDate()) && now.isBefore(period.getEndDate());
    }
}