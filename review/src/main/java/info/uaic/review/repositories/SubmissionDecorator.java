/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.repositories;

import info.uaic.review.entities.EvaluationEntity;
import info.uaic.review.entities.EvaluationPeriod;
import info.uaic.review.interfaces.SubmissionInterface;
import java.time.LocalDateTime;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import javax.validation.Valid;
/**
 *
 * @author ioana
 */

@Decorator
public abstract class SubmissionDecorator implements SubmissionInterface {

    @Inject
    @Delegate
    @SubmissionPrimary
    private SubmissionInterface delegate;

    @Inject
    @SubmissionPrimary
    private EvaluationRepository evaluationRepository;

    @Override
    public void save(EvaluationEntity evaluation) {
        if (!isWithinSubmissionPeriod()) {
            throw new IllegalStateException("Submissions are not allowed outside the active period.");
        }
        delegate.save(evaluation); 
    }

    private boolean isWithinSubmissionPeriod() {
        EvaluationPeriod period = evaluationRepository.getCurrentEvaluationPeriod();
        LocalDateTime now = LocalDateTime.now();
        return period != null && now.isAfter(period.getStartDate()) && now.isBefore(period.getEndDate());
    }
}