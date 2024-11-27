/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.dao;

import info.uaic.review.entities.EvaluationEntity;
import info.uaic.review.entities.EvaluationPeriod;
import info.uaic.review.entities.UserEntity;
import info.uaic.review.repositories.EvaluationRepository;
import info.uaic.review.repositories.UserRepository;
import info.uaic.review.utils.EvaluationEvent;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

/**
 *
 * @author ioana
 */
@Named
@RequestScoped
public class EvaluationBean {

    @Inject
    private EvaluationRepository evaluationRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private FacesContext facesContext;
    
    @Inject
    private Event<EvaluationEvent> evaluationEvent;

    @Valid
    private EvaluationEntity evaluation = new EvaluationEntity();

    private List<UserEntity> teachers;

    @PostConstruct
    public void init() {
        teachers = userRepository.findAllTeachers();
    }

    public void submitEvaluation() {
        try {
            if (!isWithinSubmissionPeriod()) {
                facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Submission Closed",
                        "Evaluations are not being accepted at this time."));
                return;
            }

            evaluationRepository.saveEvaluation(evaluation);
            evaluationEvent.fire(new EvaluationEvent(evaluation));
            facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Success",
                    "Evaluation submitted successfully!"));

            resetForm();
        } catch (ConstraintViolationException e) {
            handleValidationErrors(e);
        } catch (Exception e) {
            facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "An error occurred while submitting the evaluation."));
            e.printStackTrace();
        }
    }

    private boolean isWithinSubmissionPeriod() {
        EvaluationPeriod period = evaluationRepository.getCurrentEvaluationPeriod();
        return period != null &&
               LocalDateTime.now().isAfter(period.getStartDate()) &&
               LocalDateTime.now().isBefore(period.getEndDate());
    }

    private void handleValidationErrors(ConstraintViolationException e) {
        e.getConstraintViolations().forEach(violation -> {
            facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Validation Error",
                    violation.getMessage()));
        });
    }

    private void resetForm() {
        this.evaluation = new EvaluationEntity();
    }

    public EvaluationEntity getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(EvaluationEntity evaluation) {
        this.evaluation = evaluation;
    }

    public List<UserEntity> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<UserEntity> teachers) {
        this.teachers = teachers;
    }
}

