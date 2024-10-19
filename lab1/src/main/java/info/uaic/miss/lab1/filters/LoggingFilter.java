/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.miss.lab1.filters;

import info.uaic.miss.lab1.Helper.RequestLog;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author ioana
 */
@WebFilter(filterName = "LoggingFilter", urlPatterns = {"/input.jsp/*"})
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String exampleParam = request.getParameter("exampleParam");

        // Log the request using the RequestLog class from lab 1
        RequestLog.logRequest(request, request.getServletContext(), exampleParam);

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}