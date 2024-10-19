/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.miss.lab1.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 *
 * @author ioana
 */
@WebListener
public class AppConfigListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Read prelude and coda from context init parameters in web.xml
        String prelude = sce.getServletContext().getInitParameter("prelude");
        String coda = sce.getServletContext().getInitParameter("coda");

        // Store prelude and coda as application attributes
        sce.getServletContext().setAttribute("prelude", prelude);
        sce.getServletContext().setAttribute("coda", coda);

        System.out.println("AppConfigListener: Application started. Prelude and Coda initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
