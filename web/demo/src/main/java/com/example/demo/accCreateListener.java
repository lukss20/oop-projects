package com.example.demo;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class accCreateListener implements ServletContextListener {
    AccountManager accountManager = new AccountManager();
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("accountManager", accountManager);
    }
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
