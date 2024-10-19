/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.miss.lab1.filters;

import info.uaic.miss.lab1.Wrapper.SimpleResponseWrapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author ioana
 */
@WebFilter("/*") 
public class ResponseWrapperFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {


        SimpleResponseWrapper wrapper = new SimpleResponseWrapper((HttpServletResponse) response);

        chain.doFilter(request, wrapper);

        String prelude = (String) request.getServletContext().getAttribute("prelude");
        String coda = (String) request.getServletContext().getAttribute("coda");

        String originalContent = wrapper.toString();

        String modifiedContent = prelude + originalContent + coda;

        PrintWriter out = response.getWriter();
        out.write(modifiedContent);
        out.close();
    }

    @Override
    public void destroy() {
    }
}
