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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
/**
 *
 * @author ioana
 */

@WebFilter(filterName = "LoggingFilter", urlPatterns = {"/input.jsp/*"})
@MultipartConfig
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        StringBuilder params = new StringBuilder();

        if (request.getContentType() != null && request.getContentType().startsWith("multipart/")) {
            Part namePart = request.getPart("fileName");
            Part descriptionPart = request.getPart("description");

            String name = namePart != null ? getValueFromPart(namePart) : "N/A";
            String description = descriptionPart != null ? getValueFromPart(descriptionPart) : "N/A";

            params.append("fileName = ").append(name).append(" ");
            params.append("description = ").append(description);
        }

        RequestLog.logRequest(request, request.getServletContext(), params.toString());
        chain.doFilter(req, res);
    }
    
        private String getValueFromPart(Part part) throws IOException {
        StringBuilder value = new StringBuilder();
        byte[] buffer = new byte[1024];
        try (InputStream inputStream = part.getInputStream()) {
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                value.append(new String(buffer, 0, bytesRead));
            }
        }
        return value.toString().trim();
    }

    @Override
    public void destroy() {
    }
}