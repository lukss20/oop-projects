package com.example.demo1;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.SQLException;
@WebListener
public class DataManagerListener implements ServletContextListener {
    DataManager dataManager ;


    public void contextInitialized(ServletContextEvent sce) {
        try {
            dataManager = new DataManager();
            ServletContext servletContext = sce.getServletContext();
            servletContext.setAttribute("datamanager", dataManager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
