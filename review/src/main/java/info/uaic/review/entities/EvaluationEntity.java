/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Produces;

/**
 *
 * @author ioana
 */
@Entity
@Table(name = "evaluations")
public class EvaluationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "registration_number", unique = true)
    private String registrationNumber;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private UserEntity student;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private UserEntity teacher;

    @NotBlank(message = "Activity name is required.")
    @Size(max = 100, message = "Activity name must not exceed 100 characters.")
    @Column(name = "activity_name")
    private String activityName;

    @NotBlank(message = "Activity type is required.")
    @Column(name = "activity_type")
    private String activityType;

    @NotNull(message = "Grade is required.")
    @Min(value = 1, message = "Grade must be greater than or equal to 10")
    @Max(value = 10, message = "Grade must be less than or equal to 100")
    @Column(name = "grade")
    private Integer grade;

    @Size(max = 500, message = "Comment must not exceed 500 characters.")
    @Column(name = "comment")
    private String comment;

    @Basic(optional = false)
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    public EvaluationEntity() {
    this.grade = 1;
    }

    @Produces
    public static String generateRegistrationNumber() {
        return UUID.randomUUID().toString();
    }

    @PrePersist
    public void prePersist() {
        if (this.registrationNumber == null) {
            this.registrationNumber = generateRegistrationNumber();
        }
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }
    
    public Integer getId() {
    return id;
}

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public UserEntity getStudent() {
        return student;
    }

    public void setStudent(UserEntity student) {
        this.student = student;
    }

    public UserEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(UserEntity teacher) {
        this.teacher = teacher;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}