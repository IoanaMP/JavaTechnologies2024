/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.dao;

import info.uaic.review.entities.EvaluationEntity;
import info.uaic.review.entities.UserEntity;
import info.uaic.review.interfaces.SubmissionInterface;
import info.uaic.review.repositories.EvaluationRepository;
import info.uaic.review.repositories.SubmissionPrimary;
import info.uaic.review.repositories.UserRepository;
import info.uaic.review.utils.EvaluationEvent;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
/**
 *
 * @author ioana
 */
@Named("evaluationBean")
@RequestScoped
public class EvaluationBean {

    @Inject
    @SubmissionPrimary
    private SubmissionInterface submissionService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private Event<EvaluationEvent> evaluationEvent;

    @Valid
    private EvaluationEntity evaluation = new EvaluationEntity();
    @Inject
    private EntityManager em;

    private List<UserEntity> teachers;
    private UserEntity selectedTeacher;

    @PostConstruct
    public void init() {
        teachers = userRepository.findAllTeachers();
    }
    
    @Transactional
    public void submit() {
        System.out.print("submit");
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            System.out.print(evaluation.getGrade());
            String username  = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
            UserEntity loggedInUser = userRepository.findByUsername(username);
            evaluation.setStudent(loggedInUser);
            UserEntity managedTeacher = em.merge(evaluation.getTeacher());
            UserEntity managedStudent = em.merge(evaluation.getStudent());
            System.out.print(managedTeacher);
            System.out.print(managedTeacher);
            evaluation.setTeacher(managedTeacher);
            evaluation.setStudent(managedStudent);
            submissionService.save(evaluation);
            evaluationEvent.fire(new EvaluationEvent(evaluation));
            facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Success",
                    "Evaluation submitted successfully!"));

            resetForm();
        } catch (ConstraintViolationException e) {
            handleValidationErrors(e);
        } catch (Exception e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "An error occurred while submitting the evaluation."));
            e.printStackTrace();
        }
    }

    private void handleValidationErrors(ConstraintViolationException e) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        e.getConstraintViolations().forEach(violation -> {
            facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Validation Error",
                    violation.getMessage()));
        });
    }

    private void resetForm() {
        this.evaluation = new EvaluationEntity();
        //this.evaluation.setGrade(1); 
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

    public UserEntity getSelectedTeacher() { return selectedTeacher; }
    public void setSelectedTeacher(UserEntity selectedTeacher) { this.selectedTeacher = selectedTeacher; }
}