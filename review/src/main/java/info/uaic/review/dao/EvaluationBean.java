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
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;

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

    private Integer teacherId;

    private String activityName;

    private String activityType;

    private Integer grade;

    private String comment;

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

            EvaluationEntity evaluation = new EvaluationEntity();
            evaluation.setTeacher(userRepository.findTeacherById(teacherId));
            evaluation.setActivityName(activityName);
            evaluation.setActivityType(activityType);
            evaluation.setGrade(grade);
            evaluation.setComment(comment);

            evaluationRepository.saveEvaluation(evaluation); 

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
        this.teacherId = null;
        this.activityName = null;
        this.activityType = null;
        this.grade = null;
        this.comment = null;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<UserEntity> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<UserEntity> teachers) {
        this.teachers = teachers;
    }
}
