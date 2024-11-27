/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.utils;

import info.uaic.review.entities.EvaluationEntity;

/**
 *
 * @author ioana
 */
public class EvaluationEvent {
    private final EvaluationEntity evaluation;

    public EvaluationEvent(EvaluationEntity evaluation) {
        this.evaluation = evaluation;
    }

    public EvaluationEntity getEvaluation() {
        return evaluation;
    }
}
