/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.repositories;

import info.uaic.review.entities.UserEnntity;
import info.uaic.review.interfaces.SubmissionInterface;
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

    @Override
    public void save(UserEnntity user) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 8 && hour <= 18) {
            System.out.println("Available submission time");
        }
    }
}