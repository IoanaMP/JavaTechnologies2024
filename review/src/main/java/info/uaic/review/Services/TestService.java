/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.review.Services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author ioana
 */
@Path("/test")
public class TestService {
    @GET
    public String hello() {
        return "Hello, world!";
    }
}
