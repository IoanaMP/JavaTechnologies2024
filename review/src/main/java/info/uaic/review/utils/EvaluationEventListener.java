/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.utils;

import info.uaic.review.entities.EvaluationEntity;
import javax.enterprise.event.Observes;

/**
 *
 * @author ioana
 */
public class EvaluationEventListener {

    public void onEvaluationSubmitted(@Observes EvaluationEvent event) {
        EvaluationEntity evaluation = event.getEvaluation();

        System.out.println("New evaluation submitted for teacher: " 
                           + evaluation.getTeacher().getName()
                           + ", Registration Number: " 
                           + evaluation.getRegistrationNumber());

        sendNotificationToTeacher(evaluation);
    }

    private void sendNotificationToTeacher(EvaluationEntity evaluation) {
        System.out.println("Notification sent to teacher: " + evaluation.getTeacher().getName());
    }
}
