/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.Services;

import info.uaic.review.Filters.EvaluationCache;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ioana
 */
public class ClientReview {
    private static String URI_VIEW = "http://localhost:8080/review/resources/evaluations";

    public static void main(String[] args)
    {
        Client client = ClientBuilder.newClient()
                .register(EvaluationCache.class);
        String temp = client.target(URI_VIEW).request(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println("Client :\n" + temp);
    }
}
