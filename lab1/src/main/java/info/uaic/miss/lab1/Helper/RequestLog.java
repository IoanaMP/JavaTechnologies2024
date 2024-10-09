/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.miss.lab1.Helper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author ioana
 */
public class RequestLog {
        public static void logRequest(HttpServletRequest request, ServletContext context, String param) {
        String logMessage = "HTTP Method: " + request.getMethod() + "\n" +
                            "Client IP: " + request.getRemoteAddr() + "\n" +
                            "User-Agent: " + request.getHeader("User-Agent") + "\n" +
                            "Client-Language: " + request.getHeader("Accept-Language") + "\n" +
                            "Request Parameter: " + param;

        context.log(logMessage);
    }
}
