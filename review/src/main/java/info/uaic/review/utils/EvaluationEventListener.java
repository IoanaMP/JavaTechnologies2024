/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.utils;

import javax.enterprise.event.Observes;

/**
 *
 * @author ioana
 */
public class EvaluationEventListener {

    public void onEvaluationSubmitted(@Observes EvaluationEvent event) {
        System.out.println("New evaluation submitted for teacher: " 
                           + event.getEvaluation().getTeacher().getName()
                           + ", Registration Number: " 
                           + event.getEvaluation().getRegistrationNumber());
    }
}
